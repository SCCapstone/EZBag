package org.tests;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import org.bson.Document;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.routes.BizCartsRoute;
import org.routes.BizSalesByBarcode;
import org.routes.BizSalesRoute;
import org.services.DatabaseService;
import org.services.StartupService;
import org.services.Utils;

public class BizSalesByBarcodeTest extends JerseyTest {

    @Override
    protected Application configure() {
        StartupService.startup();
        return new ResourceConfig(BizSalesByBarcode.class);
    }

    @Test
    public void testSalesQuery() {
        Document query = new Document();
        String businessID = "179aa3e0fb88f6e4ec0ef0d0f5588d43f93713e7b7e4a5ddd8a3fdd1c39701fa";
        String email = "owner@store.com";
        String token = Utils.createJWT(email, "bearer", businessID, 86400000);
        query.append("businessID", businessID);
        query.append("barcode", "1234");
        query.append("days","1");
        query.append("token", token);
        final String response1 = target("merchant/querySales").request().post(Entity.text(query.toJson()), String.class);
        System.out.println(response1);
        JsonObject payloadObject = new JsonParser().parse(response1).getAsJsonObject();
        System.out.println(payloadObject.toString());
    }
}
