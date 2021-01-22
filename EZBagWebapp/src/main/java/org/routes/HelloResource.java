package org.routes;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.services.StartupService;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

@Path("hello")
public class HelloResource extends JerseyTest {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String doPost(Map<String, String> data) {
        return "Hello " + data.get("name") + "!";
    }

    @Override
    protected Application configure() {
        StartupService.startup();
        return new ResourceConfig(HelloResource.class);
    }

    @Test
    public void testPost() {
        Map<String, String> data = new HashMap<String, String>();
        data.put("name", "popovitsj");

        final String hello = target("hello").request().post(Entity.json(data), String.class);

        assertEquals("Hello popovitsj!", hello);
    }
}