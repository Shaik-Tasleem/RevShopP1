package com.revshop.RevShopP1.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.revshop.RevShopP1.model.Category;
import com.revshop.RevShopP1.model.Product;
import com.revshop.RevShopP1.service.CategoryService;
import com.revshop.RevShopP1.service.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/add")
    public String showAddProductForm(Model model) {
    	List<Category> categories = categoryService.getAllCat();
    	model.addAttribute("categories",categories);
        model.addAttribute("product", new Product());
        return "selleraddpro"; // Return the name of the HTML template for adding products
    }

    @PostMapping("/add")
    public String createProduct(@ModelAttribute Product product, @RequestParam int categoryId) {
        // Assuming you have a service to fetch the category
        Category category = categoryService.getCatBtId(categoryId);
        product.setCategory(category); // Set the category
       

        productService.addProduct(product); // Save the product
        return "redirect:/products"; // Redirect after successful save
    }

}
