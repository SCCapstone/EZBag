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
        Document insertDoc = new Document();
        insertDoc.append("barcode", "12345678")
                .append("businessID", "1");
        final String response = target("lookup").request().post(Entity.text(insertDoc.toJson()), String.class);
        assertEquals(response, "{ \"_id\" : { \"$oid\" : \"6005de23132a110351032487\" }, \"barcode\" : \"12345678\", \"barcodeType\" : \"ean8\", \"businessID\" : \"1\", \"name\" : \"Example Product\", \"price\" : 9.99, \"tax\" : 0.6, \"description\" : \"Example Product is used for testing. It is not a real item!\", \"time\" : { \"$numberLong\" : \"1610997283891\" } }");
    }
}
