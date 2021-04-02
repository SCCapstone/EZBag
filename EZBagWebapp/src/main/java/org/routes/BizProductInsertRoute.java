package org.routes;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.bson.Document;
import org.services.DatabaseService;
import org.services.Utils;

@Path("/product")
public class BizProductInsertRoute {
    @POST
    //@Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String doPost(String payload) {
        // TODO: validate user token
        JsonObject payloadObject = new JsonParser().parse(payload).getAsJsonObject();
        // validate user token
        if (payloadObject.has("token") && payloadObject.has("barcode")
                && Utils.validToken(payloadObject.get("token").getAsString(), payloadObject.get("businessID").getAsString())) {
            Document insertDoc = new Document();
            if (payloadObject.has("barcodeType")
                    && payloadObject.has("businessID")
                    && payloadObject.has("name")
                    && payloadObject.has("price")
                    && payloadObject.has("tax")
                    && payloadObject.has("description")) {
                insertDoc.append("barcode", payloadObject.get("barcode").getAsString())
                        .append("barcodeType", payloadObject.get("barcodeType").getAsString())
                        .append("businessID", payloadObject.get("businessID").getAsString())
                        .append("name", payloadObject.get("name").getAsString())
                        .append("price", payloadObject.get("price").getAsDouble())
                        .append("tax", payloadObject.get("tax").getAsDouble())
                        .append("description", payloadObject.get("description").getAsString())
                        .append("time", System.currentTimeMillis());
                // TODO: change to reply with message of why the addition failed
                return DatabaseService.insertProduct(insertDoc);
            } else {
                String message = "Submitted customer event json object is not valid";
                System.out.println(message);
                JsonObject response = new JsonObject();
                response.addProperty("message", message);
                response.addProperty("status", "failure");
                return response.toString();
            }

        } else {
            return Utils.generateResponse(false, "Not authorized");
        }

    }

}




















