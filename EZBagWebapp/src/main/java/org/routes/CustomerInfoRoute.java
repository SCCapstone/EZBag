package org.routes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.Document;
import org.services.DatabaseService;
import org.services.SMSService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

@Path("/info")
public class CustomerInfoRoute {
    @POST
    //@Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    // {}
    public String doPost(String payload) {
        JsonObject payloadObject = new JsonParser().parse(payload).getAsJsonObject();
        Document insertDoc = new Document();
        if (payloadObject.has("phone") || payloadObject.has("phone")) {
            if (payloadObject.has("email"))
            {
                insertDoc.append("email", payloadObject.get("email").getAsString());

            }
            if (payloadObject.has("phone"))
            {
                String number = payloadObject.get("phone").getAsString();
                insertDoc.append("phone", number);
                SMSService.sendSMS(number, "Your transaction is complete!");
            }
            insertDoc.append("time", System.currentTimeMillis());
            System.out.println(insertDoc.toString());
            String resp = DatabaseService.insertInfo(insertDoc);
            return resp;
        } else
        {
            String message = "Submitted customer info json object is not valid";
            System.out.println(message);
            JsonObject response = new JsonObject();
            response.addProperty("message", message);
            return response.toString();
        }
    }
}