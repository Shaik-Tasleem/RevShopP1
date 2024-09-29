package com.revshop.RevShopP1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.revshop.RevShopP1.model.Buyer;
import com.revshop.RevShopP1.service.BuyerService;

import jakarta.servlet.http.HttpSession;

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
		String pwd=buyer.getPassword();
		buyerService.insertBuyer(buyer);
		return "BuyerRegistration";
	}
	@PostMapping("/buyer/handleLogin")
	public String buyerLogin(@RequestParam(required=false) String email, @RequestParam(required=false)  String mobileNumber, @RequestParam String password,Model model,HttpSession session) {
		if (email != null) {
			Buyer buyer_obj=buyerService.getBuyerDetailsByEmail(email);
			if(buyer_obj==null || !buyer_obj.getPassword().equals(password)) {
				String msg="Invalid Email or Password...\nIf you are a new user Kindly...Register to access our Services..";
				model.addAttribute("errorMessage", msg);
				return "LoginPage";
			}
			else {
				session.setAttribute("buyer", buyer_obj);
				return "LoginPage";
			}
        } else {
            Buyer buyer_obj=buyerService.getBuyerDetailsByMobileNumber(mobileNumber);
            if(buyer_obj==null || !buyer_obj.getPassword().equals(password)) {
				String msg="Invalid Email or Password...\nIf you are a new user Kindly...Register to access our Services..";
				model.addAttribute("errorMessage", msg);
				return "LoginPage";
			}
			else {
				session.setAttribute("buyer", buyer_obj);
				return "LoginPage";
			}
        }
   
	}
}
