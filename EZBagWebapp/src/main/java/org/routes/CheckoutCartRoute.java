package org.routes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.bson.Document;
import org.services.DatabaseService;

import java.util.ArrayList;
import java.util.List;

@Path("/cart")
public class CheckoutCartRoute {
    @POST
    //@Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String doPost(String payload) {
        JsonObject payloadObject = new JsonParser().parse(payload).getAsJsonObject();
        Document insertDoc = new Document();
        if (payloadObject.has("cart")
                && payloadObject.has("total") && payloadObject.has("session"))
        {

            JsonArray cartItems = payloadObject.get("cart").getAsJsonArray();
            List<String> cart = new ArrayList<String>();
            for (int i=0; i<cartItems.size(); i++) {
                cart.add(cartItems.get(i).getAsString());
            }
            insertDoc.append("total", payloadObject.get("total").getAsString());
            insertDoc.append("cart", cart);
            insertDoc.append("time", System.currentTimeMillis());
            insertDoc.append("session", payloadObject.get("session").getAsString());
            String resp = DatabaseService.insertCustomerCheckoutCart(insertDoc);
            return resp;
        } else
        {
            String message = "Submitted customer event json object is not valid";
            System.out.println(message);
            JsonObject response = new JsonObject();
            response.addProperty("message", message);
            return response.toString();
        }
    }
}




















