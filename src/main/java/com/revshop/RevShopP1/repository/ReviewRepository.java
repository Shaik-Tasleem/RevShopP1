//package com.revshop.RevShopP1.repository;
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import com.revshop.RevShopP1.model.Review;
//
//public interface ReviewRepository extends JpaRepository<Review, Long> {
//
//    List<Review> findByProduct_ProductId(Long productId);
//
//    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.product.productId = :productId")
//    Double findAverageRatingByProductId(Long productId);
//}
