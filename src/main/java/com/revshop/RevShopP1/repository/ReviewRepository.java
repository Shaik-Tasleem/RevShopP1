package com.revshop.RevShopP1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.revshop.RevShopP1.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

	

	@Query(value = "SELECT * FROM review WHERE product_id = :productId AND order_id = :orderId AND seller_id=:sellerId", nativeQuery = true)
	List<Review> findByProductIdAndOrderIdAndSellerId(@Param("productId") Long productId, @Param("orderId") Long orderId, @Param("sellerId") Long sellerId);

}
