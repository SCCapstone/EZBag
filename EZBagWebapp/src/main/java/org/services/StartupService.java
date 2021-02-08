package org.services;

import org.bson.Document;
import org.database.MongoDB;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

@WebListener
public class StartupService implements ServletContextListener {
    public static String propertiesFile = "/usr/local/opt/EZBag/EZBag.properties";
    public static String mediaProperties = "/usr/local/opt/EZBag/emailAndSMS.properties";

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
        System.out.println("[Startup] Initializing media services");
        EmailService.init(mediaProp);
        SMSService.init(mediaProp);
        System.out.println("[Startup] Server started successfully in " + (System.currentTimeMillis() - clock) + "ms");

    }
}
