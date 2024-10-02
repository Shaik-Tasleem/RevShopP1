package com.revshop.RevShopP1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revshop.RevShopP1.model.Product;
import com.revshop.RevShopP1.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private  ProductRepository productRepository;


   
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }
}