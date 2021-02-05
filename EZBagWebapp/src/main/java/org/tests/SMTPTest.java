package org.tests;

import org.junit.Test;
import org.services.EmailService;
import org.services.Utils;

import java.util.Properties;

import static junit.framework.TestCase.assertEquals;

public class SMTPTest {
    public static String mediaProperties = "/usr/local/opt/EZBag/emailAndSMS.properties";
//    @Test
//    public void testSMTP()
//    {
//        Properties mediaProp = Utils.getPropertiesFile(mediaProperties);
//        EmailService.init(mediaProp);
//        int responseCode = EmailService.sendEmail("emailservicetest1234123@gmail.com", "Testing", "Test email");
//        assertEquals(responseCode, 1);
//    }
}
