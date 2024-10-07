//package com.revshop.RevShopP1.service;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.revshop.RevShopP1.model.Review;
//import com.revshop.RevShopP1.repository.ReviewRepository;
//
//@Service
//public class ReviewService {
//
//    @Autowired
//    private ReviewRepository reviewRepository;
//
//    public Review addReview(Review review) {
//        return reviewRepository.save(review);
//    }
//
//    public List<Review> getReviewsByProduct(Long productId) {
//        return reviewRepository.findByProduct_ProductId(productId);
//    }
//
//    public Double getAverageRating(Long productId) {
//        return reviewRepository.findAverageRatingByProductId(productId);
//    }
//}
