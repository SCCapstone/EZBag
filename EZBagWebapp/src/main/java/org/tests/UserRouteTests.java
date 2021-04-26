package org.tests;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;

import org.bson.Document;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.routes.BizLoginRoute;
import org.routes.BizRegisterRoute;
import org.services.DatabaseService;
import org.services.StartupService;
import org.services.Utils;


import java.lang.reflect.Array;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

public class UserRouteTests extends JerseyTest {
    @Override
    protected Application configure() {
        StartupService.startup();
        return new ResourceConfig(BizRegisterRoute.class, BizLoginRoute.class);
    }

    @Test
    public void testLogin() {
        // TODO: finish writing test
        Document exampleUser = new Document();
        exampleUser.append("email", "owner@store.com")
                .append("password", "password");

        final String response1 = target("login").request().post(Entity.text(exampleUser.toJson()), String.class);
        JsonObject payloadObject = new JsonParser().parse(response1).getAsJsonObject();
        System.out.println(payloadObject.toString());
        // TODO message is simply inserted object in text form
        assertEquals("success",payloadObject.get("status").getAsString());

        // TODO write test to query user as admin and ensure all info inserted correctly
    }

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
        // TODO message is simply inserted object in text form
        assertEquals("success",payloadObject.get("status").getAsString());

        // TODO write test to query user as admin and ensure all info inserted correctly
    }

    @Test
    public void testUserInfo() {
        ArrayList<String> params = new ArrayList<>();
        params.add("businessID");
        params.add("email");
        Document doc = DatabaseService.getUserInfoByID("d50d5c026d0d82dcc6b6c6e7f2fa4f6f845665e069923c5bc2e8f8937b237ef8", params);
        System.out.println(doc.toJson());
        System.out.println(doc.getString("email"));
    }


}
