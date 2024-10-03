package com.revshop.RevShopP1.controller;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.revshop.RevShopP1.model.Buyer;
import com.revshop.RevShopP1.service.BuyerService;
import com.revshop.RevShopP1.service.EmailService;
import com.revshop.RevShopP1.utils.PasswordUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/ecom")
public class BuyerController {
    @Autowired
    private BuyerService buyerService;
    @Autowired
    private PasswordUtils pwd_obj;

    @GetMapping("/buyerRegistration")
    public String registrationForm(Model model) {
        model.addAttribute("buyer", new Buyer());
        return "BuyerRegistration";
    }

    @PostMapping("/buyerRegistration")
    public String registration(@ModelAttribute Buyer buyer) throws NoSuchAlgorithmException {
        buyerService.insertBuyer(buyer);
        return "LoginPage";
    }

    @Autowired
    private EmailService emailService;

    @PostMapping("/api/send-verification")
    @ResponseBody
    public ResponseEntity<String> sendVerificationEmail(@RequestParam("email") String buyerEmail) {
        String otp = emailService.generateOtp();
        boolean emailSent = emailService.sendEmail(buyerEmail, otp);

        if (emailSent) {
            return ResponseEntity.ok("OTP sent successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send OTP.");
        }
    }

    @PostMapping("/api/verify-code")
    @ResponseBody
    public ResponseEntity<String> verifyOtp(@RequestParam("email") String buyerEmail,
            @RequestParam("code") String otp) {
        boolean isOtpValid = emailService.verifyOtp(buyerEmail, otp);

        if (isOtpValid) {
            return ResponseEntity.ok("OTP verified successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP.");
        }
    }

    @PostMapping("/buyer/handleLogin")
    public String buyerLogin(@RequestParam(required = false) String email,
                             @RequestParam(required = false) String mobileNumber,
                             @RequestParam String password, 
                             Model model,
                             HttpServletResponse response) throws NoSuchAlgorithmException {
        Buyer buyer_obj = null;

        if (email != null) {
            buyer_obj = buyerService.getBuyerDetailsByEmail(email);
        } else if (mobileNumber != null) {
            buyer_obj = buyerService.getBuyerDetailsByMobileNumber(mobileNumber);
        }

        if (buyer_obj == null || !buyer_obj.getPassword().equals(pwd_obj.hashPassword(password))) {
            String msg = "Invalid Email or Password...\nIf you are a new user Kindly...Register..\nTo access our Services..";
            model.addAttribute("errorMessage", msg);
            return "LoginPage";
        } else {
            Cookie buyerCookie = new Cookie("buyerId", buyer_obj.getBuyerId().toString());
            buyerCookie.setPath("/");
            buyerCookie.setMaxAge(24 * 60 * 60); 
            buyerCookie.setHttpOnly(true);
            response.addCookie(buyerCookie);

            return "redirect:/ecom/buyerdashboard";
        }
    }
}
