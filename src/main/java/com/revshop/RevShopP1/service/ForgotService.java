package com.revshop.RevShopP1.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ForgotService {

    private final JavaMailSender mailSender;

    public ForgotService(JavaMailSender mailSender) {
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
}