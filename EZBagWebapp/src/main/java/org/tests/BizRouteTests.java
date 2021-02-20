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
import org.routes.ProductQueryRoute;
import org.services.DatabaseService;
import org.services.StartupService;

import static junit.framework.TestCase.assertEquals;

public class BizRouteTests extends JerseyTest {

    @Override
    protected Application configure() {
        StartupService.startup();
        return new ResourceConfig(BizCartsRoute.class);
    }

    @Test
    public void testCartsQuery() {
        // TODO: finish
        Document query = new Document();
        query.append("businessID", "1");
        final String response1 = target("merchant/carts").request().post(Entity.text(query.toJson()), String.class);
        JsonObject payloadObject = new JsonParser().parse(response1).getAsJsonObject();
        System.out.println(payloadObject.toString());
    }
}
