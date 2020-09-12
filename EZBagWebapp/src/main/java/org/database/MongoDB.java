package org.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Properties;

import static com.mongodb.client.model.Filters.eq;


public class MongoDB {
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    private String host;
    private String port;
    private String databaseName;
    private String user;
    private String password;
    private String collectionName;
    public MongoDB(Properties prop)
    {
        System.out.println("[MongoDB] Loading database properties");
        host = prop.getProperty("host");
        port = prop.getProperty("port");
        databaseName = prop.getProperty("database");
        user = prop.getProperty("user");
        password = prop.getProperty("password");
        collectionName = prop.getProperty("collection");
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
        System.out.print("[MongoDB] Fetching collection...");
        MongoCollection<Document> aCollection = database.getCollection(collectionName);
        System.out.print(" done\n");
        this.collection = aCollection;
    }

    public String getByEAN(String barcode) {
        Document myDoc = collection.find(eq("ean", barcode)).first();
        return myDoc.toJson();
    }

    public String getByUPC(String barcode) {
        Document myDoc = collection.find(eq("upc", barcode)).first();
        return myDoc.toJson();
    }
}
