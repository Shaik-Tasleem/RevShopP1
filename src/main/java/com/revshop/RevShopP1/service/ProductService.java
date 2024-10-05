package com.revshop.RevShopP1.service;

import com.revshop.RevShopP1.model.Product;
import com.revshop.RevShopP1.repository.ProductRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Fetch all products from the database
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Search for products by product name or category name
    public List<Product> searchProducts(String keyword) {
        return productRepository.findByProductNameContainingIgnoreCaseOrCategory_CategoryNameContainingIgnoreCase(keyword, keyword);
    }

    // Get a product by its ID, handling the case where it might not be found
    public Product getProductById(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            return productOptional.get();
        } else {
            throw new RuntimeException("Product not found with id: " + productId);
            // You can also define a custom exception instead of RuntimeException
        }
    }

    // Get products by category ID
    public List<Product> getProductsByCategoryId(Long categoryId) {
        return productRepository.findByCategory_CategoryId(categoryId);
    }

    // Save the product to the database
    public void save(Product product) {
        productRepository.save(product);
    }

	public List<Product> getProductsBySellerId(Long sellerIdLong) {
		return productRepository.findProductsBySellerId(sellerIdLong);
	}

	public void deleteProductById(Long productId) {
		productRepository.deleteById(productId);
	}

	@Transactional
    public void updateProduct(Long productId, Product updatedProduct) {
        productRepository.updateProduct(
            productId,
            updatedProduct.getProductName(),
            updatedProduct.getProductDescription(),
            updatedProduct.getPrice(),
            updatedProduct.getDiscountPrice(),
            updatedProduct.getQuantity(),
            updatedProduct.getImage()
        );
    }

	public Product findById(Long productId) {
		// TODO Auto-generated method stub
		return productRepository.findById(productId).get();
	}
}
