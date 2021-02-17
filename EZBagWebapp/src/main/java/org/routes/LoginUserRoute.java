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

@Path("/login")
public class LoginUserRoute {
    @POST
    //@Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String doPost(String payload) {
        JsonObject payloadObject = new JsonParser().parse(payload).getAsJsonObject();
        Document loginDoc = new Document();
        String message = "";
        String status = "";
        String token = null;
        if (payloadObject.has("email")
                && payloadObject.has("password"))
        {
            String email = payloadObject.get("email").getAsString();
            String password = payloadObject.get("password").getAsString();
            if (DatabaseService.userExists(email)) {
                // todo
                if (DatabaseService.userLoginCredentialsValid(email, password)) {
                    // todo generate token, save in database, return it
                    token = Utils.createJWT(email, "bearer", "business", 86400000);
                    message = "Logged in!";
                    status = "success";
                } else {
                    message = "User with the provided email address does not exist.";
                    status = "success";
                }
            } else {
                message = "User with the provided email address does not exist.";
                status = "success";
            }
            // TODO check password hash against hash stored in database
            // if match
            // TODO deactive old access token if exists in db?
                // generate a token object w/ email, token, and expiry timestamp
                // insert token into token collection
                // send token back to user to be stored in secure cookie
        } else
        {
            message = "Login requires: email, password";
            status = "success";
        }
        JsonObject response = new JsonObject();
        response.addProperty("message", message);
        response.addProperty("status", status);
        if (token != null)
            response.addProperty("token", token);
        return response.toString();
    }
}