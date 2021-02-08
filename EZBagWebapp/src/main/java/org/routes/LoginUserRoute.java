package org.routes;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.bson.Document;
import org.services.DatabaseService;

@Path("/login")
public class LoginUserRoute {
    @POST
    //@Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String doPost(String payload) {
        JsonObject payloadObject = new JsonParser().parse(payload).getAsJsonObject();
        Document loginDoc = new Document();
        if (payloadObject.has("email")
                && payloadObject.has("password"))
        {
            // TODO check password hash against hash stored in database
            // if match
            // TODO deactive old access token if exists in db?
                // generate a token object w/ email, token, and expiry timestamp
                // insert token into token collection
                // send token back to user to be stored in secure cookie

            return "under construction";
        } else
        {
            String message = "Login requires: email, password";
            JsonObject response = new JsonObject();
            response.addProperty("message", message);
            response.addProperty("status", "failure");
            return response.toString();
        }
    }
}