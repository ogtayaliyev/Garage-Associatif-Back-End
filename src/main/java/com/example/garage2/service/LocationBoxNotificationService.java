package com.example.garage2.service;

import com.example.garage2.entite.LocationBox;
import com.example.garage2.entite.Utilisateur;
import com.example.garage2.repository.LocationBoxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class LocationBoxNotificationService {

    private static final String SENDER_EMAIL = "ogtayaliyev9@gmail.com";
    private static final String SENDER_PASSWORD = "vujq itip conb lscz"; // Store securely!
    private static final String SMTP_HOST = "smtp.gmail.com"; // Replace with your SMTP server
    private static final String SMTP_PORT = "587"; // Common SMTP port for TLS

    @Autowired
    private LocationBoxRepository locationBoxRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void sendNotification() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime notificationDateTime = currentDateTime.plusDays(1).withHour(0).withMinute(0).withSecond(0);

        // Récupérer les locations qui nécessitent une notification 24 heures avant la startDate
        List<LocationBox> locations = locationBoxRepository.findLocationsForNotification(notificationDateTime, currentDateTime);

        for (LocationBox location : locations) {
            sendReservationNotification(location);
        }
    }

    private void sendReservationNotification(LocationBox location) {
        try {
            Utilisateur utilisateur = location.getUtilisateur();

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", SMTP_HOST);
            props.put("mail.smtp.port", SMTP_PORT);

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
                        }
                    });

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(utilisateur.getEmail())); // Replace with actual email address
            message.setSubject("Location Box Reservation Reminder");

            StringBuilder emailBody = new StringBuilder();
            emailBody.append("Dear " + utilisateur.getPrenom() + " " + utilisateur.getNom() + ",\n\n");
            emailBody.append("This is a friendly reminder that your reservation for location box ");
            emailBody.append(location.getBox().getSuperficie() + " (ID: " + location.getId() + ") starts tomorrow, ");
            emailBody.append(location.getStartDate().toString() + ".\n\n");
            emailBody.append("We hope you're excited to use the location box! Please refer to your reservation details for any additional instructions.\n\n");
            emailBody.append("Thank you for using our service.\n\n");
            emailBody.append("Sincerely,\n");
            emailBody.append("The Location Box Team");

            message.setContent(emailBody.toString(), "text/plain");

            Transport.send(message);
            System.out.println("Location box reservation notification sent for ID: " + location.getId());
        } catch (Exception e) {
            // Gérer les exceptions
            e.printStackTrace();
        }
    }
}

