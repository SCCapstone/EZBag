package debug;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main (String args[]) {
        System.out.println("Simple MongoDB Connector");
        MongoClient mongo = new MongoClient("localhost", 27017);
        MongoCredential credential;
        credential = MongoCredential.createCredential("sampleUser", "myDB", "password".toCharArray());
        System.out.println("Connected to database");
        MongoDatabase database = mongo.getDatabase("myDB");
        MongoCollection<Document> collection = database.getCollection("sampleCollection");
        System.out.println("Collection sampleCollection collection created");
        List<Document> barcodes = new ArrayList<Document>();
        barcodes.add(new Document("barcode", "024600010436")
                .append("barcode_type", "upc"));
        barcodes.add(new Document("barcode", "0024600010436")
                .append("barcode_type", "ean"));
        Document product_one = new Document("barcodes", barcodes)
                .append("name", "Iodized Salt")
                .append("price", 10.99)
                .append("description", "100% pure salt.");
        collection.insertOne(product_one);
        System.out.println("Document 1 inserted successfully via constructed Document");
        Document doc = Document.parse("{\"barcodes\" : [ { \"barcode\" : 670433093100, \"barcode_type\" : \"upc\" }, { \"barcode\" : 0670433093100, \"barcode_type\" : \"ean\" } ], \"name\" : \"Ground Chicken\", \"price\" : 8.99, \"description\" : \"1 lb of Vegetable Fed Ground Chicken. No Antibiotics.\" }");
        collection.insertOne(doc);
        System.out.println("Document 2 inserted successfully via JSON string");

    }
    // TODO: json to document converter to easily communicate between frontend and backend

}
