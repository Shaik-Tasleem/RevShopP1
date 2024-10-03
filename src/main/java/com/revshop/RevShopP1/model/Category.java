package com.revshop.RevShopP1.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Category {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long categoryId;
	@OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL)
	private List<Product> product = new ArrayList<>();
	private String categoryName;
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Category(Long categoryId, String categoryName) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}
	public Category()
	{
		
	}
	
	
	


}
