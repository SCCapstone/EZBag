package org.routes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.bson.Document;
import org.services.DatabaseService;
import org.services.ReceiptService;
import org.services.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Path("/cart")
public class CartRoute {
    @POST
    //@Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String doPost(String payload) {
        JsonObject payloadObject = new JsonParser().parse(payload).getAsJsonObject();
        Document insertDoc = new Document();
        // TODO check if payloadObject's key-values are the right type
        if (payloadObject.has("barcodes")
                && payloadObject.has("quantities")
                && payloadObject.has("businessID")
                && payloadObject.has("session"))
        {
            String cartHash = Utils.generateCartHash();
            String businessID = payloadObject.get("businessID").getAsString();
            String session = payloadObject.get("session").getAsString();
            JsonArray barcodes = payloadObject.get("barcodes").getAsJsonArray();
            List<String> codes = new ArrayList<String>();
            // TODO make sure given product in database
            // TODO if product in database get its price, mult by quantity, add to total
            for (int i=0; i<barcodes.size(); i++) {
                codes.add(barcodes.get(i).getAsString());
            }
            JsonArray productQuantities = payloadObject.get("quantities").getAsJsonArray();
            List<Integer> quantities = new ArrayList<Integer>();
            for (int i=0; i<productQuantities.size(); i++) {
                quantities.add(productQuantities.get(i).getAsInt());
            }
            // TODO: check the product exists in database before checking the price of it
            // TODO: calculate subtotal based on product prices
            Double subtotal = 10.99;
            if (codes.size() == quantities.size()) {
                insertDoc.append("barcodes", codes);
                insertDoc.append("quantities", quantities);
                insertDoc.append("businessID", businessID);
                insertDoc.append("subtotal", subtotal);
                insertDoc.append("session", session);
                // TODO calculate real tax on cart
                insertDoc.append("tax", 0.06*subtotal);
                // TODO calculate cart total using ReceiptService getCartProducts
                insertDoc.append("total", subtotal+insertDoc.getDouble("tax"));
                insertDoc.append("time", System.currentTimeMillis());
                insertDoc.append("cartHash", cartHash);
                // TODO kick of event to send customer digital receipt (async call ReceiptService)
                ReceiptService.generateEReceipt(insertDoc);

                String resp = DatabaseService.insertCustomerCheckoutCart(insertDoc);
                JsonObject respObject = new JsonParser().parse(resp).getAsJsonObject();
                respObject.addProperty("hash", cartHash);
                return respObject.toString();
            }
            return Utils.generateResponse(false, "Number of barcodes and barcode types did not match");
        } else
        {
            return Utils.generateResponse(false, "Submitted customer event json object is not valid");
        }
    }
}




















