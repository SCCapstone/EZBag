package org.routes;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.bson.Document;
import org.services.DatabaseService;

@Path("/lookup")
public class ProductQueryRoute {
    @POST
    //@Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String doPost(String payload) {
        JsonObject payloadObject = new JsonParser().parse(payload).getAsJsonObject();
        Document insertDoc = new Document();
        if (payloadObject.has("barcode")
                && payloadObject.has("businessID"))
        {
            String resp = DatabaseService.getProductByBarcodeBusinessID(payloadObject.get("barcode").getAsString(),
                    payloadObject.get("businessID").getAsString());
            return resp;
        } else
        {
            String message = "Barcode product lookup requires: barcode, businessID";
            JsonObject response = new JsonObject();
            response.addProperty("message", message);
            return response.toString();
        }
    }
}











