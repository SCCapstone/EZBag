package org.tests;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
import org.routes.ProductInsertRoute;
import org.routes.ProductQueryRoute;
import org.routes.ProductSearchRoute;
import org.routes.ProductsRoute;
import org.services.DatabaseService;
import org.services.StartupService;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ProductRouteTests extends JerseyTest {
    @Override
    protected Application configure() {
        StartupService.startup();
        return new ResourceConfig(ProductQueryRoute.class, ProductInsertRoute.class,
                ProductSearchRoute.class, ProductsRoute.class);
    }

    @Test
    public void testPost() {

        // insert example product to the database to be used for testing
        Document insertDoc = new Document();
        int min = 10000000;
        int max = 99999999;
        int barcodeInt = (int)Math.round(Math.random() * (max - min + 1) + min);
        System.out.println(barcodeInt);
        String barcode = String.valueOf(barcodeInt);
        insertDoc.append("barcode", barcode)
                .append("barcodeType", "ean8")
                .append("businessID", "1")
                .append("name", "Example Product")
                .append("price", 9.99)
                .append("tax", 0.06)
                .append("description", "Example Product is used for testing. It is not a real item!")
                .append("time", System.currentTimeMillis());
        String resp = DatabaseService.insertProduct(insertDoc);
        JsonObject insertJson = new JsonParser().parse(insertDoc.toJson()).getAsJsonObject();
        System.out.println("Inserting: \n" + insertJson.toString());

        Document exampleItem = new Document();
        exampleItem.append("barcode", barcode)
                .append("businessID", "1");

        Document emptyItem = new Document();
        emptyItem.append("barcode", "1")
                .append("businessID", "1");

        Document brokenItem = new Document();
        emptyItem.append("asdf", "1");

        final String response1 = target("lookup").request().post(Entity.text(exampleItem.toJson()), String.class);
        JsonObject payloadObject1 = new JsonParser().parse(response1).getAsJsonObject();
        System.out.println("received: \n"+payloadObject1.toString());

        final String response2 = target("lookup").request().post(Entity.text(emptyItem.toJson()), String.class);

        final String response3 = target("lookup").request().post(Entity.text(brokenItem.toJson()), String.class);
        JsonObject payloadObject3 = new JsonParser().parse(response2).getAsJsonObject();
        payloadObject3.remove("_id");

        assertEquals(payloadObject1.toString(), insertJson.toString());
        assertEquals(response2, "{\"message\":\"Product was not found in the database\",\"status\":\"failure\"}");
        assertEquals(response3, "{\"message\":\"Barcode product lookup requires: barcode, businessID\",\"status\":\"failure\"}");
       }

    @Test
    public void testSearch() {

        // insert example product to the database to be used for testing
        Document searchObj = new Document();
        searchObj.append("businessID", "1")
                .append("query", "example product");
        final String response1 = target("search").request().post(Entity.text(searchObj.toJson()), String.class);
        JsonObject payloadObject1 = new JsonParser().parse(response1).getAsJsonObject();
        System.out.println("received: \n"+payloadObject1.toString());
    }

    @Test
    public void getProducts() {

        // insert example product to the database to be used for testing
        Document searchObj = new Document();
        searchObj.append("businessID", "1");
        final String response1 = target("products").request().post(Entity.text(searchObj.toJson()), String.class);
        JsonObject payloadObject1 = new JsonParser().parse(response1).getAsJsonObject();
        System.out.println("received: \n"+payloadObject1.toString());
    }

}
