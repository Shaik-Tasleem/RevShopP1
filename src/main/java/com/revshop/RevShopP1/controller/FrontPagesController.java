package com.revshop.RevShopP1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/ecom")
public class FrontPagesController {
	@GetMapping("/LoginPage")
	public String loginPage(Model model) {
		return "LoginPage";
	}
	@PostMapping("/seller/handleLogin")
	public String sellerLogin(@RequestParam String email, @RequestParam  String mobileNumber, @RequestParam String password,Model model) {
        if (email != null) {
            System.out.println("Email: " + email);
        } else {
            System.out.println("Mobile Number: " + mobileNumber);
        }
        System.out.println("Password: " + password);
        return "redirect:/dashboard";
	}
}
