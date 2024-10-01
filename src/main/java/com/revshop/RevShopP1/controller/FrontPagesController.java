package com.revshop.RevShopP1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.revshop.RevShopP1.model.Buyer;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/ecom")
public class FrontPagesController {
	@GetMapping("/LoginPage")
	public String loginPage(Model model) {
		return "LoginPage";
	}
	@GetMapping
	public String frontPage(Model model) {
		return "indexPage";
	}
	@GetMapping("/welcomepage")
	public String welcomePage(Model model) {
		return "welcomepage";
	}
}
