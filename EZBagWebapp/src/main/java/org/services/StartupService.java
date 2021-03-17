package org.services;

import org.apache.commons.codec.digest.DigestUtils;
import org.bson.Document;
import org.database.MongoDB;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Properties;

@WebListener
public class StartupService implements ServletContextListener {
    public static String propertiesFile = "/usr/local/opt/EZBag/EZBag.properties";
    public static String mediaProperties = "/usr/local/opt/EZBag/emailAndSMS.properties";

    public static String debugUserName = "owner@store.com";
    public static String debugPassword = "password";
    public static String debugBusinessID = "179aa3e0fb88f6e4ec0ef0d0f5588d43f93713e7b7e4a5ddd8a3fdd1c39701fa";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        startup();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        // shutdown code can go here
        System.out.println("Shutting down!");
    }

    public static void startup()
    {
        long clock = System.currentTimeMillis();
        System.out.println("[Startup] Loading properties files");
        Properties prop = Utils.getPropertiesFile(propertiesFile);
        Properties mediaProp = Utils.getPropertiesFile(mediaProperties);
        System.out.println("[Startup] Connecting to database");
        MongoDB mongo = new MongoDB(prop);

        DatabaseService.database = mongo;
        ReceiptService.database = mongo;
        DatabaseService.SECRET_KEY = prop.getProperty("secretKey");
        System.out.println("Secret key: "+ prop.getProperty("secretKey"));
        System.out.println("[Startup] Initializing media services");
        EmailService.init(mediaProp);
        SMSService.init(mediaProp);
        if(!DatabaseService.userLoginCredentialsValid(debugUserName, debugPassword)){
            System.out.println("[Startup] Generating default user...");
            Document insertDoc = new Document();
            insertDoc.append("email", debugBusinessID);
            insertDoc.append("phone", "5555555555");
            insertDoc.append("businessName", "EZBag Dev Team");
            insertDoc.append("streetAddress", "1600 Pennsylvania Avenue");
            insertDoc.append("city", "");
            insertDoc.append("state", "");
            insertDoc.append("country", "United States");
            insertDoc.append("email", debugUserName);
            insertDoc.append("password", debugPassword);
            insertDoc.append("role", 1);
            insertDoc.append("businessID", debugBusinessID);
            insertDoc.append("verified", true);
            // TODO: change to reply with message of why the addition failed
            String success =  DatabaseService.insertUser(insertDoc);
            System.out.println(success);
        }
        System.out.println("[Startup] Server started successfully in " + (System.currentTimeMillis() - clock) + "ms");

    }
}
