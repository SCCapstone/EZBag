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

@Path("/merchant/carts")
public class BizCartsRoute {
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String doPost(String payload, @HeaderParam("Authorization") String authToken) {
        JsonObject payloadObject = new JsonParser().parse(payload).getAsJsonObject();
        // validate user token
        if (payloadObject.has("token") && payloadObject.has("businessID")) {
            if (Utils.validToken(payloadObject.get("token").getAsString(), payloadObject.get("businessID").getAsString())) {
                String businessID = payloadObject.get("businessID").getAsString();
                System.out.println("Searching for carts");
                // TODO: change returned carts to include item names array
                return DatabaseService.getLast24HourCartsByBusinessID(businessID);
            } else {
                return Utils.generateResponse(false, "Not authorized");
            }
        } else {
            return Utils.generateResponse(false, "Requires: token, businessID");
        }
    }
}
