package org.services;

import org.database.MongoDB;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class StartupService {
    public static String propertiesFile = "/usr/local/opt/EZBag/EZBag.properties";
    public static void startup()
    {
        System.out.println("[Startup] Loading properties file");
        FileInputStream fis = null;
        Properties prop = null;
        try {
            fis = new FileInputStream(propertiesFile);
            prop = new Properties();
            prop.load(fis);
        } catch(FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("[Startup] Connecting to database");
        MongoDB mongo = new MongoDB(prop);
        DatabaseService.database = mongo;
    }
}
