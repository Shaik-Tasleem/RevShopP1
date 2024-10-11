package com.revshop.RevShopP1.repository;

import com.revshop.RevShopP1.model.Buyer;
import com.revshop.RevShopP1.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	boolean existsByBuyerAndProduct_ProductId(Buyer buyer, Long productId);

	void deleteByBuyerAndProduct_ProductId(Buyer buyer, Long productId);

	List<Cart> findAllByBuyer(Buyer buyer);
}
