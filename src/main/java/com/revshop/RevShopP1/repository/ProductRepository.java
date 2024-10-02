package com.revshop.RevShopP1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revshop.RevShopP1.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    // You can define custom query methods here if needed
}
