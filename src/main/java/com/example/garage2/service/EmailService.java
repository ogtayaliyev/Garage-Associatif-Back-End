package com.example.garage2.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendConfirmationEmail(String to, String subject, String text) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(message);
    }

    public void sendNotificationEmail(String to, String subject, LocalDateTime startDate,Long duree,String plaqueImmatriculation) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);

            String contenuEmail = "Bonjour,\n\n"
                    + "Votre enregistrement de réparation a été créé avec succès. Voici les détails:\n\n"
                    + "- Heure de début de la réparation: " + startDate + "\n"
                    + "- Durée de la réparation: " + duree + "minutes"+"\n"
                    + "- plaqque immatriculation de votre voiture est: " + plaqueImmatriculation + "\n\n"
                    + "Veuillez prendre les mesures nécessaires pour arriver à l'heure pour la réparation.\n\n"
                    + "N'hésitez pas à nous contacter si vous avez des questions.\n\n"
                    + "Cordialement,\n"
                    + "[MNS Garage]";

            helper.setText(contenuEmail); // true indique que le texte est au format HTML si vous souhaitez le formater
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            // Gérer l'erreur d'envoi d'e-mail ici
        }
    }

    public void sendLocationNotificationEmail(String to, String subject, LocalDateTime startDate,LocalDateTime returnDate,float prix_loc) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);

            String contenuEmail = "Bonjour,\n\n"
                    + "Votre enregistrement de location a été créé avec succès. Voici les détails:\n\n"
                    + "- Heure de début de la location: " + startDate + "\n"
                    + "- Heure de Fin de la location: " + returnDate + " "+"\n"
                    + "- prix location  est: " + prix_loc + " "+"euros"+"\n\n"
                    + "Veuillez prendre les mesures nécessaires pour arriver à l'heure pour la réparation.\n\n"
                    + "N'hésitez pas à nous contacter si vous avez des questions.\n\n"
                    + "Cordialement,\n"
                    + "[MNS Garage]";

            helper.setText(contenuEmail); // true indique que le texte est au format HTML si vous souhaitez le formater
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            // Gérer l'erreur d'envoi d'e-mail ici
        }
    }


}
