package com.revshop.RevShopP1.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revshop.RevShopP1.model.Category;
import com.revshop.RevShopP1.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepo;
	public Category getCatById(Long id) {
        Optional<Category> categoryOptional = categoryRepo.findById(id);
        
        return categoryOptional.orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

}
