package org.routes;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.bson.Document;
import org.services.DatabaseService;

@Path("/event")
public class CustomerEventRoute {
    @POST
    //@Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String doPost(String payload) {
        JsonObject payloadObject = new JsonParser().parse(payload).getAsJsonObject();
        Document insertDoc = new Document();
        if (payloadObject.has("type") && payloadObject.has("barcode")
            && payloadObject.has("session"))
        {
            insertDoc.append("type", payloadObject.get("type").getAsString());
            insertDoc.append("barcode", payloadObject.get("barcode").getAsString());
            insertDoc.append("session", payloadObject.get("session").getAsString());
            insertDoc.append("time", System.currentTimeMillis());
            String resp = DatabaseService.insertCustomerEvent(insertDoc);
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




















