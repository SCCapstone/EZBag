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

@Path("/merchant/querySales")
public class BizSalesByBarcode {
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String doPost(String payload, @HeaderParam("Authorization") String authToken) {
        JsonObject payloadObject = new JsonParser().parse(payload).getAsJsonObject();
        if (payloadObject.has("token") && payloadObject.has("businessID") && payloadObject.has("barcode")){
            if(Utils.validToken(payloadObject.get("token").getAsString(), payloadObject.get("businessID").getAsString())){
                String businessID = payloadObject.get("businessID").getAsString();
                String barcode = payloadObject.get("barcode").getAsString();
                return DatabaseService.getSalesByBarcode(businessID, barcode);
            }
        }
        System.out.println("Malformed request");
        System.out.println(payload);
        return "nay";
    }
}