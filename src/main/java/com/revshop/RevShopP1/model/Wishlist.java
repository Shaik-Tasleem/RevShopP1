package com.revshop.RevShopP1.model;

import jakarta.persistence.*;

@Entity
@Table(name = "wishlist")
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishId;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false) // Ensure Product entity has productId as PK
    private Product product;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "buyerId", nullable = false) // Ensure Buyer entity has buyerId as PK
    private Buyer buyer;

    @ManyToOne
    @JoinColumn(name = "sellerId", nullable = false) // Ensure Seller entity has sellerId as PK
    private Seller seller;

    // Getters and Setters
    public Long getWishId() {
        return wishId;
    }

    public void setWishId(Long wishId) {
        this.wishId = wishId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }
}
