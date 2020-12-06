package org.routes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.Document;
import org.services.*;

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
        if ((payloadObject.has("phone") || payloadObject.has("phone"))
                && payloadObject.has("hash")) {
            // TODO: generate customer receipt string here
            String cartHash = payloadObject.get("hash").getAsString();
            Document customerCart = DatabaseService.getCustomerCartByHash(cartHash);
            // TODO: handle case when customer cart returns as null
            String receipt = "";
            if (customerCart != null) {
                // generate receipt
                receipt = ReceiptService.generateEReceipt(customerCart);
            } else {
                // submitted invalid customer cart hash
                String message = "Customer cart was not found in the database";
                return Utils.generateResponse(false, message);
            }

            if (payloadObject.has("email"))
            {
                String email = payloadObject.get("email").getAsString();
                insertDoc.append("email", email);
                EmailService.sendEmail(email, "EZBag eReceipt", receipt);
            }
            if (payloadObject.has("phone"))
            {
                String number = payloadObject.get("phone").getAsString();
                insertDoc.append("phone", number);
                SMSService.sendSMS(number, receipt);
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