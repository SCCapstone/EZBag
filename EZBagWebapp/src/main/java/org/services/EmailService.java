package org.services;

import com.sun.mail.smtp.SMTPMessage;

import javax.activation.DataHandler;
import javax.imageio.ImageIO;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

public class EmailService {

    public static String from = "";
    public static String password = "";
    public static boolean didInit = false;

    public static class AsyncEmail implements Runnable {

        private String to;
        private String subject;
        private String messageText;

        public AsyncEmail(String to, String subject, String messageText) {
            this.to = to;
            this.subject = subject;
            this.messageText = messageText;
        }

        public void run() {
            System.out.println("Sending an email to: " + to);
            System.out.println("\tSubject: " + subject);
            System.out.println("\tText: " + messageText);
            if(!didInit){
                System.out.println("[EmailService] Warning, email service has not been initialized. Email not sent");
                return;
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
            } catch (MessagingException mex) {
                mex.printStackTrace();
            }
        }
    }

    public static class AsyncEmailWithContent implements Runnable {

        private String to;
        private String subject;
        private String messageText;
        private BufferedImage image;

        public AsyncEmailWithContent(String to, String subject, String messageText, BufferedImage image) {
            this.to = to;
            this.subject = subject;
            this.messageText = messageText;
            this.image = image;
        }

        public void run() {
            System.out.println("Sending an email to: " + to);
            System.out.println("\tSubject: " + subject);
            System.out.println("\tText: " + messageText);
            if(!didInit){
                System.out.println("[EmailService] Warning, email service has not been initialized. Email not sent");
                return;
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
                MimeMultipart content = new MimeMultipart("related");

                String cid = String.format("%s-%s", "img", UUID.randomUUID());

                MimeBodyPart htmlPart = new MimeBodyPart();
                htmlPart.setText(""
                                + "<html>"
                                + " <body>"
                                + "  <p>" + messageText + "</p>"
                                + "  <img src=\"cid:" + cid + "\" />"
                                + " </body>"
                                + "</html>"
                        ,"US-ASCII", "html");
                content.addBodyPart(htmlPart);

                MimeBodyPart imagePart = new MimeBodyPart();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "png", baos);
                baos.flush();
                byte[] imageBytes= baos.toByteArray();
                baos.close();

                ByteArrayDataSource bds = new ByteArrayDataSource(imageBytes, "image/png");
                imagePart.setDataHandler(new DataHandler(bds));
                imagePart.setFileName("./phill.png");
                imagePart.setHeader("Content-ID", "<image>");
                imagePart.setContentID("<" + cid + ">");
                imagePart.setDisposition(MimeBodyPart.INLINE);
                content.addBodyPart(imagePart);

                SMTPMessage m = new SMTPMessage(session);
                m.setFrom(new InternetAddress(from));
                m.setContent(content);
                m.setSubject(subject);
                m.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
                Transport.send(m);
            } catch (MessagingException | IOException mex) {
                mex.printStackTrace();
            }
            return;
        }
    }

    public static void init(Properties properties)
    {
        System.out.println("[EmailService] Initializing credentials");
        from = properties.getProperty("email");
        password = properties.getProperty("emailPassword");
        System.out.println("[EmailService] Done");
        didInit = true;
    }

    public static int sendEmail(String to, String subject, String messageText) {
        AsyncEmail asyncEmail = new AsyncEmail(to, subject, messageText);
        Thread emailThread = new Thread(asyncEmail);
        emailThread.start();
        return 1;
    }

    public static int sendEmailWithContent(String to, String subject, String messageText, BufferedImage image) {
        AsyncEmailWithContent asyncEmailWithContent = new AsyncEmailWithContent(to, subject, messageText, image);
        Thread emailThread = new Thread(asyncEmailWithContent);
        emailThread.start();
        try {
            emailThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
