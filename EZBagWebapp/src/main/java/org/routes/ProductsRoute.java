package org.routes;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.bson.Document;
import org.services.DatabaseService;
import org.services.Utils;


@Path("/products")
public class ProductsRoute {
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String doPost(String payload) {
        JsonObject payloadObject = new JsonParser().parse(payload).getAsJsonObject();
        Document insertDoc = new Document();
        if (payloadObject.has("businessID"))
        {
            String resp = DatabaseService.getProductsBusinessID(payloadObject.get("businessID").getAsString());
            return resp;
        } else
        {
            String message = "Product search requires: businessID";
            return Utils.generateResponse(false, message);
        }
    }
}
