package com.revshop.RevShopP1.model;

import java.time.LocalDate;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class Payments {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long paymentId;
	@ManyToOne
	@JoinColumn(name = "buyerId", nullable = false)
	private Buyer buyer;
	@ManyToOne
	@JoinColumn(name = "productId", nullable = false)
	private Product product;
	@ManyToOne
	@JoinColumn(name = "sellerId", nullable = false)
	private Seller seller;
	private String paymentMethod;
	private String paymentStatus;
	private LocalDate paymentDate;
	public Long getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}
	public Long getBuyer() {
		return buyer.getBuyerId();
	}
	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}
	public Long getProduct() {
		return product.getProductId();
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Long getSeller() {
		return seller.getSellerId();
	}
	public void setSeller(Seller seller) {
		this.seller = seller;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public LocalDate getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}
	
	
	
	
}
