package com.revshop.RevShopP1.producttest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revshop.RevShopP1.model.Product;
import com.revshop.RevShopP1.repository.ProductRepository;
import com.revshop.RevShopP1.service.ProductService;

public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddProduct() {
        Product product = new Product();
        product.setProductName("Test Product");
        
        when(productRepository.save(any(Product.class))).thenReturn(product);
        
        Product savedProduct = productService.addProduct(product);
        
        assertNotNull(savedProduct);
        assertEquals("Test Product", savedProduct.getProductName());
        verify(productRepository, times(1)).save(product);
    }
}
