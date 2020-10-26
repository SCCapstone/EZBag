package org.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import java.util.Properties;

public class SMSService {
    public static String OUT_PHONE_NUMBER = "";
    public static String ACCOUNT_SID = "";
    public static String AUTH_TOKEN = "";

    public static void init(Properties properties)
    {
        System.out.println("[SMSService] Initializing properties");
        ACCOUNT_SID = properties.getProperty("accountSID");
        AUTH_TOKEN = properties.getProperty("authToken");
        OUT_PHONE_NUMBER = properties.getProperty("smsNumber");
        System.out.println("[SMSService] Initializing Twilio");
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        System.out.println("[SMSService] Done");
    }

    public static void sendSMS(String phoneNumber, String message)
    {
        System.out.println("Sending a message to: " + phoneNumber);
        System.out.println("\t" + message);
        //*
        Message.creator(
                new com.twilio.type.PhoneNumber("+1" + phoneNumber),
                new com.twilio.type.PhoneNumber(OUT_PHONE_NUMBER),
                message)
                .create();
         //*/
    }
}
