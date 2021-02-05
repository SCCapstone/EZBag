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
    private String userCollectionName;
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
        userCollectionName = prop.getProperty("userCollection");
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
        System.out.print("[MongoDB] Fetching product collection...");
        collectionsMap.put(productCollectionName, database.getCollection(productCollectionName));
        System.out.print("[MongoDB] Fetching info collection...");
        collectionsMap.put(infoCollectionName, database.getCollection(infoCollectionName));
        System.out.print("[MongoDB] Fetching event collection...");
        collectionsMap.put(eventsCollectionName, database.getCollection(eventsCollectionName));
        System.out.print("[MongoDB] Fetching checkout collection...");
        collectionsMap.put(checkoutCartCollectionName, database.getCollection(checkoutCartCollectionName));
        System.out.print("[MongoDB] Fetching user collection...");
        collectionsMap.put(userCollectionName, database.getCollection(userCollectionName));
        System.out.print("[MongoDB] initialization done\n");
    }
    public Document getProductByBarcodeBusinessID(String barcode, String businessID) {
        BasicDBObject query = new BasicDBObject();
        List<BasicDBObject> matchDoc = new ArrayList<BasicDBObject>();
        matchDoc.add(new BasicDBObject("barcode", barcode));
        matchDoc.add(new BasicDBObject("businessID", businessID));
        query.put("$and", matchDoc);
        Document respDoc = collectionsMap.get(productCollectionName).find(query).first();
        if (respDoc != null) {
            return respDoc;
        }
        return null;
    }

    public Document getCustomerCartByHash(String cartHash) {
        BasicDBObject query = new BasicDBObject();
        List<BasicDBObject> matchDoc = new ArrayList<BasicDBObject>();
        matchDoc.add(new BasicDBObject("cartHash", cartHash));
        query.put("$and", matchDoc);
        Document respDoc = collectionsMap.get(checkoutCartCollectionName).find(query).first();
        if (respDoc != null) {
            return respDoc;
        }
        return null;
    }

    // method to check whether the given document already exists within a collection collection
    private Boolean documentExistsInCollection(String collectionName, String idField, String value) {
        BasicDBObject query = new BasicDBObject();
        List<BasicDBObject> matchDoc = new ArrayList<BasicDBObject>();
        matchDoc.add(new BasicDBObject(idField, value));
        query.put("$and", matchDoc);
        Document respDoc = collectionsMap.get(collectionName).find(query).first();
        if (respDoc != null) {
            System.out.println(respDoc.toString());
            return true;
        }
        return false;
    }

    public Boolean userExists(String userEmail) {
        return documentExistsInCollection(userCollectionName, "email", userEmail);
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
        if (!(documentExistsInCollection(infoCollectionName,"hash", customerInfo.getString("hash"))))
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
        Document resp = getProductByBarcodeBusinessID(newProduct.getString("barcode"),
                newProduct.getString("businessID"));
        if (resp == null) {
            collectionsMap.get(productCollectionName).insertOne(newProduct);
            return true;
        }
        return false;
    }
    public Boolean insertUser(Document newUser) {
        // only insert document if it does not exist
        if (!(documentExistsInCollection(userCollectionName,"email", newUser.getString("email"))))
        {
            collectionsMap.get(userCollectionName).insertOne(newUser);
            return true;
        }
        return false;
    }
}
