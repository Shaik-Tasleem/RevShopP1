package com.revshop.RevShopP1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revshop.RevShopP1.model.Review;
import com.revshop.RevShopP1.repository.ReviewRepository;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository; // Assuming you have a JPA repository

    public void saveReview(Review review) {
        reviewRepository.save(review); // Save the review to the database
    }
}