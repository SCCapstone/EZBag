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

// verify cart
@Path("/merchant/verify")
public class BizCartRoute {
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String doPost(String payload, @HeaderParam("Authorization") String authToken) {
        JsonObject payloadObject = new JsonParser().parse(payload).getAsJsonObject();
        // validate user token
        if (payloadObject.has("token") && Utils.validToken(payloadObject.get("token").getAsString())) {
            if (payloadObject.has("cartHash") && payloadObject.has("businessID")) {
                String businessID = payloadObject.get("businessID").getAsString();
                String cartHash = payloadObject.get("cartHash").getAsString();
                System.out.println("Verifying customer cart");
                JsonObject resp = new JsonObject();
                // TODO : check if cart exists
                boolean exists = DatabaseService.cartExists(cartHash, businessID);
                System.out.println("Cart exists: " + exists);
                String message = "Cart with given cartHash, businessID does not exist";
                boolean verified = false;
                if (exists) {
                    // mark customer cart as verified w/ cartHash and businessID
                    // TODO write logic in verify cart to ensure update happened
                    verified = DatabaseService.verifyCart(cartHash, businessID);
                    if (!verified) {
                        message = "Cart not verified, hash or businessID invalid";
                    } else {
                        message = "Cart verified";
                    }
                }
                return Utils.generateResponse(verified, message);
            } else {
                return Utils.generateResponse(false, "Barcode product lookup requires: businessID");
            }
        } else {
            return Utils.generateResponse(false, "Not authorized");
        }
    }
}


















