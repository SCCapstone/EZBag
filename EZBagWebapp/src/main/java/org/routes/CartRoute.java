package org.routes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.bson.Document;
import org.services.DatabaseService;
import org.services.Utils;

import java.util.*;

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
            JsonArray productQuantities = payloadObject.get("quantities").getAsJsonArray();
            // check if product and quantities array lengths match
            if (barcodes.size() != productQuantities.size()) {
                String message = "Submitted number of products and quantities does not match!";
                return Utils.generateResponse(false, message);
            }
            List<String> codes = new ArrayList<String>();
            List<Integer> quantities = new ArrayList<Integer>();
            // calculate subtotal and taxes of cart
            Double cartSubtotal = 0.0;
            Double cartTaxTotal = 0.0;
            for (int i=0; i<barcodes.size(); i++) {
                // checking if product with given barcode and BID exists in DB
                String resp = DatabaseService.getProductByBarcodeBusinessID(barcodes.get(i).getAsString(), businessID);
                JsonObject productResp = new JsonParser().parse(resp).getAsJsonObject();
                // if does, get price
                if (!productResp.has("status"))
                {
                    Double productPrice = productResp.get("price").getAsDouble();
                    int productQuantity = productQuantities.get(i).getAsInt();
                    cartSubtotal += (double) Math.ceil(100 * productPrice * productQuantity) / 100.0;
                    cartTaxTotal += (double) Math.ceil(100* productResp.get("tax").getAsDouble() * (productPrice * productQuantity)) / 100.0;
                } else {
                    String message = "Submitted product does not exist: "+barcodes.get(i).getAsString();
                    return Utils.generateResponse(false, message);
                }
                codes.add(barcodes.get(i).getAsString());
                quantities.add(productQuantities.get(i).getAsInt());
            }

            insertDoc.append("barcodes", codes);
            insertDoc.append("quantities", quantities);
            insertDoc.append("businessID", businessID);
            insertDoc.append("subtotal", cartSubtotal);
            insertDoc.append("session", session);
            insertDoc.append("tax", cartTaxTotal);
            insertDoc.append("total", cartSubtotal+cartTaxTotal);
            insertDoc.append("time", System.currentTimeMillis());
            insertDoc.append("cartHash", cartHash);
            insertDoc.append("verified", false);

            String resp = DatabaseService.insertCustomerCheckoutCart(insertDoc);
            JsonObject respObject = new JsonParser().parse(resp).getAsJsonObject();
            respObject.addProperty("hash", cartHash);
            return respObject.toString();

        } else
        {
            return Utils.generateResponse(false, "Submitted cart json object does not contain required fields");
        }
    }
}




















