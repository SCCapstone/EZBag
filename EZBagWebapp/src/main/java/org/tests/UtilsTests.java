
package org.tests;

import io.jsonwebtoken.Claims;
import jakarta.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import org.services.StartupService;



import org.junit.Test;
import org.services.Utils;

import static junit.framework.TestCase.assertEquals;

public class UtilsTests extends JerseyTest {
    @Override
    protected Application configure() {
        StartupService.startup();
        return new ResourceConfig(Utils.class);
    }

    @Test
    public void testJWTs() {
        String jwt = Utils.createJWT("1", "ezbagapi", "blakete@email.sc.edu", (long)(86400000));
        System.out.println(jwt);
        Claims claims = Utils.decodeJWT(jwt);
        System.out.println(claims.toString());
    }

}
