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
        if (payloadObject.has("barcode")
                && payloadObject.has("barcodeType")
                && payloadObject.has("businessID")
                && payloadObject.has("name")
                && payloadObject.has("price")
                && payloadObject.has("description"))
        {
            insertDoc.append("barcode", payloadObject.get("barcode").getAsString())
                    .append("barcodeType", payloadObject.get("barcodeType").getAsString())
                    .append("name", payloadObject.get("name").getAsString())
                    .append("price", payloadObject.get("price").getAsDouble())
                    .append("description", payloadObject.get("description").getAsString())
                    .append("businessID", payloadObject.get("businessID").getAsString())
                    .append("time", System.currentTimeMillis());
            return DatabaseService.insertProduct(insertDoc);
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




















