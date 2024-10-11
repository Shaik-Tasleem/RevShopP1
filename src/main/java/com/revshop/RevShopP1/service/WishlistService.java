package com.revshop.RevShopP1.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revshop.RevShopP1.model.*;
import com.revshop.RevShopP1.repository.*;
@Service
public class WishlistService {
	
	@Autowired
	private WishlistRepository wislistRepo; 
	
	@Autowired
	private BuyerRepository buyerRepo;
	
	public boolean existsByBuyerAndProduct_ProductId(Buyer buyer, Long productId) {
		// TODO Auto-generated method stub
		return wislistRepo.existsByBuyerAndProduct_ProductId(buyer,productId);
	}
	@Transactional
	public void addProductToWish(Long buyerId,Product product) {
		Buyer buyer=buyerRepo.findById(buyerId).get();
		Optional<Wishlist> existingWish=wislistRepo.findAllByBuyer(buyer).stream()
				.filter(wishlist->wishlist.getProduct().equals(product))
				.findFirst();
		if(existingWish.isPresent()) {
			Wishlist wishlist=existingWish.get();
			wishlist.setQuantity(wishlist.getQuantity()+1);
			wislistRepo.save(wishlist);
		}
		else {
			Wishlist wishlist=new Wishlist();
			wishlist.setBuyer(buyer);
			wishlist.setProduct(product);
			wishlist.setQuantity(1);
			wishlist.setSeller(product.getSeller());
			wislistRepo.save(wishlist);
		}
	}
	@Transactional
	public void removeProductFromWishlist(Long buyerId,Product product) {
		Buyer buyer = buyerRepo.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));
		wislistRepo.deleteByBuyerAndProduct_ProductId(buyer, product.getProductId());
	}
	public List<Wishlist> findAllByBuyer(Buyer buyer){
		return wislistRepo.findAllByBuyer(buyer);
	}
}
