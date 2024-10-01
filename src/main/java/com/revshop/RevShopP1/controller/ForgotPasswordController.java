package com.revshop.RevShopP1.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.revshop.RevShopP1.service.ForgotService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/ecom")
public class ForgotPasswordController {
	@Autowired
	private ForgotService forgotService;
	@PostMapping("/api/send-verification/forgot")
    @ResponseBody
    public String sendVerificationCode(@RequestParam("email") String email, HttpSession session) {
        // Generate a random 6-digit code
        String verificationCode = String.format("%06d", new Random().nextInt(999999));

        try {
            // Send the verification email
        	forgotService.sendVerificationEmail(email, verificationCode);
            // Save the code and email in session
            session.setAttribute("verificationCode", verificationCode);
            session.setAttribute("email", email);
            return "Verification code sent to your email.";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @PostMapping("/api/verify-code/forgot")
    @ResponseBody
    public String verifyCode(@RequestParam("code") String code, HttpSession session) {
        String sessionCode = (String) session.getAttribute("verificationCode");
        
        if (sessionCode != null && sessionCode.equals(code)) {
            return "Code verified successfully.";
        } else {
            return "Invalid verification code.";
        }
    }

    @PostMapping("/api/reset-password/forgot")
    public String resetPassword(
            @RequestParam("new-password") String newPassword, 
            @RequestParam("confirm-password") String confirmPassword, 
            HttpSession session, 
            Model model) {

        // Validate password match
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            return "forgot-password";  // return to the reset form
        }

        // Retrieve the email from the session
        String email = (String) session.getAttribute("email");

        if (email == null) {
            model.addAttribute("error", "Session expired, please try again.");
            return "forgot-password"; // return to the reset form
        }

        // Update the user's password (this is an example, you should hash the password)
        // userService.updatePassword(email, newPassword);

        model.addAttribute("message", "Password reset successfully.");
        return "login";  // Redirect to the login page after success
    }
}
