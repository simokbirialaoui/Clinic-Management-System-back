package com.javatechie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendResetToken(String toEmail, String token) {
        String resetUrl = "http://localhost:4200/auth/reset-password?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Réinitialisation du mot de passe");
        message.setText("Cliquez sur le lien pour réinitialiser votre mot de passe : " + resetUrl);

        mailSender.send(message);
    }
}
