package com.revshop.RevShopP1.model;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="cart")
public class Cart {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
private Long cartId;
private int price;
private int quantity;
@ManyToOne
@JoinColumn(name = "buyerId", nullable = false)
private Buyer buyer;
@ManyToOne
@JoinColumn(name = "sellerId", nullable = false)
private Seller seller;
private Long productId;
public Long getCartId() {
	return cartId;
}
public void setCartId(Long cartId) {
	this.cartId = cartId;
}
public int getPrice() {
	return price;
}
public void setPrice(int price) {
	this.price = price;
}
public int getQuantity() {
	return quantity;
}
public void setQuantity(int quantity) {
	this.quantity = quantity;
}
public Long getBuyer() {
	return buyer.getBuyerId();
}
public void setBuyerId(Buyer buyer) {
	this.buyer=buyer;
}
public Long getSeller() {
	return seller.getSellerId();
}
public void setSeller(Seller seller) {
	this.seller = seller;
}
public Long getProductId() {
	return productId;
}
public void setProductId(Long productId) {
	this.productId = productId;
}

}
