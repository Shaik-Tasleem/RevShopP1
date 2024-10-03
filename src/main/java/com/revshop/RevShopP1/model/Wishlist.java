package com.revshop.RevShopP1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="wishlist")
public class Wishlist {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long wishId;
private Long productId;
private int quanity;
@ManyToOne
@JoinColumn(name = "buyerId", nullable = false)
private Buyer buyer;
@ManyToOne
@JoinColumn(name = "sellerId", nullable = false)
private Seller seller;
public Long getWishId() {
	return wishId;
}
public void setWishId(Long wishId) {
	this.wishId = wishId;
}
public Long getProductId() {
	return productId;
}
public void setProductId(Long productId) {
	this.productId = productId;
}
public int getQuanity() {
	return quanity;
}
public void setQuanity(int quanity) {
	this.quanity = quanity;
}
public Long getBuyer() {
	return buyer.getBuyerId();
}
public void setBuyerId(Buyer buyer) {
	this.buyer=buyer;
}


}
