package com.revshop.RevShopP1.service;

<<<<<<< HEAD
=======
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
>>>>>>> 1e3195eb68ab3b49105dfd6e9ccc14ae49a248a8
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
<<<<<<< HEAD

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // Send plain text email without MimeMessage
    public void sendVerificationEmail(String to, String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Password Reset Verification Code");
        message.setText("Your verification code is: " + verificationCode);

        mailSender.send(message);
    }
=======
	@Autowired
    private JavaMailSender javaMailSender;

    private final Map<String, String> otpStorage = new ConcurrentHashMap<>();  // Thread-safe storage for OTPs

    // Method to generate random OTP
    public String generateOtp() {
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000);  // Generate 6 digit OTP
        System.out.println("Generated OTP: " + otp);  // Log the generated OTP
        return String.valueOf(otp);
    }

    // Method to send email
    public boolean sendEmail(String toEmail, String otp) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Your OTP Code");
            message.setText("Your OTP for registration is: " + otp);

            javaMailSender.send(message);

            // Store OTP for later verification
            otpStorage.put(toEmail, otp);
            System.out.println("Stored OTP for " + toEmail + ": " + otp);  // Log the stored OTP
            return true;
        } catch (MailException e) {
            e.printStackTrace(); // Log the exception for debugging
            return false;
        }
    }

    // Method to verify OTP
    public boolean verifyOtp(String email, String inputOtp) {
        String storedOtp = otpStorage.get(email);
        System.out.println("Verifying OTP for " + email + ": Entered OTP = " + inputOtp + ", Stored OTP = " + storedOtp); // Log the verification details
        return storedOtp != null && storedOtp.equals(inputOtp);
    }
	 
>>>>>>> 1e3195eb68ab3b49105dfd6e9ccc14ae49a248a8
}
