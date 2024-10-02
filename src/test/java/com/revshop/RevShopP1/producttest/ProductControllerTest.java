package com.revshop.RevShopP1.producttest;



import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.revshop.RevShopP1.controller.ProductController;
import com.revshop.RevShopP1.model.Category;
import com.revshop.RevShopP1.model.Product;
import com.revshop.RevShopP1.service.CategoryService;
import com.revshop.RevShopP1.service.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Mock
    private CategoryService categoryService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void testShowAddProductForm() throws Exception {
        List<Category> categories = Arrays.asList(new Category(1, "Electronics"), new Category(2, "Books"));
        when(categoryService.getAllCat()).thenReturn(categories);

        mockMvc.perform(get("/ecom/add"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("categories"))
                .andExpect(view().name("addProducts"));
        
        verify(categoryService, times(1)).getAllCat();
    }

    @Test
    void testCreateProduct() throws Exception {
        Product product = new Product();
        product.setProductName("Test Product");

        when(productService.addProduct(any(Product.class))).thenReturn(product);
        when(categoryService.getCatBtId(1)).thenReturn(new Category(1, "Electronics"));

        mockMvc.perform(post("/ecom/add")
                .param("productName", "Test Product")
                .param("categoryId", "1")
                .param("price", "100.00")
                .param("discountPrice", "10.00")
                .param("quantity", "50")
                .param("threshold", "10")
                .param("image", "image_url"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"));
        
        verify(productService, times(1)).addProduct(any(Product.class));
    }
}
