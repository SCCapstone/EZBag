package org.tests;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import org.bson.Document;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.routes.*;
import org.services.StartupService;
import org.services.Utils;

import static junit.framework.TestCase.assertEquals;

public class BizTests extends JerseyTest {

    @Override
    protected Application configure() {
        StartupService.startup();
        return new ResourceConfig(BizLoginRoute.class, BizRegisterRoute.class,
                BizProductInsertRoute.class, BizProductDeleteRoute.class, BizCartsRoute.class);
    }

    // Test /register
    @Test
    public void testRegister() {
        // TODO: finish writing test
        Document exampleUser = new Document();
        String randEmail = String.valueOf((int)Math.round(Math.random() * (100000 - 0 + 1))) + "@email.com";
        exampleUser.append("businessName", "Test Biz 1")
                .append("streetAddress", "650 Lincoln Street")
                .append("city", "Columbia")
                .append("state", "SC")
                .append("country", "US")
                .append("email", randEmail)
                .append("phone", "8603337654")
                .append("password", "BadPassword1");
        final String response1 = target("register").request().post(Entity.text(exampleUser.toJson()), String.class);
        JsonObject payloadObject = new JsonParser().parse(response1).getAsJsonObject();
        System.out.println(payloadObject.toString());
        assertEquals(payloadObject.get("status").getAsString(), "success");
    }

    // Tests /login
    @Test
    public void testLogin() {
        // TODO: finish
        Document query = new Document();
        query.append("email", "owner@store.com");
        query.append("password", "password");
        final String response1 = target("/login").request().post(Entity.text(query.toJson()), String.class);
        JsonObject payloadObject = new JsonParser().parse(response1).getAsJsonObject();
        assertEquals(payloadObject.get("status").getAsString(), "success");
        assertEquals(payloadObject.has("token"), true);
        System.out.println(payloadObject.toString());
    }

    // Test get /merchant/carts
    @Test
    public void testCartsQuery() {
        // login
        Document query = new Document();
        query.append("email", "owner@store.com");
        query.append("password", "password");
        final String response1 = target("/login").request().post(Entity.text(query.toJson()), String.class);
        JsonObject payloadObject = new JsonParser().parse(response1).getAsJsonObject();
        System.out.println("Login result:");
        System.out.println(payloadObject.toString());

        // get credentials
        String token = payloadObject.get("token").getAsString();
        String businessID = payloadObject.get("businessID").getAsString();

        // test get carts
        Document query2 = new Document();
        query2.append("businessID", businessID);
        query2.append("token", token);
        System.out.println("Valid token? "+Utils.validToken(token, businessID));
        System.out.println("Query2: "+query2.toJson());

        final String response2 = target("merchant/carts").request().post(Entity.text(query2.toJson()), String.class);
        JsonObject cartsPayload = new JsonParser().parse(response2).getAsJsonObject();
        System.out.println("Carts result:");
        System.out.println(cartsPayload.toString());
        assertEquals(cartsPayload.get("status").getAsString(), "success");
    }

    // Test get /merchant/carts
    @Test
    public void testProductInsert() {
        // login
        Document query = new Document();
        query.append("email", "owner@store.com");
        query.append("password", "password");
        final String response1 = target("/login").request().post(Entity.text(query.toJson()), String.class);
        JsonObject payloadObject = new JsonParser().parse(response1).getAsJsonObject();
        System.out.println("Login result:");
        System.out.println(payloadObject.toString());

        // get credentials
        String token = payloadObject.get("token").getAsString();
        String businessID = payloadObject.get("businessID").getAsString();

        // create product to insert
        Document insertDoc = new Document();
        String randBarcode = String.valueOf((int)Math.round(Math.random() * (1000000000 - 0 + 1)));
        String randomProductName = String.valueOf((int)Math.round(Math.random() * (100000000 - 0 + 1)));
        insertDoc.append("barcode", randBarcode)
                .append("barcodeType", "ean8")
                .append("businessID", businessID)
                .append("name", randomProductName)
                .append("price", 1.99)
                .append("tax", 0.03)
                .append("description", "Hello world this is am example product")
                .append("token", token);

        // test insert product
        final String response2 = target("product").request().post(Entity.text(insertDoc.toJson()), String.class);
        JsonObject responsePayload = new JsonParser().parse(response2).getAsJsonObject();
        System.out.println("Product insert result:");
        System.out.println(responsePayload.toString());
        assertEquals(responsePayload.get("status").getAsString(), "success");
    }

    // Test get /merchant/carts
    @Test
    public void testProductInsertAndDelete() {
        // login
        Document query = new Document();
        query.append("email", "owner@store.com");
        query.append("password", "password");
        final String response1 = target("/login").request().post(Entity.text(query.toJson()), String.class);
        JsonObject payloadObject = new JsonParser().parse(response1).getAsJsonObject();
        System.out.println("Login result:");
        System.out.println(payloadObject.toString());

        // get credentials
        String token = payloadObject.get("token").getAsString();
        String businessID = payloadObject.get("businessID").getAsString();

        // create product to insert
        Document insertDoc = new Document();
        String randBarcode = String.valueOf((int)Math.round(Math.random() * (1000000000 - 0 + 1)));
        String randomProductName = String.valueOf((int)Math.round(Math.random() * (100000000 - 0 + 1)));
        insertDoc.append("barcode", randBarcode)
                .append("barcodeType", "ean8")
                .append("businessID", businessID)
                .append("name", randomProductName)
                .append("price", 1.99)
                .append("tax", 0.03)
                .append("description", "Hello world this is am example product")
                .append("token", token);

        // test insert product
        final String response2 = target("product").request().post(Entity.text(insertDoc.toJson()), String.class);
        JsonObject responsePayload = new JsonParser().parse(response2).getAsJsonObject();
        System.out.println("Product insert result:");
        System.out.println(responsePayload.toString());
        assertEquals(responsePayload.get("status").getAsString(), "success");

        // delete product that was inserted
        Document deleteDoc = new Document();
        deleteDoc.append("barcode", randBarcode)
                .append("businessID", businessID)
                .append("token", token);
        final String response3 = target("delete").request().post(Entity.text(insertDoc.toJson()), String.class);
        JsonObject deletePayload = new JsonParser().parse(response3).getAsJsonObject();
        System.out.println("Product insert result:");
        System.out.println(deletePayload.toString());
        assertEquals(deletePayload.get("status").getAsString(), "success");

    }

    // test sales by barcode

    // test verify token

    // test products route

}
