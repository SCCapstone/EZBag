package org.routes;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.ws.rs.*;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import org.bson.Document;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.services.DatabaseService;
import org.services.StartupService;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

@Path("/lookup")
public class ProductQueryRoute extends JerseyTest {
    @POST
    //@Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String doPost(String payload) {
        JsonObject payloadObject = new JsonParser().parse(payload).getAsJsonObject();
        Document insertDoc = new Document();
        if (payloadObject.has("barcode")
                && payloadObject.has("businessID"))
        {
            String resp = DatabaseService.getProductByBarcodeBusinessID(payloadObject.get("barcode").getAsString(),
                    payloadObject.get("businessID").getAsString());
            return resp;
        } else
        {
            String message = "Barcode product lookup requires: barcode, businessID";
            JsonObject response = new JsonObject();
            response.addProperty("message", message);
            return response.toString();
        }
    }

    @Override
    protected Application configure() {
        StartupService.startup();
        return new ResourceConfig(ProductQueryRoute.class);
    }

    @Test
    public void testPost() {
        Document insertDoc = new Document();
        insertDoc.append("barcode", "12345678")
                .append("businessID", "1");

        final String hello = target("lookup").request().post(Entity.json(insertDoc.toJson()), String.class);

        assertEquals("{ \"_id\" : { \"$oid\" : \"6005de23132a110351032487\" }, \"barcode\" : \"12345678\", \"barcodeType\" : \"ean8\", \"businessID\" : \"1\", \"name\" : \"Example Product\", \"price\" : 9.99, \"tax\" : 0.6, \"description\" : \"Example Product is used for testing. It is not a real item!\", \"time\" : { \"$numberLong\" : \"1610997283891\" } }", hello);
    }
}











