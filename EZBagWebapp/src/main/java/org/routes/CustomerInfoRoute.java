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
    public String doPost(String payload) {
        JsonObject payloadObject = new JsonParser().parse(payload).getAsJsonObject();
        Document insertDoc = new Document();
        if ((payloadObject.has("email") || payloadObject.has("phone"))
                && payloadObject.has("hash") && payloadObject.has("session")) {

            // check cart collection for hash, aka existence of cart
            String cartHash = payloadObject.get("hash").getAsString();
            Document customerCart = DatabaseService.getCustomerCartByHash(cartHash);
            String receipt = "";
            if (customerCart != null) {
                // generate receipt
                receipt = ReceiptService.generateEReceipt(customerCart);
            } else {
                // submitted invalid customer cart hash
                String message = "Customer cart was not found in the database";
                return Utils.generateResponse(false, message);
            }

            if (payloadObject.has("email")) {
                insertDoc.append("email", payloadObject.get("email").getAsString());
            }
            if (payloadObject.has("phone")) {
                insertDoc.append("phone", payloadObject.get("phone").getAsString());
            }
            insertDoc.append("hash", payloadObject.get("hash").getAsString());
            insertDoc.append("session", payloadObject.get("session").getAsString());
            insertDoc.append("time", System.currentTimeMillis());

            // check if customer has already in customer info database
            // if it does not, insert, if it does, send back error response
            String resp = DatabaseService.insertInfo(insertDoc);
            System.out.println(resp);
            // if insertInfo returns failure status, skip sending of digital receipt and return error msg
            JsonObject respJson = new JsonParser().parse(resp).getAsJsonObject();
            String status = respJson.get("status").getAsString();

            if (!status.equals("failure")) {

                JsonObject response = new JsonObject();
                try {
                    if (payloadObject.has("email"))
                    {
                        response.addProperty("emailStatus", "success");
                        String email = payloadObject.get("email").getAsString();
                        EmailService.sendEmail(email, "EZBag eReceipt", receipt);
                    }
                } catch (Exception e) {
                    System.out.println("Error when sending receipt via email");
                    response.remove("emailStatus");
                    response.addProperty("emailStatus", "failure");
                }

                try {
                    if (payloadObject.has("phone"))
                    {
                        response.addProperty("phoneStatus", "success");
                        String number = payloadObject.get("phone").getAsString();
                        SMSService.sendSMS(number, receipt);
                    }
                } catch (Exception e) {
                    System.out.println("Error when sending receipt via phone");
                    response.remove("phoneStatus");
                    response.addProperty("phoneStatus", "failure");
                }
                return response.toString();

            } else {
                String message = "Digital receipt already requested.";
                return Utils.generateResponse(false, message);
            }

        } else
        {
            String message = "Submitted invalid customer info object.";
            return Utils.generateResponse(false, message);
        }
    }
}