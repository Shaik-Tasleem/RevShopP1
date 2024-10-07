//package com.revshop.RevShopP1.model;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//
//@Entity
//@Table(name = "reviews")
//public class Review {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long reviewId;
//
//    @ManyToOne
//    @JoinColumn(name = "buyerId", nullable = false)
//    private Buyer buyer;
//
//    @ManyToOne
//    @JoinColumn(name = "productId", nullable = false)
//    private Product product;
//
//    private int rating; // Store rating as a number (1-5 stars)
//
//    private String comment; // The buyer's review
//
//    // Getters and setters
//    public Long getReviewId() {
//        return reviewId;
//    }
//
//    public void setReviewId(Long reviewId) {
//        this.reviewId = reviewId;
//    }
//
//    public Buyer getBuyer() {
//        return buyer;
//    }
//
//    public void setBuyer(Buyer buyer) {
//        this.buyer = buyer;
//    }
//
//    public Product getProduct() {
//        return product;
//    }
//
//    public void setProduct(Product product) {
//        this.product = product;
//    }
//
//    public int getRating() {
//        return rating;
//    }
//
//    public void setRating(int rating) {
//        this.rating = rating;
//    }
//
//    public String getComment() {
//        return comment;
//    }
//
//    public void setComment(String comment) {
//        this.comment = comment;
//    }
//}
