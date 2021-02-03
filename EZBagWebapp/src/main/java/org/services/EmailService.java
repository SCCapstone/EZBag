package org.services;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {

    public static String from = "";
    public static String password = "";
    public static boolean didInit = false;

    public static void init(Properties properties)
    {
        System.out.println("[EmailService] Initializing credentials");
        from = properties.getProperty("email");
        password = properties.getProperty("emailPassword");
        System.out.println("[EmailService] Done");
        didInit = true;
    }

    public static int sendEmail(String to, String subject, String messageText) {
        System.out.println("Sending an email to: " + to);
        System.out.println("\tSubject: " + subject);
        System.out.println("\tText: " + messageText);
        if(!didInit){
            System.out.println("[EmailService] Warning, email service has not been initialized. Email not sent");
            return 3;
        }
        String host = "smtp.gmail.com";
        //*
        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(from, password);
            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
            message.setText(messageText);
            // Send message
            Transport.send(message);
            return 1;
        } catch (MessagingException mex) {
            mex.printStackTrace();
            return 0;
        }
        //*/
    }
}
