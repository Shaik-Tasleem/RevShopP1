package com.revshop.RevShopP1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.revshop.RevShopP1.model.Category;
import com.revshop.RevShopP1.service.CategoryService;


@Controller
@RequestMapping("/api/category")
public class CategoryController {
	@Autowired
	private CategoryService service;
	@PostMapping
	public ResponseEntity<Category> addCat(@RequestBody Category category){
		service.insertCategory(category);
		return new ResponseEntity<>(category,HttpStatus.CREATED);
	}
	@GetMapping
	public ResponseEntity<List<Category>> getAllCat(){
		return new ResponseEntity<>(service.getAllCat(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Category> getCatById(@PathVariable Long id){
		return new ResponseEntity<>(service.getCatBtId(id), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Category> updateCat(@PathVariable Long id, @RequestBody Category category){
		service.insertCategory(category);
		return new ResponseEntity<>(category, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCat(@PathVariable Long id){
		service.deleteCat(id);
		return new ResponseEntity<>("deleted Successfully", HttpStatus.OK);
	}
	@GetMapping("/cats")
	public String showcats(Model model) {
		List<Category> cat=service.getAllCat();
		model.addAttribute("cat", cat);
		return "category";
	}
	@PostMapping("/select")
    public String selectcategory(@RequestParam String categoryName, Model model) {
        List<Category> category = service.getCatByName(categoryName);
        if (category != null) {
            model.addAttribute("category",category);
        } else {
            model.addAttribute("error", "category not found");
        }
        return "categoryDetails"; 
    }

}
