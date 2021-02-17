package org.services;

import org.database.MongoDB;
import org.bson.Document;
import sun.font.TrueTypeFont;

public class DatabaseService {
    public static String SECRET_KEY = null;
    public static MongoDB database = null;
    // TODO change to support "ean8", "ean13", "upca", "upce"
    public static String getProductByBarcodeBusinessID(String barcode, String businessID) {
        Document returnedProduct = database.getProductByBarcodeBusinessID(barcode, businessID);
        if (returnedProduct != null) {
            return returnedProduct.toJson();
        }
        String message = "Product was not found in the database";
        return Utils.generateResponse(false, message);
    }

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

    public static Document getCustomerCartByHash(String cartHash) {
        Document returnedProduct = database.getCustomerCartByHash(cartHash);
        if (returnedProduct != null) {
            return returnedProduct;
        }
        return null;
    }

    // used to check if user with email exists
    public static Boolean userExists(String userEmail) {
        return database.userExists(userEmail);
    }

    public static Boolean userLoginCredentialsValid(String userEmail, String userPassword) {
        Document returnedUser = database.getUserByEmailPassword(userEmail, userPassword);
        if (returnedUser != null) {
            return true;
        }
        return false;
    }

    public static String insertInfo(Document customerInfo) {
        System.out.println("Inserting info document");
        return Utils.generateResponse(database.insertInfo(customerInfo), customerInfo.toJson());
    }
    public static String insertCustomerEvent(Document customerScanEvent) {
        System.out.println("Inserting customer event");
        return Utils.generateResponse(database.insertCustomerScannedItemEvent(customerScanEvent), customerScanEvent.toJson());
    }
    public static String insertCustomerCheckoutCart(Document customerCheckoutCart) {
        System.out.println("Inserting customer checkout cart");
        return Utils.generateResponse(database.insertCustomerCheckoutCart(customerCheckoutCart), customerCheckoutCart.toJson());
    }
    public static String insertProduct(Document newProduct) {
        System.out.println("Inserting new product");
        return Utils.generateResponse(database.insertProduct(newProduct), newProduct.toJson());
    }
    public static String insertUser(Document newUser) {
        System.out.println("Inserting new use");
        return Utils.generateResponse(database.insertUser(newUser), newUser.toJson());
    }
}
