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

@Path("/merchant/verify")
public class BizCartRoute {
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String doPost(String payload, @HeaderParam("Authorization") String authToken) {
        // validate user token
        if (Utils.validToken(authToken)) {
            JsonObject payloadObject = new JsonParser().parse(payload).getAsJsonObject();
            if (payloadObject.has("cartHash") && payloadObject.has("businessID")) {
                String businessID = payloadObject.get("cartHash").getAsString();
                System.out.println("Verifying customer cart");
                // TODO: mark customer cart as verifiedw/ cartHash and businessID
//                return DatabaseService.verifyCart();
                return "in progress";
            } else {
                return Utils.generateResponse(false, "Barcode product lookup requires: businessID");
            }
        } else {
            return Utils.generateResponse(false, "Not authorized");
        }
    }
}
