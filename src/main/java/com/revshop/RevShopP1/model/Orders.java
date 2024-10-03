package com.revshop.RevShopP1.model;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;
	@ManyToOne
	@JoinColumn(name = "buyerId", nullable = false)
	private Buyer buyer;
	@ManyToOne
	@JoinColumn(name = "sellerId", nullable = false)
	private Seller seller;
	@ManyToOne
	@JoinColumn(name = "productId", nullable = false)
	private Product product;
	private double totalPrice;
	private String shippingAddress;
	private String status;
	private LocalDate orderDate;
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getBuyer() {
		return buyer.getBuyerId();
	}
	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}
	public Long getSeller() {
		return seller.getSellerId();
	}
	public void setSeller(Seller seller) {
		this.seller = seller;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDate getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}
	
}
