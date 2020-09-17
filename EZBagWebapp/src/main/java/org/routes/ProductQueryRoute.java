package org.routes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.Document;
import org.services.DatabaseService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

@Path("/lookup")
public class ProductQueryRoute {
    @POST
    //@Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String doPost(String payload) {
        JsonObject payloadObject = new JsonParser().parse(payload).getAsJsonObject();
        Document insertDoc = new Document();
        if (payloadObject.has("barcode")
                && payloadObject.has("barcodeType")
                && payloadObject.has("businessID"))
        {
            String resp = DatabaseService.getProductByBarcodeBarcodeTypeBusinessID(payloadObject.get("barcode").getAsString(),
                    payloadObject.get("barcodeType").getAsString(),
                    payloadObject.get("businessID").getAsString());
            return resp;
        } else
        {
            String message = "Barcode product lookup requires: barcode, barcodeType, businessID";
            JsonObject response = new JsonObject();
            response.addProperty("message", message);
            return response.toString();
        }
    }
}











