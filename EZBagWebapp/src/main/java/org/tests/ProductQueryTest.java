package org.tests;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;

import org.bson.Document;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.routes.ProductQueryRoute;
import org.services.StartupService;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ProductQueryTest extends JerseyTest {
    @Override
    protected Application configure() {
        StartupService.startup();
        return new ResourceConfig(ProductQueryRoute.class);
    }

    @Test
    public void testPost() {
        Document exampleItem = new Document();
        exampleItem.append("barcode", "12345678")
                .append("businessID", "1");

        Document emptyItem = new Document();
        emptyItem.append("barcode", "1")
                .append("businessID", "1");

        Document brokenItem = new Document();
        emptyItem.append("asdf", "1");
        final String response1 = target("lookup").request().post(Entity.text(exampleItem.toJson()), String.class);
        final String response2 = target("lookup").request().post(Entity.text(emptyItem.toJson()), String.class);
        final String response3 = target("lookup").request().post(Entity.text(brokenItem.toJson()), String.class);

        assertEquals(response1, "{ \"_id\" : { \"$oid\" : \"6005de23132a110351032487\" }, \"barcode\" : \"12345678\", \"barcodeType\" : \"ean8\", \"businessID\" : \"1\", \"name\" : \"Example Product\", \"price\" : 9.99, \"tax\" : 0.6, \"description\" : \"Example Product is used for testing. It is not a real item!\", \"time\" : { \"$numberLong\" : \"1610997283891\" } }");
        assertEquals(response2, "{\"message\":\"Product was not found in the database\",\"status\":\"failure\"}");
        assertEquals(response3, "{\"message\":\"Barcode product lookup requires: barcode, businessID\"}");
       }
}
