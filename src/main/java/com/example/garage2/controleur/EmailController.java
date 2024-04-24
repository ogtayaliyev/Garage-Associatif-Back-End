package com.example.garage2.controleur;

import com.example.garage2.dto.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin
@RestController
public class EmailController {

    @Autowired
    private JavaMailSender emailSender;

    @PostMapping("send-email")
    public void sendEmail(@RequestBody EmailRequest request) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(request.getEmail());
        message.setTo("ogtayaliyev9@gmail.com");
        message.setSubject(request.getSubject());
        message.setText(request.getMessage());

        emailSender.send(message);
    }
}