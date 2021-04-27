package org.routes;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.services.DatabaseService;
import org.services.Utils;

@Path("/checkid")
public class CheckBusinessID {
    @POST
    //@Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String doPost(String payload) {
        JsonObject payloadObject = new JsonParser().parse(payload).getAsJsonObject();
        if (payloadObject.has("businessID"))
        {
            String businessID = payloadObject.get("businessID").getAsString();
            boolean exists = DatabaseService.businessIDExists(businessID);
            String resp = "";
            if (exists) {
                resp = "Exists";
            } else {
                resp = "Does not exist";
            }
            return Utils.generateResponse(exists, resp);
        } else
        {
            String message = "Barcode product lookup requires: barcode, businessID";
            return Utils.generateResponse(false, message);
        }
    }
}