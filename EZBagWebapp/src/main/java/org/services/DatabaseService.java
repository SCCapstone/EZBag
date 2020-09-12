package org.services;

import org.database.MongoDB;

public class DatabaseService {
    public static MongoDB database = null;
    public static void getByProductCode(String productCode)
    {
        database.getByBarcode(productCode);
    }
}
