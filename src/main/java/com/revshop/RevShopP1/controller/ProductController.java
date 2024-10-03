package com.revshop.RevShopP1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.revshop.RevShopP1.model.Category;
import com.revshop.RevShopP1.model.Product;
import com.revshop.RevShopP1.model.Seller;
import com.revshop.RevShopP1.service.CategoryService;
import com.revshop.RevShopP1.service.ProductService;
import com.revshop.RevShopP1.service.SellerService;

import jakarta.servlet.http.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SellerService sellerService;
    // Home or dashboard
    @GetMapping("/dashboard") // Change this if needed
    public String fromdashboard() {
        return "Buyerdashboard"; // Return the name of the detail view
    }
    @GetMapping("/welcome") // Change this if needed
    public String welcome() {
        return "welcomepage"; // Return the name of the detail view
    }

    // When a particular item is selected from search results
    @GetMapping("/{id}")
    public String showProduct(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        
        List<Product> availableProducts = productService.getAllProducts();
        model.addAttribute("availableProducts", availableProducts);
        return "products"; // Return the name of the detail view
    }
    
    // Displaying the products as per category id from buyer dashboard
    @GetMapping("/category/{categoryId}")
    public String showProductsByCategory(@PathVariable Long categoryId, Model model) {
        List<Product> products = productService.getProductsByCategoryId(categoryId);
        model.addAttribute("products", products);
        return "BuyerdashboardExtend";  // HTML page to display the products
    }

    // Seller check
    @GetMapping("/dashboard/seller") // Optional: Change as necessary
    public String showDashboard(Model model) {
        return "SellerDashboard"; // Return the seller dashboard view
    }

    // Show the add product form based on category ID
    @GetMapping("/add")
    public String showAddProductForm(HttpServletRequest request, @RequestParam("categoryId") Long categoryId, Model model) {
        Category category = categoryService.getCategoryById(categoryId); // Fetch the category by ID
        model.addAttribute("category", category);
        String sellerId=null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("sellerId")) {
                	sellerId = cookie.getValue();
                    break;
                }
            }
        }
        if(sellerId==null) {
        	return "redirect:/ecom/LoginPage";
        }
        Seller seller=sellerService.findById(Long.parseLong(sellerId));
        model.addAttribute("seller",seller);
        model.addAttribute("product", new Product()); // Add an empty product object for the form
        return "selleraddpro"; // Return the add product form view
    }

    // Save product in the selected category
    @PostMapping("/save")
    public String saveProduct(@ModelAttribute Product product, @RequestParam("categoryId") Long categoryId,@RequestParam("sellerId") Long sellerId) {
        // Fetch the category by ID and set it to the product
    	System.out.println(product.getSeller());
    	System.out.println(product.getCategory());
        Category category = categoryService.getCategoryById(categoryId);
        Seller seller=sellerService.findById(sellerId);
        product.setSeller(seller);
        product.setCategory(category);
        
//
//        // Save the product
        productService.save(product);
        
        // Redirect back to the dashboard after saving
        return "SellerDashboard";
    }
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        try {
            productService.deleteProductById(productId); // Assuming this service method deletes the product
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete product.");
        }
    }
    @GetMapping("/edit/{productId}")
    public String showUpdateForm(@PathVariable("productId") Long productId, Model model) {
        Product product = productService.getProductById(productId);
        model.addAttribute("product", product);
        return "updateProduct"; // This will load the updateProduct.html page
    }
    @PostMapping("/update/{productId}")
    public String updateProduct(@PathVariable("productId") Long productId, @ModelAttribute("product") Product updatedProduct) {
        productService.updateProduct(productId, updatedProduct);
        return "redirect:/ecom/manage"; // Redirect back to manage products after update
    }
}
