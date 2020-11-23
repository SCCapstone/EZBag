package debug;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.database.MongoDB;
import org.services.DatabaseService;
import org.services.ReceiptService;
import org.services.StartupService;
import org.services.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class main {
    public static void main (String args[]) {

        String propertiesFile = "/usr/local/opt/EZBag/EZBag.properties";
        System.out.println("[Startup] Loading properties file");
        Properties prop = Utils.getPropertiesFile(propertiesFile);
        System.out.println("[Startup] Connecting to database");
        MongoDB mongo = new MongoDB(prop);
        DatabaseService.database = mongo;
        ReceiptService.database = mongo;

        System.out.println("Simple MongoDB Connector");
        MongoClient informalDB = new MongoClient("localhost", 27017);
        MongoCredential credential;
        credential = MongoCredential.createCredential("blake", "myDB", "password".toCharArray());
        System.out.println("Connected to database");
        MongoDatabase database = informalDB.getDatabase("myDB");
        MongoCollection<Document> collection = database.getCollection("product");


        List<Document> products = new ArrayList<Document>();
        products.add(Document.parse("{\"upc\" : \"670433093100\", \"ean\" : \"0670433093100\", \"name\" : \"Ground Chicken\", \"price\" : 8.99, \"description\" : \"1 lb of Vegetable Fed Ground Chicken. No Antibiotics.\" }"));
        products.add(Document.parse("{\"upc\" : \"024600010436\", \"ean\" : \"0024600010436\", \"name\" : \"Iodized Salt\", \"price\" : 3.99, \"description\" : \"100% pure salt.\" }"));
        products.add(Document.parse("{\"upc\" : \"226270000001\", \"ean\" : \"0226270000001\", \"name\" : \"Whole Muscle Beef\", \"price\" : 10.99, \"description\" : \"Beef Hind Shank.\" }"));
        products.add(Document.parse("{\"upc\" : \"810180026178\", \"ean\" : \"0810180026178\", \"name\" : \"Melatonin\", \"price\" : 10.99, \"description\" : \"Makes you fall asleep fast.\" }"));
        products.add(Document.parse("{\"upc\" : \"696554738959\", \"ean\" : \"0696554738959\", \"name\" : \"Sword Floss\", \"price\" : 5.99, \"description\" : \"Disposable Floss Picks Mint 50 Ct.\" }"));
        products.add(Document.parse("{\"upc\" : \"06827465\", \"ean\" : \"0696554738959\", \"name\" : \"Sword Floss\", \"price\" : 5.99, \"description\" : \"Disposable Floss Picks Mint 50 Ct.\" }"));

        Document waterBottle = new Document("ean", "06827465")
                .append("name", "Nestle Pure Life Water").append("description", "Nestle Pure Life Purified Water comes in convenient plastic, resealable bottles, for easy, on-the-go hydration.");
        products.add(waterBottle);
        Document salt = new Document("ean", "0050428427439")
                .append("name", "Iodized Salt")
                .append("description", "Iodized salt is table salt mixed with a minute amount of various salts of the element iodine. The ingestion of iodine prevents iodine deficiency.");
        products.add(salt);

        collection.insertMany(products);
        System.out.println("Inserted starter documents");

        StartupService.startup();
        System.out.println(DatabaseService.getByProductCode("810180026178"));
        System.out.println(DatabaseService.getByProductCode("0670433093100"));



        // Testing customer info insertion
        Document customerInfo = new Document("email", "blakeedwards823@gmail.com").append("phone", "8603337654");
        String resp = DatabaseService.insertInfo(customerInfo);
        System.out.println(resp);
        Document customerInfo2 = new Document("email", "mikercutro@gmail.com");
        String resp2 = DatabaseService.insertInfo(customerInfo2);
        System.out.println(resp2);
        Document customerInfo3 = new Document("phone", "8603333333");
        String resp3 = DatabaseService.insertInfo(customerInfo3);
        System.out.println(resp3);

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


//        // TODO insert customer check out cart (cart they paid for)
//        List<Document> customerShoppingCartObject = new ArrayList<Document>();
//        customerShoppingCartObject.add(new Document("barcode", "670433093100"));
//        customerShoppingCartObject.add(new Document("barcode", "024600010436"));
//        customerShoppingCartObject.add(new Document("barcode", "226270000001"));
//        Document customerCheckoutCartObject = new Document("time", System.currentTimeMillis())
//                .append("cart", customerShoppingCartObject)
//                .append("cartHash", "aaj129fm3fk8fjaj8") // TODO: standard method of hashing list of barcodes in cart
//                .append("total", "23.97");
//        String resp7 = DatabaseService.insertCustomerCheckoutCart(customerCheckoutCartObject);
//        System.out.println(resp7.toString());

        // Testing eReceipt generator
        List<String> barcodes = new ArrayList<String>();
        barcodes.add("6704330931001");
        barcodes.add("0246000104361");
        barcodes.add("2262700000011");
        List<String> barcodeTypes = new ArrayList<String>();
        barcodeTypes.add("ean13");
        barcodeTypes.add("ean13");
        barcodeTypes.add("ean13");
        List<Integer> quantities = new ArrayList<Integer>();
        quantities.add(1);
        quantities.add(2);
        quantities.add(3);
        Document mockCartObject = new Document("time", System.currentTimeMillis())
                .append("barcodes", barcodes)
                .append("barcodeTypes", barcodeTypes)
                .append("quantities", quantities)
                .append("businessID", "1")
                .append("session", "1")
                .append("subtotal", 10.99)
                .append("tax", 1.16)
                .append("total", 12.15);
        String receipt = ReceiptService.generateEReceipt(mockCartObject);
        System.out.println(receipt);

    }
    // TODO: json to document converter to easily communicate between frontend and backend

}