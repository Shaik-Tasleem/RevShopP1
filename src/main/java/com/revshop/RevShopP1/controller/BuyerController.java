package com.revshop.RevShopP1.controller;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.revshop.RevShopP1.model.*;
import com.revshop.RevShopP1.model.Product;
import com.revshop.RevShopP1.repository.ReviewRepository;
import com.revshop.RevShopP1.service.BuyerService;
import com.revshop.RevShopP1.service.CartService;
import com.revshop.RevShopP1.service.EmailService;
import com.revshop.RevShopP1.service.OrderService;
import com.revshop.RevShopP1.service.ProductService;
import com.revshop.RevShopP1.service.ReviewService;
import com.revshop.RevShopP1.service.WishlistService;
import com.revshop.RevShopP1.utils.PasswordUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/ecom")
public class BuyerController {
    @Autowired
    private BuyerService buyerService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CartService cartService;
    @Autowired
    private WishlistService wishService;
    @Autowired
    private PasswordUtils pwd_obj;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ReviewService reviewService;
   
    @GetMapping("/buyerRegistration")
    public String registrationForm(Model model) {
        model.addAttribute("buyer", new Buyer());
        return "BuyerRegistration";
    }

    @PostMapping("/buyerRegistration")
    public String registration(@ModelAttribute Buyer buyer) throws NoSuchAlgorithmException {
        buyerService.insertBuyer(buyer);
        return "LoginPage";
    }

    @Autowired
    private EmailService emailService;

    @PostMapping("/api/send-verification")
    @ResponseBody
    public ResponseEntity<String> sendVerificationEmail(@RequestParam("email") String buyerEmail) {
        String otp = emailService.generateOtp();
        boolean emailSent = emailService.sendEmail(buyerEmail, otp);

        if (emailSent) {
            return ResponseEntity.ok("OTP sent successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send OTP.");
        }
    }

    @PostMapping("/api/verify-code")
    @ResponseBody
    public ResponseEntity<String> verifyOtp(@RequestParam("email") String buyerEmail,
            @RequestParam("code") String otp) {
        boolean isOtpValid = emailService.verifyOtp(buyerEmail, otp);

        if (isOtpValid) {
            return ResponseEntity.ok("OTP verified successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP.");
        }
    }
    @PostMapping("/buyer/handleLogin")
    public String buyerLogin(@RequestParam(required = false) String email,
                             @RequestParam(required = false) String mobileNumber,
                             @RequestParam String password, 
                             Model model,
                             HttpServletResponse response) throws NoSuchAlgorithmException {
        Buyer buyer_obj = null;

        if (email != null) {
            buyer_obj = buyerService.getBuyerDetailsByEmail(email);
        } else if (mobileNumber != null) {
            buyer_obj = buyerService.getBuyerDetailsByMobileNumber(mobileNumber);
        }

        if (buyer_obj == null || !buyer_obj.getPassword().equals(pwd_obj.hashPassword(password))) {
            String msg = "Invalid Email or Password...\nIf you are a new user Kindly...Register..\nTo access our Services..";
            model.addAttribute("errorMessage", msg);
            return "LoginPage";
        } else {
            Cookie buyerCookie = new Cookie("buyerId", buyer_obj.getBuyerId().toString());
            buyerCookie.setPath("/");
            buyerCookie.setMaxAge(24 * 60 * 60); 
            buyerCookie.setHttpOnly(true);
            response.addCookie(buyerCookie);

            return "redirect:/ecom/buyerdashboard";
        }
    }
    @GetMapping("/buyerdashboard")
    public String displayBuyerDashboard(Model model, HttpServletRequest request) {
        // Assuming you have a method to get the buyer's ID from the session
        Long buyerId = (Long) request.getSession().getAttribute("buyerId");
        model.addAttribute("buyerId", buyerId);
        return "buyerdashboard";
    }
    @PostMapping("/toggle")
	public ResponseEntity<Map<String, Object>> toggleCart(@RequestParam("productId") Long productId,
			HttpServletRequest request) {
		// Retrieve buyer ID from cookies
		Map<String, Object> response = new HashMap<>();
		Long buyerId = getBuyerIdFromCookies(request); // This should fetch the buyer ID from cookies or session

		if (buyerId != null) {
			Product product = productService.getProductById(productId);
			Buyer buyer = buyerService.findBuyerDetailsById(buyerId);
			if (product != null) {
				boolean isProductInCart = cartService.existsByBuyerAndProduct_ProductId(buyer, productId);

				if (isProductInCart) {
					cartService.removeProductFromCart(buyerId, product);
					response.put("message", "Product removed from cart.");
				} else {
					cartService.addProductToCart(buyerId, product);
					response.put("message", "Product added to cart.");
				}

				response.put("success", true);
			} else {
				response.put("success", false);
				response.put("errorMessage", "Product not found.");
			}
		} else {
			response.put("success", false);
			response.put("errorMessage", "Please log in to manage your cart.");
		}

		return ResponseEntity.ok(response); // Return the JSON response
	}

	@PostMapping("/wishlist/toggle")
	public ResponseEntity<Map<String, Object>> toggleWish(@RequestParam("productId") Long productId,
			HttpServletRequest request) {
		// Retrieve buyer ID from cookies
		Map<String, Object> response = new HashMap<>();
		Long buyerId = getBuyerIdFromCookies(request); // This should fetch the buyer ID from cookies or session
		if (buyerId != null) {
			Product product = productService.getProductById(productId);
			Buyer buyer = buyerService.findBuyerDetailsById(buyerId);
			if (product != null) {
				boolean isProductInWish = wishService.existsByBuyerAndProduct_ProductId(buyer, productId);
				if (isProductInWish) {
					System.out.println("Hi1");
					wishService.removeProductFromWishlist(buyerId, product);
					response.put("message", "Product removed from Wish.");
				} else {
					System.out.println("Hi1");
					wishService.addProductToWish(buyerId, product);
					response.put("message", "Product added to Wish.");
				}
				response.put("success", true);
			} else {
				response.put("success", false);
				response.put("errorMessage", "Product not found.");
			}

		} else {
			response.put("success", false);
			response.put("errorMessage", "Please log in to manage your cart.");
		}

		return ResponseEntity.ok(response);

	}

	@GetMapping("/wishlist/view")

	// Create a new Wishlist entry
	public String viewWishlist(Model model, HttpServletRequest request) {
		Long buyerId = getBuyerIdFromCookies(request);
		Buyer buyer = buyerService.findBuyerDetailsById(buyerId);
		if (buyerId != null) {
			List<Wishlist> wishlistItems = wishService.findAllByBuyer(buyer);
			List<Product> productInWish = new ArrayList<>();
			for (Wishlist i : wishlistItems) {
				productInWish.add(i.getProduct());
				// System.out.println();
			}
			model.addAttribute("wishlistItems", productInWish);
		}

		return "wishlist-view"; // Render the wishlist Thymeleaf view
	}

	private Long getBuyerIdFromCookies(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("buyerId".equals(cookie.getName())) {
					return Long.parseLong(cookie.getValue());
				}
			}
		}
		return null;
	}

	@GetMapping("/cart/view")
	public String cartView(Model model, HttpServletRequest request) {
		Long buyerId = getBuyerIdFromCookies(request);
		Buyer buyer = buyerService.findBuyerDetailsById(buyerId);
		if (buyerId != null) {
			List<Cart> cartItems=cartService.findAllByBuyer(buyer);
			List<Product> productInCart = new ArrayList<>();
			for(Cart i:cartItems) {
				productInCart.add(i.getProduct());
			}
			model.addAttribute("cartItems", productInCart);
		}
		return "cart-view";
	}
	@PostMapping("/cart/remove/{productId}")
    public String removeFromCart(@PathVariable Long productId, HttpServletRequest request) {
        Long buyerId = null;
        Product product = productService.getProductById(productId);
        // Retrieve buyer ID from cookies
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("buyerId")) {
                    buyerId = Long.parseLong(cookie.getValue());
                    break;
                }
            }
        }
        
        if (buyerId != null) {
            // Call service to remove the product from wishlist
        	System.out.println(buyerId);
        	System.out.println(productId);
        	cartService.removeProductFromCart(buyerId, product);
        }

        // Redirect back to wishlist view
        return "redirect:/ecom/cart/view";
    }
	
	@PostMapping("/cart/buyNow")
	public String checkout(Model model, HttpServletRequest request) {
	    Long buyerId = getBuyerIdFromCookies(request);
	    Buyer buyer = buyerService.findBuyerDetailsById(buyerId);
	    
	    if (buyer != null) {
	        List<Cart> cartItems = cartService.findAllByBuyer(buyer);
	        double totalPrice = cartItems.stream().mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity()).sum();
	        
	        model.addAttribute("cartItems", cartItems);
	        model.addAttribute("totalPrice", totalPrice);
	        model.addAttribute("buyer", buyer); // Display buyer's shipping info
	    }
	    
	    return "checkout"; // Render checkout view
	}
	@PostMapping("/checkout/confirm")
	public String confirmCheckout( @RequestParam String paymentMethod,HttpServletRequest request, Model model) {
	    Long buyerId = getBuyerIdFromCookies(request);
	    Buyer buyer = buyerService.findBuyerDetailsById(buyerId);

	    if (buyer != null) {
	        List<Cart> cartItems = cartService.findAllByBuyer(buyer);
	        double totalPrice = cartItems.stream().mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity()).sum();
	        
	        // Save order for each cart item
	        for (Cart cartItem : cartItems) {
	            Orders order = new Orders();
	            order.setBuyer(buyer);
	            order.setProduct(cartItem.getProduct());
	            order.setSeller(cartItem.getSeller()); // Store seller information
	            order.setQuantity(cartItem.getQuantity());
	            order.setShippingAddress(buyer.getStreet()+buyer.getCity()+buyer.getState()+buyer.getCountry());
	            order.setTotalPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
	            order.setStatus("Processing"); // Initial status of the order
	            orderService.saveOrder(order);
	        }
	        
	        // Optionally clear the cart after order is placed
	        cartService.clearCartForBuyer(buyer);
	        

	        
	        model.addAttribute("orderSummary", cartItems);
	        model.addAttribute("totalPrice", totalPrice);
	        model.addAttribute("paymentMethod", paymentMethod);
	        model.addAttribute("buyer", buyer);
	    }
	    
	    return "order-confirmation"; // Return order confirmation page
	}
	
    @GetMapping("/buyer/{buyerId}")
    public String getOrdersByBuyer(@PathVariable Long buyerId, Model model) {
        List<Orders> orders = orderService.getOrdersByBuyerId(buyerId);
        model.addAttribute("orders", orders);
        return "buyer-orders"; // The Thymeleaf template where orders will be displayed
    } //ecom/buyer/id for all orders 
    
    @GetMapping("/review/add")
    public String showAddReviewForm(@RequestParam Long buyerId, @RequestParam Long productId, @RequestParam Long orderId, Model model) {
        // Add necessary attributes to the model
        model.addAttribute("buyerId", buyerId);
        model.addAttribute("productId", productId);
        model.addAttribute("orderId", orderId);
        return "addReview"; // Return the name of the view for the review form
    }
    @PostMapping("/review/submit")
    public String submitReview(@RequestParam Long buyerId,
                                @RequestParam Long productId,
                                @RequestParam Long orderId,
                                @RequestParam String content,
                                @RequestParam int rating) {
        Review review = new Review();
        review.setBuyerId(buyerId);
        review.setProductId(productId);
        review.setOrderId(orderId);
        review.setContent(content);
        review.setRating(rating);

        reviewService.saveReview(review); // Save the review to the database

        return "redirect:/ecom/buyerdashboard"; // Redirect to orders page after submission
    }
    
    



}
