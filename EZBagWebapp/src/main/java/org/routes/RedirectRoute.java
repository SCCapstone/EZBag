package org.routes;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import javax.ws.rs.Consumes;
import javax.ws.rs.QueryParam;

@Path("/redirect/{id}")
public class RedirectRoute {
    @GET
    @Produces({MediaType.TEXT_HTML})
    public String redirect(@PathParam("id") String ID) {
        System.out.println(ID);
        String returnDoc = "" +
                "<html>\n" +
                "<script>\n" +
                "myStorage = window.localStorage\n" +
                "localStorage.setItem('clearCart', 'true')\n" +
                "localStorage.setItem('redirectID', '" + ID + "')\n" +
                "window.location.href = '../../';\n" +
                "</script>\n" +
                "</html>\n";
        return returnDoc;
    }
}
