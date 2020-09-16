package org.routes;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.bson.Document;
import org.services.DatabaseService;

@Path("/product")
public class ProductInsertRoute {
    @POST
    //@Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String doPost(String payload) {
        JsonObject payloadObject = new JsonParser().parse(payload).getAsJsonObject();
        Document insertDoc = new Document();
        if (payloadObject.has("upc") && payloadObject.has("ean")
                && payloadObject.has("barcode")
                && payloadObject.has("name")
                && payloadObject.has("price")
                && payloadObject.has("description")
                && payloadObject.has("business-id"))
        {
            insertDoc.append("upc", payloadObject.get("upc").getAsString());
            insertDoc.append("ean", payloadObject.get("ean").getAsString());
            insertDoc.append("name", payloadObject.get("name").getAsString());
            insertDoc.append("price", payloadObject.get("price").getAsString());
            insertDoc.append("description", payloadObject.get("description").getAsString());
            insertDoc.append("business-id", payloadObject.get("business-id").getAsString());
            insertDoc.append("time", System.currentTimeMillis());
            String resp = DatabaseService.insertProduct(insertDoc);
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




















