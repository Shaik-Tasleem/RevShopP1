package com.revshop.RevShopP1.controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;

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
import com.revshop.RevShopP1.service.BuyerService;
import com.revshop.RevShopP1.service.EmailService;
import com.revshop.RevShopP1.service.ProductService;
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
    private PasswordUtils pwd_obj;
    @Autowired
    private ProductService productService;
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
    public String displayBuyerDashboard() {
    	return "buyerdashboard";
    }
    @GetMapping("/buyer/profile")
    public String viewProfile(HttpServletRequest request, Model model) {
        // Retrieve the buyerId from the cookie
        String buyerIdStr = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("buyerId")) {
                    buyerIdStr = cookie.getValue();
                    break;
                }
            }
        }
        if (buyerIdStr == null) {
            return "redirect:/ecom/LoginPage";
        }

        Long buyerId = null;
        try {
            buyerId = Long.parseLong(buyerIdStr);
        } catch (NumberFormatException e) {
            return "redirect:/ecom/LoginPage";
        }

        Buyer buyer = buyerService.getBuyerDetailsById(buyerId);
        if (buyer == null) {
            return "redirect:/ecom/LoginPage";
        }
        model.addAttribute("buyer", buyer);  // Add the buyer to the model
        return "buyerprofile";  // Resolve to profile.html
    }
    @GetMapping("/Logout")
    public String logoutMe(HttpServletRequest request, HttpServletResponse response) {
        // Get all the cookies
        Cookie[] cookies = request.getCookies();
        
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // Set the cookie's max age to 0 to delete it
                cookie.setMaxAge(0);
                // Set the path to root so it applies to all pages
                cookie.setPath("/");
                // Add the updated cookie back to the response
                response.addCookie(cookie);
            }
        }
        
        // Invalidate the session to log out the user
        request.getSession().invalidate();
        
        // Redirect to the welcome page after logout
        return "redirect:/ecom/welcomepage";
    }
    // Update profile information
    @PostMapping("/buyer/updateProfile")
    public String updateProfileInfo(@RequestParam("firstName") String firstName,
                                    @RequestParam("lastName") String lastName,
                                    @RequestParam("email") String email,
                                    @RequestParam("mobileNumber") String mobileNumber,
                                    HttpServletRequest request, 
                                    HttpServletResponse response,
                                    Model model) {

        // Retrieve the buyerId from the cookie
        String buyerIdStr = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("buyerId")) {
                    buyerIdStr = cookie.getValue();
                    break;
                }
            }
        }

        if (buyerIdStr == null) {
            return "redirect:/ecom/LoginPage";
        }

        Long buyerId = null;
        try {
            buyerId = Long.parseLong(buyerIdStr);
        } catch (NumberFormatException e) {
            return "redirect:/ecom/LoginPage";
        }

        Buyer existingBuyer = buyerService.getBuyerDetailsById(buyerId);
        if (existingBuyer == null) {
            return "redirect:/ecom/LoginPage";
        }

        // Update buyer's personal information
        existingBuyer.setFirstName(firstName);
        existingBuyer.setLastName(lastName);
        existingBuyer.setEmail(email);
        existingBuyer.setMobileNumber(mobileNumber);

        // Save the updated buyer back to the database
        buyerService.updateBuyerProfile(existingBuyer);

        // Update the model with updated buyer details
        model.addAttribute("buyer", existingBuyer);

        return "redirect:/ecom/buyer/profile"; // Redirect to profile page after updating
    }

    @PostMapping("/buyer/updateAddress")
    public String updateAddress(@RequestParam("street") String street,
                                @RequestParam("city") String city,
                                @RequestParam("state") String state,
                                @RequestParam("postalCode") int postalCode,
                                @RequestParam("country") String country,
                                HttpServletRequest request, 
                                HttpServletResponse response,
                                Model model) {

        // Retrieve the buyerId from the cookie
        String buyerIdStr = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("buyerId")) {
                    buyerIdStr = cookie.getValue();
                    break;
                }
            }
        }

        if (buyerIdStr == null) {
            return "redirect:/ecom/LoginPage";
        }

        Long buyerId = null;
        try {
            buyerId = Long.parseLong(buyerIdStr);
        } catch (NumberFormatException e) {
            return "redirect:/ecom/LoginPage";
        }

        Buyer existingBuyer = buyerService.getBuyerDetailsById(buyerId);
        if (existingBuyer == null) {
            return "redirect:/ecom/LoginPage";
        }

        // Update buyer's address information
        existingBuyer.setStreet(street);
        existingBuyer.setCity(city);
        existingBuyer.setState(state);
        existingBuyer.setPostalCode(postalCode);
        existingBuyer.setCountry(country);

        // Save the updated buyer back to the database
        buyerService.updateBuyerProfile(existingBuyer);

        // Update the model with updated buyer details
        model.addAttribute("buyer", existingBuyer);

        return "redirect:/ecom/buyer/profile"; // Redirect to profile page after updating
    }
    @PostMapping("/buyer/changePassword")
    public String changePassword(
            @RequestParam("current-password") String currentPassword,
            @RequestParam("new-password") String newPassword,
            HttpServletRequest request, 
            Model model) throws NoSuchAlgorithmException {

        // Retrieve the buyerId from the cookie
        String buyerIdStr = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
            	System.out.println(cookie.getName());
                if (cookie.getName().equals("buyerId")) {
                    buyerIdStr = cookie.getValue();
                    break;
                }
            }
        }

        if (buyerIdStr == null) {
            return "redirect:/ecom/LoginPage";
        }

        Long buyerId;
        try {
            buyerId = Long.parseLong(buyerIdStr);
        } catch (NumberFormatException e) {
            return "redirect:/ecom/LoginPage";
        }

        // Retrieve the buyer from the database
        Buyer buyer = buyerService.getBuyerDetailsById(buyerId);
        if (buyer == null) {
            return "redirect:/ecom/LoginPage";
        }

        // Verify the current password
        if (!buyer.getPassword().equals(pwd_obj.hashPassword(currentPassword))) {
            model.addAttribute("errorMessage", "Incorrect current password. Please try again.");
            return "profile"; // Or wherever the form is rendered
        }

        // Update the password
        buyer.setPassword(pwd_obj.hashPassword(newPassword));
        buyerService.updateBuyerProfile(buyer);
        model.addAttribute("successMessage", "Password updated successfully.");
        model.addAttribute("buyer", buyer);
        return "profile"; // Redirect or return to the profile page
    }
    @GetMapping("/category/{categoryId}")
    public String showProductsByCategory(@PathVariable Long categoryId, Model model) {
        List<Product> products = productService.getProductsByCategoryId(categoryId);
        model.addAttribute("products", products);
        return "BuyerdashboardExtend";  // HTML page to display the products
    }
}
