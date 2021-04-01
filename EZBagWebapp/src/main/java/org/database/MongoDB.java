package org.database;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.services.DatabaseService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import static com.mongodb.client.model.Filters.*;


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
        // create product collection text index for for search of products
        collectionsMap.get(productCollectionName).createIndex(Indexes.text("name"));

        System.out.print("[MongoDB] initialization done\n");
    }



    public Boolean deleteProductByBarcodeBusinessID(String barcode, String businessID) {
        BasicDBObject query = new BasicDBObject();
        List<BasicDBObject> matchDoc = new ArrayList<BasicDBObject>();
        matchDoc.add(new BasicDBObject("barcode", barcode));
        matchDoc.add(new BasicDBObject("businessID", businessID));
        query.put("$and", matchDoc);
        DeleteResult result = collectionsMap.get(productCollectionName).deleteMany(query);
        System.out.println("Deleted "+result.getDeletedCount()+" products");
        return result.getDeletedCount()>=1;
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

    public String getProductsBusinessID(String businessID) {
        FindIterable<Document> result = collectionsMap.get(productCollectionName)
                .find(new Document().append("businessID", businessID));
        JsonArray jsArray = new JsonArray();
        for (Document doc : result) {
            doc.remove("_id");
            JsonObject payloadObject = new JsonParser().parse(doc.toJson()).getAsJsonObject();
            jsArray.add(payloadObject);
        }
        JsonObject resp = new JsonObject();
        resp.addProperty("count", jsArray.size());
        resp.add("products", jsArray);
        resp.addProperty("status", "success");
        return resp.toString();

    }

    public String searchProductsByBusinessIDQuery(String businessID, String queryString) {
        FindIterable<Document> results = collectionsMap.get(productCollectionName)
                .find(and(eq("businessID", businessID), Filters.text(queryString)))
                .projection(Projections.metaTextScore("score"))
                .sort(Sorts.metaTextScore("score"));
        JsonArray searchResults = new JsonArray();
        for (Document resDoc : results) {
            resDoc.remove("_id");
            JsonObject payloadObject = new JsonParser().parse(resDoc.toJson()).getAsJsonObject();
            searchResults.add(payloadObject);
        }
        JsonObject resp = new JsonObject();
        resp.add("results", searchResults);
        resp.addProperty("status", "success");
        return resp.toString();
    }

    public String getLast30DaysCartsByBusinessID(String businessID) {
        long dayMillis = System.currentTimeMillis() - 86400000*30;
        FindIterable<Document> result = collectionsMap.get(checkoutCartCollectionName)
                .find(new Document().append("businessID", businessID)
                        .append("time", new Document().append("$gt" , dayMillis)));
        JsonArray jsArray = new JsonArray();
        HashMap<String, String> productCache = new HashMap<>();
        for (Document doc : result) {
            doc.remove("_id");
            // TODO: get product names
            ArrayList<String> barcodes = (ArrayList<String>) doc.get("barcodes");
            ArrayList<String> productNames = new ArrayList<>(barcodes.size());
            for (int i = 0; i < barcodes.size(); i++) {
                if (productCache.containsKey(barcodes.get(i))) {
                    productNames.add(i, productCache.get(barcodes.get(i)));
                } else {
                    Document resp = getProductByBarcodeBusinessID(barcodes.get(i), businessID);
                    if (resp != null)
                    {
                        productNames.add(i, resp.getString("name"));
                        productCache.put(barcodes.get(i), resp.getString("name"));
                    } else {
                        // TODO: product doesnt exists do something else to handle this
                        productNames.add(i, "UNKNOWN PRODUCT ERROR");
                        productCache.put(barcodes.get(i), "UNKNOWN PRODUCT ERROR");
                    }
                }
            }
            doc.append("names", productNames);
            JsonObject payloadObject = new JsonParser().parse(doc.toJson()).getAsJsonObject();
            jsArray.add(payloadObject);
        }
        JsonObject resp = new JsonObject();
        resp.add("carts", jsArray);
        resp.addProperty("status", "success");
        return resp.toString();
    }

    public String getLast7DaysCartsByBusinessID(String businessID) {
        long dayMillis = System.currentTimeMillis() - 86400000*7;
        FindIterable<Document> result = collectionsMap.get(checkoutCartCollectionName)
                .find(new Document().append("businessID", businessID)
                        .append("time", new Document().append("$gt" , dayMillis)));
        JsonArray jsArray = new JsonArray();
        HashMap<String, String> productCache = new HashMap<>();
        for (Document doc : result) {
            doc.remove("_id");
            // TODO: get product names
            ArrayList<String> barcodes = (ArrayList<String>) doc.get("barcodes");
            ArrayList<String> productNames = new ArrayList<>(barcodes.size());
            for (int i = 0; i < barcodes.size(); i++) {
                if (productCache.containsKey(barcodes.get(i))) {
                    productNames.add(i, productCache.get(barcodes.get(i)));
                } else {
                    Document resp = getProductByBarcodeBusinessID(barcodes.get(i), businessID);
                    if (resp != null)
                    {
                        productNames.add(i, resp.getString("name"));
                        productCache.put(barcodes.get(i), resp.getString("name"));
                    } else {
                        // TODO: product doesnt exists do something else to handle this
                        productNames.add(i, "UNKNOWN PRODUCT ERROR");
                        productCache.put(barcodes.get(i), "UNKNOWN PRODUCT ERROR");
                    }
                }
            }
            doc.append("names", productNames);
            JsonObject payloadObject = new JsonParser().parse(doc.toJson()).getAsJsonObject();
            jsArray.add(payloadObject);
        }
        JsonObject resp = new JsonObject();
        resp.add("carts", jsArray);
        resp.addProperty("status", "success");
        return resp.toString();
    }

    public String getLast24HourCartsByBusinessID(String businessID) {
        long dayMillis = System.currentTimeMillis() - 86400000;
        FindIterable<Document> result = collectionsMap.get(checkoutCartCollectionName)
                .find(new Document().append("businessID", businessID)
                                    .append("time", new Document().append("$gt" , dayMillis)));
        JsonArray jsArray = new JsonArray();
        HashMap<String, String> productCache = new HashMap<>();
        for (Document doc : result) {
            doc.remove("_id");
            // TODO: get product names
            ArrayList<String> barcodes = (ArrayList<String>) doc.get("barcodes");
            ArrayList<String> productNames = new ArrayList<>(barcodes.size());
            for (int i = 0; i < barcodes.size(); i++) {
                if (productCache.containsKey(barcodes.get(i))) {
                    productNames.add(i, productCache.get(barcodes.get(i)));
                } else {
                    Document resp = getProductByBarcodeBusinessID(barcodes.get(i), businessID);
                    if (resp != null)
                    {
                        productNames.add(i, resp.getString("name"));
                        productCache.put(barcodes.get(i), resp.getString("name"));
                    } else {
                        // TODO: product doesnt exists do something else to handle this
                        productNames.add(i, "UNKNOWN PRODUCT ERROR");
                        productCache.put(barcodes.get(i), "UNKNOWN PRODUCT ERROR");
                    }
                }
            }
            doc.append("names", productNames);
            JsonObject payloadObject = new JsonParser().parse(doc.toJson()).getAsJsonObject();
            jsArray.add(payloadObject);
        }
        JsonObject resp = new JsonObject();
        resp.add("carts", jsArray);
        resp.addProperty("status", "success");
        return resp.toString();
    }

    public String getUserNonceByEmail(String userEmail) {
        BasicDBObject query = new BasicDBObject();
        List<BasicDBObject> matchDoc = new ArrayList<BasicDBObject>();
        matchDoc.add(new BasicDBObject("email", userEmail));
        query.put("$and", matchDoc);
        Document respDoc = collectionsMap.get(userCollectionName).find(query).first();
        return respDoc.getString("nonce");
    }

    public Document getUserByEmailPassword(String email, String password) {
        BasicDBObject query = new BasicDBObject();
        List<BasicDBObject> matchDoc = new ArrayList<BasicDBObject>();
        matchDoc.add(new BasicDBObject("email", email));
        matchDoc.add(new BasicDBObject("password", password));
        query.put("$and", matchDoc);
        Document respDoc = collectionsMap.get(userCollectionName).find(query).first();
        return respDoc;
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
    private Boolean documentExistsInCollection(String collectionName, HashMap<String, String> matchMap) {
        BasicDBObject query = new BasicDBObject();
        List<BasicDBObject> matchDoc = new ArrayList<BasicDBObject>();
        // Iterate matchMap
        for (String key : matchMap.keySet()) {
            System.out.println("key: " + key + " value: " + matchMap.get(key));
            matchDoc.add(new BasicDBObject(key, matchMap.get(key)));
        }
        query.put("$and", matchDoc);
        Document respDoc = collectionsMap.get(collectionName).find(query).first();
        if (respDoc != null) {
            System.out.println(respDoc.toString());
            return true;
        }
        System.out.println("Exists resp doc was null!");
        return false;
    }

    public Document getUserEmailByUserID(String userID, ArrayList<String> returnParams) {
        HashMap<String, String> matchMap = new HashMap<>();
        matchMap.put("userID", userID);
        return documentGetFields(userCollectionName, matchMap, returnParams);
    }

    // method to check whether the given document already exists within a collection collection
    private Document documentGetFields(String collectionName, HashMap<String, String> matchMap, ArrayList<String> returnFields) {
        System.out.println("running documentGetFields");
        BasicDBObject query = new BasicDBObject();
        List<BasicDBObject> matchDoc = new ArrayList<BasicDBObject>();
        // Iterate matchMap
        for (String key : matchMap.keySet()) {
            System.out.println("key: " + key + " value: " + matchMap.get(key));
            matchDoc.add(new BasicDBObject(key, matchMap.get(key)));
        }
        query.put("$and", matchDoc);
        Document respDoc = collectionsMap.get(collectionName).find(query).first();
        Document returnDoc = new Document();
        if (respDoc == null) {
            System.out.println("response doc was null!");
            return returnDoc;
        } else {
            System.out.println(respDoc.toString());
            System.out.println("queried for: " + returnFields.toString());
            for (String field : returnFields) {
                // check if exists in reponse doc
                if(respDoc.containsKey(field)) {
                    returnDoc.append(field, respDoc.get(field));
                }
            }
            return returnDoc;
        }
    }

    public Boolean userIsVerified(String userEmail) {
        System.out.println("Checking if user is verified: " + userEmail);
        BasicDBObject query = new BasicDBObject();
        List<BasicDBObject> matchDoc = new ArrayList<BasicDBObject>();
        matchDoc.add(new BasicDBObject("email", userEmail));
        query.put("$and", matchDoc);
        Document respDoc = collectionsMap.get(userCollectionName).find(query).first();
        if (respDoc != null) {
            System.out.println("User verified: "+respDoc.getBoolean("verified"));
            return respDoc.getBoolean("verified");
        }
        System.out.println("Exists resp doc was null!");
        return false;
    }

    public Boolean userExists(String userEmail) {
        HashMap<String, String> matchMap = new HashMap<>();
        matchMap.put("email", userEmail);
        return documentExistsInCollection(userCollectionName, matchMap);
    }

    public Boolean userWithIDExists(String userID) {
        HashMap<String, String> matchMap = new HashMap<>();
        matchMap.put("userID", userID);
        return documentExistsInCollection(userCollectionName, matchMap);
    }

    public Boolean cartExists(String cartHash, String businessID) {
        HashMap<String, String> matchMap = new HashMap<>();
        matchMap.put("cartHash", cartHash);
        matchMap.put("businessID", businessID);
        return documentExistsInCollection(checkoutCartCollectionName, matchMap);
    }

    public String getUserBusinessID(String userEmail) {
        Document respDoc = collectionsMap.get(userCollectionName).find(eq("email", userEmail)).first();
        if (respDoc != null) {
            System.out.println(respDoc.toString());
            return respDoc.get("businessID").toString();
        }
        return null;
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

    public boolean verifyCart(String cartHash, String businessID) {
        System.out.println("Verifying cart: " + cartHash + " " + businessID);
        BasicDBObject query = new BasicDBObject();
        List<BasicDBObject> matchDoc = new ArrayList<BasicDBObject>();
        matchDoc.add(new BasicDBObject("cartHash", cartHash));
        matchDoc.add(new BasicDBObject("businessID", businessID));
        query.put("$and", matchDoc);

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put("verified", true);
        BasicDBObject updateObject = new BasicDBObject();
        updateObject.put("$set", newDocument);

        UpdateResult res = collectionsMap.get(checkoutCartCollectionName).updateOne(query, updateObject);
        System.out.println("Was acknowledged: " + res.toString());
        return true;
    }

    public boolean verifyUser(String userID) {
        System.out.println("Verifying user: " + userID);
        BasicDBObject query = new BasicDBObject();
        List<BasicDBObject> matchDoc = new ArrayList<BasicDBObject>();
        matchDoc.add(new BasicDBObject("userID", userID));
        query.put("$and", matchDoc);

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put("verified", true);
        BasicDBObject updateObject = new BasicDBObject();
        updateObject.put("$set", newDocument);

        UpdateResult res = collectionsMap.get(userCollectionName).updateOne(query, updateObject);
        System.out.println("Was acknowledged: " + res.toString());
        return true;
    }

    // todo check if insert was actually successful for all insert methods
    public Boolean insertInfo(Document customerInfo) {
        // only insert document if it does not exist
        HashMap<String, String> matchMap = new HashMap<>();
        // TODO: test this b/c changed from hash to cartHash
        matchMap.put("cartHash", customerInfo.getString("cartHash"));
        if (!(documentExistsInCollection(infoCollectionName, matchMap)))
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
        HashMap<String, String> matchMap = new HashMap<>();
        matchMap.put("email", newUser.getString("email"));
        if (!(documentExistsInCollection(userCollectionName, matchMap)))
        {
            collectionsMap.get(userCollectionName).insertOne(newUser);
            return true;
        }
        return false;
    }
}
