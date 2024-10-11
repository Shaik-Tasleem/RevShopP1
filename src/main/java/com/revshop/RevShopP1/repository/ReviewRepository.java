package com.revshop.RevShopP1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revshop.RevShopP1.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // You can define custom queries if needed
}