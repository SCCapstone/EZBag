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
        if (payloadObject.has("token") && Utils.validToken(payloadObject.get("token").getAsString())) {
            if (payloadObject.has("businessID") && payloadObject.has("interval")) {
                String businessID = payloadObject.get("businessID").getAsString();
                String interval = payloadObject.get("interval").getAsString();
                System.out.println("Searching for carts");
                // TODO: change returned carts to include item names array
                if(interval=="day") {
                    return DatabaseService.getLast24HourCartsByBusinessID(businessID);
                }else if(interval=="week") {
                    return DatabaseService.getLast7DaysCartsByBusinessID(businessID);
                }else{
                    return DatabaseService.getLast30DaysCartsByBusinessID(businessID);
                }
            } else {
                return Utils.generateResponse(false, "Barcode product lookup requires: businessID");
            }
        } else {
            return Utils.generateResponse(false, "Not authorized");
        }
    }
}
