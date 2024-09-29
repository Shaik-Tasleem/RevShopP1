package com.revshop.RevShopP1.controller;

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
		return "indexPage";
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
	    public ResponseEntity<String> verifyOtp(@RequestParam("email") String buyerEmail, @RequestParam("code") String otp) {
	        boolean isOtpValid = emailService.verifyOtp(buyerEmail, otp);

	        if (isOtpValid) {
	            return ResponseEntity.ok("OTP verified successfully.");
	        } else {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP.");
	        }
	
	
	
	
	

}
	    
}
