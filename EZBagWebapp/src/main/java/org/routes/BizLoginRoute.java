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

import javax.xml.crypto.Data;

@Path("/login")
public class BizLoginRoute {
    @POST
    //@Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String doPost(String payload) {
        JsonObject payloadObject = new JsonParser().parse(payload).getAsJsonObject();
        Document loginDoc = new Document();
        String message = "";
        String status = "";
        String token = null;
        String businessID = null;
        if (payloadObject.has("email")
                && payloadObject.has("password"))
        {
            String email = payloadObject.get("email").getAsString();
            String password = payloadObject.get("password").getAsString();
            if (DatabaseService.userExists(email)) {
                // todo
                System.out.println("User exists");
                if (DatabaseService.userLoginCredentialsValid(email, password)) {
                    if (DatabaseService.userIsVerified(email)) {
                        // TODO: get business ID and add to payload to be returned
                        businessID = DatabaseService.getUserBusinessID(email);
                        token = Utils.createJWT(email, "bearer", "business", 86400000);
//                        token = Utils.createJWT(email, "bearer", "business", 10000);
                        message = "Logged in!";
                        status = "success";
                    } else {
                        message = "User email address has not been verified.";
                        status = "failure";
                    }
                } else {
                    message = "User email and password combination is incorrect.";
                    status = "failure";
                }
            } else {
                message = "User with the provided email address does not exist.";
                status = "failure";
            }
        } else
        {
            message = "Login requires: email, password";
            status = "failure";
        }
        JsonObject response = new JsonObject();
        response.addProperty("message", message);
        response.addProperty("status", status);
        if (token != null) {
            response.addProperty("token", token);
            response.addProperty("businessID", businessID);
        }

        return response.toString();
    }
}