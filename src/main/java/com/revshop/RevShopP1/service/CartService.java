package com.revshop.RevShopP1.service;

import com.revshop.RevShopP1.model.Buyer;
import com.revshop.RevShopP1.model.Cart;
import com.revshop.RevShopP1.model.Seller;
import com.revshop.RevShopP1.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public boolean isProductInCart(Buyer buyer, Long productId) {
        // Check if the product is already in the cart for this buyer
        return cartRepository.existsByBuyerAndProductId(buyer, productId);
    }

    public void addToCart(Buyer buyer, Long productId, double price, int quantity, Seller seller) {
        // Create a new cart item
        Cart cart = new Cart();
        cart.setBuyer(buyer);
        cart.setProductId(productId);
        cart.setPrice(price);
        cart.setQuantity(quantity);
        cart.setSeller(seller);

        cartRepository.save(cart);
    }

    public void removeFromCart(Buyer buyer, Long productId) {
        // Remove the cart item based on the buyer and product
        cartRepository.deleteByBuyerAndProductId(buyer, productId);
    }

    public List<Cart> getCartItemsForBuyer(Buyer buyer) {
        // Fetch all cart items for the buyer
        return cartRepository.findAllByBuyer(buyer);
    }
}
