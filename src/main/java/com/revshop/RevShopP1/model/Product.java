package com.revshop.RevShopP1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product {
	
	private int sellerId;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productId;
	private String productName;
	private String productDescription;
	private double price;
	private double discountPrice;
	private int threshold;
	private int quantity;
	private String image;
	
	private int categoryId;
	
	
	
	
}
