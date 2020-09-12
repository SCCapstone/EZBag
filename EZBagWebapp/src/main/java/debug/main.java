package debug;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.services.DatabaseService;
import org.services.StartupService;

import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main (String args[]) {
        /*
        System.out.println("Simple MongoDB Connector");
        MongoClient mongo = new MongoClient("localhost", 27017);
        MongoCredential credential;
        credential = MongoCredential.createCredential("sampleUser", "myDB", "password".toCharArray());
        System.out.println("Connected to database");
        MongoDatabase database = mongo.getDatabase("myDB");
        MongoCollection<Document> collection = database.getCollection("sampleCollection");
        List<Document> products = new ArrayList<Document>();
        products.add(Document.parse("{\"upc\" : \"670433093100\", \"ean\" : \"0670433093100\", \"name\" : \"Ground Chicken\", \"price\" : 8.99, \"description\" : \"1 lb of Vegetable Fed Ground Chicken. No Antibiotics.\" }"));
        products.add(Document.parse("{\"upc\" : \"024600010436\", \"ean\" : \"0024600010436\", \"name\" : \"Iodized Salt\", \"price\" : 3.99, \"description\" : \"100% pure salt.\" }"));
        products.add(Document.parse("{\"upc\" : \"226270000001\", \"ean\" : \"0226270000001\", \"name\" : \"Whole Muscle Beef\", \"price\" : 10.99, \"description\" : \"Beef Hind Shank.\" }"));
        products.add(Document.parse("{\"upc\" : \"810180026178\", \"ean\" : \"0810180026178\", \"name\" : \"Melatonin\", \"price\" : 10.99, \"description\" : \"Makes you fall asleep fast.\" }"));
        products.add(Document.parse("{\"upc\" : \"696554738959\", \"ean\" : \"0696554738959\", \"name\" : \"Sword Floss\", \"price\" : 5.99, \"description\" : \"Disposable Floss Picks Mint 50 Ct.\" }"));
        collection.insertMany(products);
        System.out.println("Document 2 inserted successfully via JSON string");
        //*/
        StartupService.startup();
        System.out.println(DatabaseService.getByProductCode("810180026178"));
        System.out.println(DatabaseService.getByProductCode("0670433093100"));
    }
    // TODO: json to document converter to easily communicate between frontend and backend

}
