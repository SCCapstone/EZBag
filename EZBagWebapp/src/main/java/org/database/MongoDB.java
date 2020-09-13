package org.database;

import com.google.gson.JsonObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Properties;

import static com.mongodb.client.model.Filters.eq;


public class MongoDB {
    private MongoDatabase database;
    private MongoCollection<Document> productCollection;
    private MongoCollection<Document> infoCollection;
    private MongoCollection<Document> eventCollection;
    private MongoCollection<Document> cartCollection;

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
        System.out.print("[MongoDB] Fetching productCollection...");
        MongoCollection<Document> aCollection = database.getCollection(productCollectionName);
        MongoCollection<Document> infoCollection = database.getCollection(infoCollectionName);
        MongoCollection<Document> eventCollection = database.getCollection(eventsCollectionName);
        MongoCollection<Document> cartCollection = database.getCollection(checkoutCartCollectionName);
        this.productCollection = aCollection;
        this.infoCollection = infoCollection;
        this.eventCollection = eventCollection;
        this.cartCollection = cartCollection;
        System.out.print("[MongoDB] initialization done\n");
    }

    public String getByEAN(String barcode) {
        Document myDoc = productCollection.find(eq("ean", barcode)).first();
        if (myDoc != null) {
            return myDoc.toJson();
        }
        JsonObject resp = new JsonObject();
        resp.addProperty("message", "Product not found");
        return resp.toString();
    }

    public String getByUPC(String barcode) {
        Document myDoc = productCollection.find(eq("upc", barcode)).first();
        if (myDoc != null) {
            return myDoc.toJson();
        }
        JsonObject resp = new JsonObject();
        resp.addProperty("message", "Product not found");
        return resp.toString();
    }
    public String insertInfo(Document customerInfo) {
        infoCollection.insertOne(customerInfo);
        JsonObject resp = new JsonObject();
        resp.addProperty("message", "Success");
        System.out.println(customerInfo.toString());
        return resp.toString();
    }
    public String insertCustomerScannedItemEvent(Document customerScannedItemEvent) {
        eventCollection.insertOne(customerScannedItemEvent);
        JsonObject resp = new JsonObject();
        resp.addProperty("message", "Success");
        System.out.println(customerScannedItemEvent.toString());
        return resp.toString();
    }
    public String insertCustomerCheckoutCart(Document customerCheckoutCart) {
        cartCollection.insertOne(customerCheckoutCart);
        JsonObject resp = new JsonObject();
        resp.addProperty("message", "Success");
        System.out.println(customerCheckoutCart.toString());
        return resp.toString();
    }
}
