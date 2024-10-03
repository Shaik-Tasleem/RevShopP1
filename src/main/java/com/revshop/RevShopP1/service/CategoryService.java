//package com.revshop.RevShopP1.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.revshop.RevShopP1.model.Category;
//import com.revshop.RevShopP1.repository.CategoryRepository;
//
//import java.util.Optional;
//
//@Service
//public class CategoryService {
//
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    public Category getCategoryById(Long id) {
//        Optional<Category> category = Optional.ofNullable(categoryRepository.findById(id));
//        return category.orElse(null);  // Return null or throw an exception if not found
//    }
//}