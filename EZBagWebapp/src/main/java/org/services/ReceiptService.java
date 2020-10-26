package org.services;
import de.vandermeer.asciitable.AT_Row;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciithemes.TA_GridThemes;
import de.vandermeer.asciithemes.a7.A7_Grids;
import de.vandermeer.asciithemes.a8.A8_Grids;
import de.vandermeer.asciithemes.u8.U8_Grids;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import org.services.DatabaseService;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.database.MongoDB;
import org.bson.Document;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReceiptService {
    public static MongoDB database = null;
    public static List<Document> receiptsQueue = new ArrayList<Document>();
    public static void addReceiptToQueue(Document cart) {

    }
    public static List<Document> getCartProducts(List<String> barcodes, List<String> barcodeTypes, String businessID) {
        List<Document> cartProducts = new ArrayList<Document>();
        for (int i=0; i<barcodes.size(); i++) {
            Document product = database.getProductByBarcodeBarcodeTypeBusinessID(barcodes.get(i), barcodeTypes.get(i), businessID);
            if (product != null) {
                cartProducts.add(new Document("name", product.getString("name"))
                        .append("price", product.getDouble("price")));
            }
            // TODO log an error that receipt barcode was not found
        }
        return cartProducts;
    }

    public static String generateEReceipt(Document cartObject) {
        // convert millisecond time to date time stamp
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss:SSS Z");
        Date dateFromMillis = new Date(cartObject.getLong("time"));
        String receipt = dateFormat.format(dateFromMillis)+"\n";

        List<String> barcodes = (List<String>) cartObject.get("barcodes");
        List<String> barcodeTypes = (List<String>) cartObject.get("barcodeTypes");
        List<Integer> quantities = (List<Integer>) cartObject.get("quantities");
        String businessID = cartObject.getString("businessID");
        // get all names and prices of products from database
        List<Document> cartProducts = getCartProducts(barcodes, barcodeTypes, businessID);
        // generate table
        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("Name", "Quantity", "Price").setTextAlignment(TextAlignment.CENTER);
        for (int i=0; i<cartProducts.size(); i++) {
            at.addRule();
            at.addRow(cartProducts.get(i).getString("name"),
                    Integer.toString(quantities.get(i)),
                    Double.toString(cartProducts.get(i).getDouble("price"))).setTextAlignment(TextAlignment.CENTER);
        }
        at.addRule();
        at.getContext().setWidth(35);
//        at.getContext().setGridTheme(TA_GridThemes.VERTICAL);
        at.getContext().setGridTheme(TA_GridThemes.NONE);
        receipt = receipt+at.render()+"\n";
        receipt = receipt+"Subtotal: "+Double.toString(cartObject.getDouble("subtotal"))+"\n";
        receipt = receipt+"Tax: "+Double.toString(cartObject.getDouble("tax"))+"\n";
        receipt = receipt+"Total: "+Double.toString(cartObject.getDouble("total"));
        return receipt;
    }
}
