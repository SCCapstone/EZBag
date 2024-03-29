package org.routes;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.bson.Document;
import org.services.DatabaseService;
import org.services.Utils;

@Path("/delete")
public class BizProductDeleteRoute {
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String doPost(String payload) {
        JsonObject payloadObject = new JsonParser().parse(payload).getAsJsonObject();
        // validate user token
        if (payloadObject.has("token") && payloadObject.has("businessID")
                && Utils.validToken(payloadObject.get("token").getAsString(), payloadObject.get("businessID").getAsString())) {

            Document insertDoc = new Document();
            if (payloadObject.has("barcode")
                    && payloadObject.has("businessID")) {
                //TODO: delete product
                String message = "Submitted product was successfully deleted";
                String barcode = payloadObject.get("barcode").getAsString();
                String businessID = payloadObject.get("businessID").getAsString();
                Boolean succ = DatabaseService.deleteProductBarcodeBusinessID(barcode, businessID);
                return Utils.generateResponse(succ, message);
            } else {
                String message = "Submitted product json object is not valid";
                return Utils.generateResponse(false, message);
            }

        } else {
            return Utils.generateResponse(false, "Not authorized");
        }

    }
}




















