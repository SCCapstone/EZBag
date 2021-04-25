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

import java.awt.image.DataBuffer;

@Path("/merchant/sales")
public class BizSalesRoute {
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String doPost(String payload, @HeaderParam("Authorization") String authToken) {
        // validate user token
        JsonObject payloadObject = new JsonParser().parse(payload).getAsJsonObject();
        if (payloadObject.has("token") && payloadObject.has("businessID")
                && Utils.validToken(payloadObject.get("token").getAsString(), payloadObject.get("businessID").getAsString())) {
            if (payloadObject.has("businessID") && payloadObject.has("days")) {
                String businessID = payloadObject.get("businessID").getAsString();
                int days = Integer.parseInt(payloadObject.get("days").getAsString());
                System.out.println("Searching for carts");
                // TODO: change returned carts to include item names array
                return DatabaseService.getLastDaysCartsByBusinessID(businessID, days);
            } else {
                return Utils.generateResponse(false, "Past sales lookup requires: businessID, days");
            }
        } else {
            return Utils.generateResponse(false, "Not authorized");
        }
    }
}
