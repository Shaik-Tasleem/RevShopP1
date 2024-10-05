package com.revshop.RevShopP1.controller;

import com.revshop.RevShopP1.model.Buyer;
import com.revshop.RevShopP1.model.Product;
import com.revshop.RevShopP1.model.Seller;
import com.revshop.RevShopP1.service.CartService;
import com.revshop.RevShopP1.service.ProductService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService; // Assuming ProductService is available

    @PostMapping("/cart/toggle")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> toggleCartItem(@RequestParam("productId") Long productId, 
                                                              @RequestParam("sellerId") Long sellerId, 
                                                              HttpSession session) {
        Buyer buyer = (Buyer) session.getAttribute("buyer");
        
        // Check if the buyer is logged in
        if (buyer == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("message", "You must be logged in to add items to the cart."));
        }

        Product product = productService.findById(productId); // Fetch product by ID
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", "Product not found"));
        }

        Seller seller = product.getSeller(); // Get seller from product
        boolean isInCart = cartService.isProductInCart(buyer, productId);

        Map<String, Object> response = new HashMap<>();
        if (isInCart) {
            cartService.removeFromCart(buyer, productId); // Remove if already in cart
            response.put("message", "Product removed from cart");
            response.put("inCart", false);
        } else {
            cartService.addToCart(buyer, productId, product.getPrice(), 1, seller); // Add with quantity 1
            response.put("message", "Product added to cart");
            response.put("inCart", true);
        }

        response.put("success", true);
        return ResponseEntity.ok(response);
    }

}
