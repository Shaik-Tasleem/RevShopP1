package com.revshop.RevShopP1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.revshop.RevShopP1.model.Buyer;
import com.revshop.RevShopP1.service.BuyerService;

@Controller
@RequestMapping("/ecom")
public class BuyerController {
	@Autowired
	private BuyerService buyerService;
	@GetMapping("/buyerRegistration")
	public String registrationForm(Model model) {
		model.addAttribute("buyer", new Buyer());
		return "BuyerRegistration";
	}
	@PostMapping("/buyerRegistration")
	public String registration(@ModelAttribute Buyer buyer) {
		buyerService.insertBuyer(buyer);
		return "BuyerRegistration";
	}
}
