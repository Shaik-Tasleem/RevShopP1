package com.revshop.RevShopP1.service;

import com.revshop.RevShopP1.model.*;
import com.revshop.RevShopP1.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BuyerRepository buyerRepository;
    @Transactional
    // Add product to cart
    public void addProductToCart(Long buyerId, Product product) {
        Buyer buyer = buyerRepository.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));

        Optional<Cart> existingCart = cartRepository.findAllByBuyer(buyer)
            .stream()
            .filter(cart -> cart.getProduct().equals(product))
            .findFirst();

        if (existingCart.isPresent()) {
            // If the product is already in the cart, update the quantity
            Cart cart = existingCart.get();
            cart.setQuantity(cart.getQuantity() + 1);  // Increase quantity
            cartRepository.save(cart);
        } else {
            // Add new cart entry if the product is not in the cart
            Cart cart = new Cart();
            cart.setSeller(product.getSeller());
            cart.setBuyer(buyer);
            cart.setProduct(product);
            cart.setQuantity(1);  // Assuming starting quantity is 1
            cart.setPrice(product.getPrice());  // Set the price
            cartRepository.save(cart);
        }
    }
    @Transactional
    // Remove product from cart
    public void removeProductFromCart(Long buyerId, Product product) {
        Buyer buyer = buyerRepository.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));
        cartRepository.deleteByBuyerAndProduct_ProductId(buyer, product.getProductId());
    }

	public List<Cart> findAllByBuyer(Buyer buyer) {
		// TODO Auto-generated method stub
		return cartRepository.findAllByBuyer(buyer);
	}

	public boolean existsByBuyerAndProduct_ProductId(Buyer buyer, Long productId) {
		// TODO Auto-generated method stub
		return cartRepository.existsByBuyerAndProduct_ProductId(buyer, productId);
	}
	public void clearCartForBuyer(Buyer buyer) {
        List<Cart> cartItems = cartRepository.findAllByBuyer(buyer);
        cartRepository.deleteAll(cartItems);  // Deletes all items for the buyer
    }
}
