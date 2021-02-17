package org.tests;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;

import org.bson.Document;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.routes.RegisterUserRoute;
import org.services.DatabaseService;
import org.services.StartupService;


import static junit.framework.TestCase.assertEquals;

public class UserRouteTests extends JerseyTest {
    @Override
    protected Application configure() {
        StartupService.startup();
        return new ResourceConfig(RegisterUserRoute.class);
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
        // TODO message is simply inserted object in text form
        assertEquals(payloadObject.get("status").getAsString(), "success");

        // TODO write test to query user as admin and ensure all info inserted correctly


    }

    @Test
    public void testLogin() {
        // TODO: finish writing test
        Document exampleUser = new Document();
        String randEmail = String.valueOf((int)Math.round(Math.random() * (100000 - 0 + 1))) + "@email.com";
        exampleUser.append("email", "blakete@email.sc.edu")
                .append("password", "");
        final String response1 = target("register").request().post(Entity.text(exampleUser.toJson()), String.class);
        JsonObject payloadObject = new JsonParser().parse(response1).getAsJsonObject();
        assertEquals(payloadObject.get("status").getAsString(), "success");
    }

}
