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
        if (payloadObject.has("token") && payloadObject.has("businessID")
                && payloadObject.has("barcode") && payloadObject.has("days")){
            if(Utils.validToken(payloadObject.get("token").getAsString(), payloadObject.get("businessID").getAsString())){
                String businessID = payloadObject.get("businessID").getAsString();
                String barcode = payloadObject.get("barcode").getAsString();
                int days = Integer.parseInt(payloadObject.get("days").getAsString());
                String resp = "";
                if(barcode.equals("")){
                    resp = DatabaseService.getLastDaysCartsByBusinessID(businessID, days);
                }else {
                    resp = DatabaseService.getSalesByBarcode(businessID, barcode, days);
                }
                return resp;
            } else {
                return Utils.generateResponse(false, "Not authorized");
            }
        } else {
            return Utils.generateResponse(false, "Past sales by barcode lookup requires: businessID, barcode");
        }
    }
}