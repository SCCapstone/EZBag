package org.routes;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.services.DatabaseService;
import org.services.Utils;

// verify cart
@Path("/merchant/token")
public class BizVerifyToken {
    @POST
    //@Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String doPost(String payload, @HeaderParam("Authorization") String authToken) {
        JsonObject payloadObject = new JsonParser().parse(payload).getAsJsonObject();
        if (payloadObject.has("token") && payloadObject.has("businessID")){
            String token = payloadObject.get("token").getAsString();
            String businessID = payloadObject.get("businessID").getAsString();
            System.out.println("Verifying token: "+token);
            if(Utils.validToken(token, businessID)) {
                return Utils.generateResponse(true, "Valid token");
            } else {
                return Utils.generateResponse(false, "Invalid token");
            }
        } else {
            return Utils.generateResponse(false, "Must provide token and businessID");
        }
    }
}


















