package org.services;

import com.google.gson.JsonObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Utils {
    public static String generateResponse(Boolean success, String message) {
        JsonObject resp = new JsonObject();
        resp.addProperty("message", message);
        if (success)
        {
            resp.addProperty("status", "success");
            return resp.toString();
        }
        resp.addProperty("status", "failure");
        return resp.toString();
    }
    public static boolean isEAN(String barcode)
    {
        return (barcode.length()==13 || barcode.length()==8);
    }
    public static boolean isUPC(String barcode)
    {
        return barcode.length()==12;
    }

    public static Properties getPropertiesFile(String fileName)
    {
        FileInputStream fis = null;
        Properties prop = null;
        try {
            fis = new FileInputStream(fileName);
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
        return prop;
    }

}
