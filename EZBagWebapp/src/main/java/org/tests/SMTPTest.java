package org.tests;

import org.junit.Test;
import org.services.EmailService;
import org.services.QRCodeService;
import org.services.Utils;

import java.awt.image.BufferedImage;
import java.util.Properties;

import static junit.framework.TestCase.assertEquals;

public class SMTPTest {
    public static String mediaProperties = "/usr/local/opt/EZBag/emailAndSMS.properties";
    @Test
    public void testSMTP()
    {
        Properties mediaProp = Utils.getPropertiesFile(mediaProperties);
        EmailService.init(mediaProp);
        BufferedImage qrCode = null;
        try {
            qrCode = QRCodeService.generateQRCodeImage("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        int responseCode = EmailService.sendEmailWithContent("brendanreidy16@gmail.com", "Testing", "Hello world!", qrCode);
        //int responseCode = EmailService.sendEmail("bcreidy@email.sc.edu", "Testing", mailContext);
        //assertEquals(responseCode, 1);
    }
}
