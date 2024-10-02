package com.revshop.RevShopP1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.revshop.RevShopP1.model.Buyer;
import com.revshop.RevShopP1.model.Seller;
import com.revshop.RevShopP1.service.BuyerService;
import com.revshop.RevShopP1.service.SellerService;

@Controller
@RequestMapping("/ecom")
public class SellerController {
	@Autowired
	private SellerService sellerService;
	
	public String registrationForm(Model model) {
		model.addAttribute("seller", new Seller());
		return "SellerDashboard";
	}
	
	public String registration(@ModelAttribute Seller seller) {
		sellerService.insertSeller(seller);
		return "SellerDashboard";
	}
}