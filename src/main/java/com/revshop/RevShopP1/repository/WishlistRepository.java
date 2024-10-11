package com.revshop.RevShopP1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revshop.RevShopP1.model.*;
@Repository
public interface WishlistRepository extends JpaRepository<Wishlist,Long> {
	boolean existsByBuyerAndProduct_ProductId(Buyer buyer, Long productId);
	void deleteByBuyerAndProduct_ProductId(Buyer buyer, Long productId);

	List<Wishlist> findAllByBuyer(Buyer buyer);
}
