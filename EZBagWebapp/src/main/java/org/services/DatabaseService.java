package org.services;

import org.database.MongoDB;

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
    public static String getByEAN(String barcode)
    {
        return database.getByEAN(barcode);
    }
    public static String getByUPC(String barcode)
    {
        return database.getByUPC(barcode);
    }
}
