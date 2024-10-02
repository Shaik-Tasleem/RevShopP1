package com.revshop.RevShopP1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ecom")
public class FrontPagesController {

    @GetMapping("/LoginPage")
    public String loginPage(Model model) {
        return "LoginPage"; // This returns the LoginPage.html view
    }

    @GetMapping
    public String frontPage(Model model) {
        return "indexPage"; // This returns the indexPage.html view
    }

    @GetMapping("/welcomepage")
    public String welcomePage(Model model) {
        return "welcomepage"; // This returns the welcomepage.html view
    }

    // Mapping for emptyCart.html
    @GetMapping("/emptyCart")
    public String emptyCartPage(Model model) {
        return "emptyCart"; // This returns the emptyCart.html view
    }
}
