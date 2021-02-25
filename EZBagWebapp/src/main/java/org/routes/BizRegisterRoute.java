package org.routes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.apache.commons.codec.digest.DigestUtils;
import org.bson.Document;
import org.services.DatabaseService;
import org.services.EmailService;
import org.services.Utils;

import java.util.*;

@Path("/register")
public class BizRegisterRoute {
    static final String successfulSignupMessage = "Hello <business>,\n" +
            "Thank you for registering with EZBag! Your application is currently under review. Upon approval, you will get full access to the EZBag merchant platform. \n\nThanks,\nEZBag Team";

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String doPost(String payload) {
        JsonObject payloadObject = new JsonParser().parse(payload).getAsJsonObject();
        Document insertDoc = new Document();

        if (payloadObject.has("businessName")
                && payloadObject.has("streetAddress")
                && payloadObject.has("city")
                && payloadObject.has("state")
                && payloadObject.has("country")
                && payloadObject.has("email")
                && payloadObject.has("phone")
                && payloadObject.has("password"))
        {
            // reject new user registration for already used email
            System.out.println(payloadObject.get("email").getAsString());
            System.out.println("User exists: " + DatabaseService.userExists(payloadObject.get("email").getAsString()));
            if(DatabaseService.userExists(payloadObject.get("email").getAsString()))
            {
                String message = "User with given email address already exists!";
                System.out.println(message);
                JsonObject response = new JsonObject();
                response.addProperty("message", message);
                response.addProperty("status", "failure");
                return response.toString();
            } else {
                String responseMessage = successfulSignupMessage.replace("<business>", payloadObject.get("businessName").getAsString());
                EmailService.sendEmail(payloadObject.get("email").getAsString(), "Your EZBag Confirmation", responseMessage);

                insertDoc.append("email", payloadObject.get("email").getAsString());
                insertDoc.append("phone", payloadObject.get("phone").getAsString());
                insertDoc.append("businessName", payloadObject.get("businessName").getAsString());
                insertDoc.append("streetAddress", payloadObject.get("streetAddress").getAsString());
                insertDoc.append("city", payloadObject.get("city").getAsString());
                insertDoc.append("state", payloadObject.get("state").getAsString());
                insertDoc.append("country", payloadObject.get("country").getAsString());
                insertDoc.append("email", payloadObject.get("email").getAsString());
                insertDoc.append("password", payloadObject.get("password").getAsString());
                insertDoc.append("role", 1);
                String hashString = payloadObject.get("email").getAsString();
                insertDoc.append("businessID", DigestUtils.sha256Hex(hashString));
                // TODO: change to reply with message of why the addition failed
                return DatabaseService.insertUser(insertDoc);
            }
        }
        String message = "Submitted user object is not valid";
        System.out.println(message);
        JsonObject response = new JsonObject();
        response.addProperty("message", message);
        response.addProperty("status", "failure");
        return response.toString();
    }
}




















