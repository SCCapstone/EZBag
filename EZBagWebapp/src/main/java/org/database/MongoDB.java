package org.database;

import com.google.gson.JsonObject;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import static com.mongodb.client.model.Filters.eq;


public class MongoDB {
    private MongoDatabase database;
    private HashMap<String, MongoCollection<Document>> collectionsMap;

    private String host;
    private String port;
    private String databaseName;
    private String user;
    private String password;
    private String infoCollectionName;
    private String productCollectionName;
    private String eventsCollectionName;
    private String checkoutCartCollectionName;
    public MongoDB(Properties prop)
    {
        System.out.println("[MongoDB] Loading database properties");
        host = prop.getProperty("host");
        port = prop.getProperty("port");
        databaseName = prop.getProperty("database");
        user = prop.getProperty("user");
        password = prop.getProperty("password");
        collectionsMap = new HashMap<String, MongoCollection<Document>>();
        productCollectionName = prop.getProperty("productCollection");
        infoCollectionName = prop.getProperty("infoCollection");
        eventsCollectionName = prop.getProperty("eventCollection");
        checkoutCartCollectionName = prop.getProperty("cartCollection");
        init();
    }
    public void init()
    {
        System.out.print("[MongoDB] Establishing connection...");
        MongoClient mongo = new MongoClient(host, Integer.parseInt(port));
        System.out.print(" done\n");
        System.out.print("[MongoDB] Fetching database...");
        MongoDatabase aDatabase = mongo.getDatabase(databaseName);
        this.database = aDatabase;
        System.out.print(" done\n");
        System.out.print("[MongoDB] Fetching collections...");
        collectionsMap.put(productCollectionName, database.getCollection(productCollectionName));
        collectionsMap.put(infoCollectionName, database.getCollection(infoCollectionName));
        collectionsMap.put(eventsCollectionName, database.getCollection(eventsCollectionName));
        collectionsMap.put(checkoutCartCollectionName, database.getCollection(checkoutCartCollectionName));
        System.out.print("[MongoDB] initialization done\n");
    }
    public Document getProductByBarcodeBarcodeTypeBusinessID(String barcode, String barcodeType, String businessID) {
        BasicDBObject query = new BasicDBObject();
        List<BasicDBObject> matchDoc = new ArrayList<BasicDBObject>();
        matchDoc.add(new BasicDBObject("barcode", barcode));
        matchDoc.add(new BasicDBObject("barcodeType", barcodeType));
        matchDoc.add(new BasicDBObject("businessID", businessID));
        query.put("$and", matchDoc);
        Document respDoc = collectionsMap.get(productCollectionName).find(query).first();
        if (respDoc != null) {
            return respDoc;
        }
        return null;
    }

    // method to check whether the given document already exists within a collection collection
    private Boolean documentExistsInCollection(MongoCollection<Document> collection, Document insertDoc, String idField) {
        Document respDoc = collection.find(eq(idField, insertDoc.getString(idField))).first();
        if (respDoc != null) {
            return false;
        }
        return true;
    }

    public String getByEAN(String barcode) {
        Document myDoc = collectionsMap.get(productCollectionName).find(eq("ean", barcode)).first();
        if (myDoc != null) {
            return myDoc.toJson();
        }
        JsonObject resp = new JsonObject();
        resp.addProperty("message", "ProductInsertRoute not found");
        return resp.toString();
    }

    public String getByUPC(String barcode) {
        Document myDoc = collectionsMap.get(productCollectionName).find(eq("upc", barcode)).first();
        if (myDoc != null) {
            return myDoc.toJson();
        }
        JsonObject resp = new JsonObject();
        return resp.toString();
    }
    // todo check if insert was actually successful for all insert methods
    public Boolean insertInfo(Document customerInfo) {
        // only insert document if it does not exist
        if (!(documentExistsInCollection(collectionsMap.get(infoCollectionName), customerInfo, "phone")
                || documentExistsInCollection(collectionsMap.get(infoCollectionName), customerInfo, "email")))
        {
            collectionsMap.get(infoCollectionName).insertOne(customerInfo);
            return true;
        }
        return false;
    }
    public Boolean insertCustomerScannedItemEvent(Document customerScannedItemEvent) {
        collectionsMap.get(eventsCollectionName).insertOne(customerScannedItemEvent);
        return true;
    }
    public Boolean insertCustomerCheckoutCart(Document customerCheckoutCart) {
        collectionsMap.get(checkoutCartCollectionName).insertOne(customerCheckoutCart);
        return true;
    }
    public Boolean insertProduct(Document newProduct) {
        // check if product already exists
        Document resp = getProductByBarcodeBarcodeTypeBusinessID(newProduct.getString("barcode"),
                newProduct.getString("barcodeType"),
                newProduct.getString("businessID"));
        if (resp == null) {
            collectionsMap.get(productCollectionName).insertOne(newProduct);
            return true;
        }
        return false;
    }
}
