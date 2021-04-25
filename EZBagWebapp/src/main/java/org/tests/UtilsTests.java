
package org.tests;

import io.jsonwebtoken.Claims;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;

import org.apache.commons.codec.digest.DigestUtils;
import org.bson.Document;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import org.routes.BizSalesByBarcode;
import org.routes.BizSalesRoute;
import org.services.StartupService;



import org.junit.Test;
import org.services.Utils;

import java.util.Random;

import static junit.framework.TestCase.assertEquals;

public class UtilsTests extends JerseyTest {
    @Override
    protected Application configure() {
        StartupService.startup();
        return new ResourceConfig(Utils.class, BizSalesRoute.class, BizSalesByBarcode.class);
    }

    @Test
    public void testJWTs() {
        String jwt = Utils.createJWT("1", "ezbagapi", "blakete@email.sc.edu", (long)(86400000));
        System.out.println(jwt);
        Claims claims = Utils.decodeJWT(jwt);
        System.out.println(claims.toString());

        System.out.println(DigestUtils.sha256Hex("ryoogfj4lpSi1lhaknOA"+"password"));

    }



}
