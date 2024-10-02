package com.revshop.RevShopP1.repository;

import com.revshop.RevShopP1.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    
    // Custom search method
    List<Product> findByProductNameContainingIgnoreCaseOrCategory_CategoryNameContainingIgnoreCase(String productName, String categoryName);
}
