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

        System.out.println("Simple MongoDB Connector");
        MongoClient mongo = new MongoClient("localhost", 27017);
        MongoCredential credential;
        credential = MongoCredential.createCredential("blake", "myDB", "password".toCharArray());
        System.out.println("Connected to database");
        MongoDatabase database = mongo.getDatabase("myDB");
        MongoCollection<Document> collection = database.getCollection("product");
        List<Document> products = new ArrayList<Document>();
        products.add(Document.parse("{\"upc\" : \"670433093100\", \"ean\" : \"0670433093100\", \"name\" : \"Ground Chicken\", \"price\" : 8.99, \"description\" : \"1 lb of Vegetable Fed Ground Chicken. No Antibiotics.\" }"));
        products.add(Document.parse("{\"upc\" : \"024600010436\", \"ean\" : \"0024600010436\", \"name\" : \"Iodized Salt\", \"price\" : 3.99, \"description\" : \"100% pure salt.\" }"));
        products.add(Document.parse("{\"upc\" : \"226270000001\", \"ean\" : \"0226270000001\", \"name\" : \"Whole Muscle Beef\", \"price\" : 10.99, \"description\" : \"Beef Hind Shank.\" }"));
        products.add(Document.parse("{\"upc\" : \"810180026178\", \"ean\" : \"0810180026178\", \"name\" : \"Melatonin\", \"price\" : 10.99, \"description\" : \"Makes you fall asleep fast.\" }"));
        products.add(Document.parse("{\"upc\" : \"696554738959\", \"ean\" : \"0696554738959\", \"name\" : \"Sword Floss\", \"price\" : 5.99, \"description\" : \"Disposable Floss Picks Mint 50 Ct.\" }"));
        collection.insertMany(products);
        System.out.println("Inserted starter documents");

        StartupService.startup();
        System.out.println(DatabaseService.getByProductCode("810180026178"));
        System.out.println(DatabaseService.getByProductCode("0670433093100"));

//        // Testing customer info insertion
//        Document customerInfo = new Document("email", "blakeedwards823@gmail.com").append("phone", "8603337654");
//        String resp = DatabaseService.insertInfo(customerInfo);
//        System.out.println(resp);
//        Document customerInfo2 = new Document("email", "mikercutro@gmail.com");
//        String resp2 = DatabaseService.insertInfo(customerInfo2);
//        System.out.println(resp2);
//        Document customerInfo3 = new Document("phone", "8603333333");
//        String resp3 = DatabaseService.insertInfo(customerInfo3);
//        System.out.println(resp3);

//        // TODO insert customer scanned item event
//        Document customerScannedItemEventObject = new Document("time", System.currentTimeMillis())
//                .append("session", "192948261782944030289273483")
//                .append("barcode", "670433093100")
//                .append("type", "customerScannedItem");
//        String resp4 = DatabaseService.insertCustomerEvent(customerScannedItemEventObject);
//        System.out.println(resp4);


//        // TODO insert customer added item to cart
//        Document customerAddedItemToCartEventObject = new Document("time", System.currentTimeMillis())
//                .append("session", "192948261782944030289273483")
//                .append("barcode", "670433093100")
//                .append("type", "customerAddedItemToCart");
//        String resp5 = DatabaseService.insertCustomerEvent(customerScannedItemEventObject);
//        System.out.println(resp5);
//
//        // TODO insert customer removed item from cart
//        Document customerRemovedItemFromCartEventObject = new Document("time", System.currentTimeMillis())
//                .append("session", "192948261782944030289273483")
//                .append("barcode", "670433093100")
//                .append("type", "customerRemovedItemToCart");
//        String resp6 = DatabaseService.insertCustomerEvent(customerRemovedItemFromCartEventObject);
//        System.out.println(resp6);

//
//        // TODO insert customer check out cart (cart they paid for)
//        List<Document> customerShoppingCartObject = new ArrayList<Document>();
//        customerShoppingCartObject.add(new Document("barcode", "670433093100"));
//        customerShoppingCartObject.add(new Document("barcode", "024600010436"));
//        customerShoppingCartObject.add(new Document("barcode", "226270000001"));
//        Document customerCheckoutCartObject = new Document("time", System.currentTimeMillis())
//                .append("cart", customerShoppingCartObject)
//                .append("total", "23.97");
//        String resp7 = DatabaseService.insertCustomerCheckoutCart(customerCheckoutCartObject);
//        System.out.println(resp7.toString());

    }
    // TODO: json to document converter to easily communicate between frontend and backend

}
