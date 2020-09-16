package org.services;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.database.MongoDB;
import org.bson.Document;

public class DatabaseService {
    public static MongoDB database = null;
    public static String getByProductCode(String productCode)
    {
        if(Utils.isEAN(productCode))
            return getByEAN(productCode);
        else if(Utils.isUPC(productCode))
            return getByUPC(productCode);
        else{
            System.out.println("[DatabaseService] ERROR: product code is not UPC or EAN: " + productCode);
            return null;
        }
    }
    private static String getByEAN(String barcode)
    {
        return database.getByEAN(barcode);
    }
    private static String getByUPC(String barcode)
    {
        return database.getByUPC(barcode);
    }
    public static String insertInfo(Document customerInfo) {
        String resp = database.insertInfo(customerInfo);
        System.out.println("Inserted info document");
        return resp;
    }
    public static String insertCustomerEvent(Document customerScanEvent) {
        String resp = database.insertCustomerScannedItemEvent(customerScanEvent);
        System.out.println("Inserted customer event");
        return resp;
    }
    public static String insertCustomerCheckoutCart(Document customerCheckoutCart) {
        String resp = database.insertCustomerCheckoutCart(customerCheckoutCart);
        System.out.println("Inserted customer checkout cart");
        return resp;
    }
    public static String insertProduct(Document newProduct) {
        String resp = database.insertProduct(newProduct);
        System.out.println("Inserted new product");
        return resp;
    }

}
