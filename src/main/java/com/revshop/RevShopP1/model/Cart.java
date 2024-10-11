package com.revshop.RevShopP1.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    private double price;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "buyerId", nullable = false)
    private Buyer buyer;

    @ManyToOne
    @JoinColumn(name = "sellerId", nullable = false)
    private Seller seller;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product product;
    

    // Getters and Setters
    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price2) {
        this.price = price2;
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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
