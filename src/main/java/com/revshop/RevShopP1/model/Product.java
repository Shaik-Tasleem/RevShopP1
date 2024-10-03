package com.revshop.RevShopP1.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Product {
	@ManyToOne
	@JoinColumn(name = "sellerId", nullable = false)
	private Seller seller;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<Orders> orders = new ArrayList<>();
	private List<Payments> payments = new ArrayList<>();

	private Long productId;
	private String productName;
	private String productDescription;
	private double price;
	private double discountPrice;
	private int threshold;
	private int quantity;
	private String image;
	@ManyToOne
	@JoinColumn(name = "categoryId", nullable = false)
	private Category category;
	public Long getSeller() {
		return seller.getSellerId();
	}
	public void setSeller(Seller seller) {
		this.seller = seller;
	}
	
	
	public List<Orders> getOrders() {
		return orders;
	}
	public void setOrders(List<Orders> orders) {
		this.orders = orders;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(double discountPrice) {
		this.discountPrice = discountPrice;
	}
	public int getThreshold() {
		return threshold;
	}
	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Long getCategory() {
		return category.getCategoryId();
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
	
	
	
	
}
