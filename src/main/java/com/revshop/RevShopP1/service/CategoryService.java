package com.revshop.RevShopP1.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revshop.RevShopP1.model.Category;
import com.revshop.RevShopP1.model.Product;
import com.revshop.RevShopP1.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepo;
	public void insertCategory(Category category) {
		categoryRepo.save(category);
	}
	public List<Category> getAllCat(){
		return categoryRepo.findAll();
	}
	public Category getCatBtId(int id) {
		return categoryRepo.findById(id).get();
	}

	public void deleteCat(int id) {
		categoryRepo.deleteById(id);
	}
	
	public List<String> getAllCategoryNames() {
	    return categoryRepo.findAll().stream()
	               .map(Category::getCategoryName) 
	               .collect(Collectors.toList());
	}


	public List<Category> getCatByName(String categoryName) {
	    return categoryRepo.findByCategoryName(categoryName); 
	}
	
}
