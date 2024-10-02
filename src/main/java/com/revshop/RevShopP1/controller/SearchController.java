package com.revshop.RevShopP1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.revshop.RevShopP1.model.Product;
import com.revshop.RevShopP1.service.ProductService;

@Controller
public class SearchController {

    @Autowired
    private ProductService productService;

    // Search from welcome page using GET
    @GetMapping("/products/search") // Updated mapping
    public String showWelcomePage(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Product> products;
        
        if (keyword == null || keyword.isEmpty()) {
            // No keyword provided, show all products
            products = productService.getAllProducts();
        } else {
            // Search by keyword (product name or category name)
            products = productService.searchProducts(keyword);
            if (products == null || products.isEmpty()) {
                // If no matching products are found, show all products
                products = productService.getAllProducts();
            }
        }
        
        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword); // To retain the search keyword in the UI
        return "Buyerdashboard"; // Return to welcome page
    }

    // Search from demo page using GET
    @GetMapping("/searchproducts")
    public String showDemoPage(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Product> products;

        if (keyword == null || keyword.isEmpty()) {
            // No keyword provided, show all products
            products = productService.getAllProducts();
        } else {
            // Search by keyword (product name or category name)
            products = productService.searchProducts(keyword);
            if (products == null || products.isEmpty()) {
                // If no matching products are found, show all products
                products = productService.getAllProducts();
            }
        }

        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword); // Retain the search keyword
        return "BuyerdashboardExtend"; // Stay on the demo page
    }

    // Post mapping for search on any page
    @PostMapping("/search")
    public String searchProducts(@RequestParam("keyword") String keyword, Model model) {
        List<Product> products;

        if (keyword == null || keyword.isEmpty()) {
            // No keyword provided, show all products
            products = productService.getAllProducts();
        } else {
            // Search by keyword (product name or category name)
            products = productService.searchProducts(keyword);
            if (products == null || products.isEmpty()) {
                // If no matching products are found, show all products
                products = productService.getAllProducts();
            }
        }

        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword); // Retain the search keyword

        // You can decide where to redirect based on which page the search is performed on
        return "BuyerdashboardExtend"; // Render the results on a common search results page
    }
}
