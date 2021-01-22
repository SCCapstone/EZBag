package org.services;

import org.database.MongoDB;
import org.bson.Document;

public class DatabaseService {
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

    // TODO: method for getting product price by barcode type and business ID
//    public static String getProductPriceByBarcodeBarcodeTypeBusinessID(String barcode, String barcodeType, businessID) {
//        Double returnedPrice = 0.0;
//        returnedPrice = database.getProductPriceByBarcodeBarcodeTypeBusinessID(barcode, barcodeType, businessID);
//        if (returnedPrice == -1.0) {
//            return
//        }
//    }

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

    public static String insertInfo(Document customerInfo) {
        System.out.println("Insert info document");
        return Utils.generateResponse(database.insertInfo(customerInfo), customerInfo.toJson());
    }
    public static String insertCustomerEvent(Document customerScanEvent) {
        System.out.println("Inserted customer event");
        return Utils.generateResponse(database.insertCustomerScannedItemEvent(customerScanEvent), customerScanEvent.toJson());
    }
    public static String insertCustomerCheckoutCart(Document customerCheckoutCart) {
        System.out.println("Inserted customer checkout cart");
        return Utils.generateResponse(database.insertCustomerCheckoutCart(customerCheckoutCart), customerCheckoutCart.toJson());
    }
    public static String insertProduct(Document newProduct) {
        System.out.println("Inserted new product");
        return Utils.generateResponse(database.insertProduct(newProduct), newProduct.toJson());
    }

}
