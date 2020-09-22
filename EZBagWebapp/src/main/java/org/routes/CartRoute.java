package org.routes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.bson.Document;
import org.services.DatabaseService;
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
                && payloadObject.has("barcodeTypes")
                && payloadObject.has("quantities")
                && payloadObject.has("businessID")
                && payloadObject.has("session"))
        {

            JsonArray barcodes = payloadObject.get("barcodes").getAsJsonArray();
            List<String> codes = new ArrayList<String>();
            for (int i=0; i<barcodes.size(); i++) {
                codes.add(barcodes.get(i).getAsString());
            }
            JsonArray barcodeTypes = payloadObject.get("barcodeTypes").getAsJsonArray();
            List<String> types = new ArrayList<String>();
            for (int i=0; i<barcodeTypes.size(); i++) {
                types.add(barcodeTypes.get(i).getAsString());
            }
            JsonArray productQuantities = payloadObject.get("quantities").getAsJsonArray();
            List<Integer> quantities = new ArrayList<Integer>();
            for (int i=0; i<productQuantities.size(); i++) {
                quantities.add(productQuantities.get(i).getAsInt());
            }
            // TODO: calculate subtotal based on product prices
            Double subtotal = 10.99;
            if (codes.size() == types.size() && types.size() == quantities.size()) {
                insertDoc.append("barcodes", codes);
                insertDoc.append("barcodeTypes", types);
                insertDoc.append("quantities", quantities);
                insertDoc.append("businessID", payloadObject.get("businessID").getAsString());
                insertDoc.append("subtotal", subtotal);
                insertDoc.append("session", payloadObject.get("session").getAsString());
                // TODO calculate real tax on cart
                insertDoc.append("tax", 0.06*subtotal);
                // TODO calculate cart total using ReceiptService getCartProducts
                insertDoc.append("total", subtotal+insertDoc.getDouble("tax"));
                insertDoc.append("time", System.currentTimeMillis());
                // TODO kick of event to send customer digital receipt (async call ReceiptService)
                String resp = DatabaseService.insertCustomerCheckoutCart(insertDoc);
                return resp;
            }
            return Utils.generateResponse(false, "Number of barcodes and barcode types did not match");
        } else
        {
            return Utils.generateResponse(false, "Submitted customer event json object is not valid");
        }
    }
}




















