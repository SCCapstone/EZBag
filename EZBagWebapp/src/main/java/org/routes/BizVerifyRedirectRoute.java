package org.routes;

import com.google.gson.JsonObject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.bson.Document;
import org.services.DatabaseService;
import org.services.EmailService;
import org.services.QRCodeService;
import org.services.Utils;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

@Path("/verify/{id}")
public class BizVerifyRedirectRoute {
    @GET
    @Produces({MediaType.TEXT_HTML})
    public String redirect(@PathParam("id") String userID) {
        String returnDoc = "";
        System.out.println("Verifying userID: "+userID);
        JsonObject resp = new JsonObject();
        // check if user exists
        boolean exists = DatabaseService.userWithIDExists(userID);
        System.out.println("User exists: " + exists);
        String message = "User with given userID does not exist";
        boolean verified = false;
        if (exists) {
            // mark user verified
            verified = DatabaseService.verifyUser(userID);
            if (!verified) {
                message = "User not verified, hash or businessID invalid";
                System.out.println("User not verified");
                returnDoc = "User not verified";
//                returnDoc = "" +
//                        "<html>\n" +
//                        "<script>\n" +
//                        // TODO: test this
//                        "window.location.href = '/EZBagWebapp/#/';\n" +
//                        "</script>\n" +
//                        "</html>\n";
            } else {
                System.out.println("User verified");
                ArrayList<String> params = new ArrayList<>();
                params.add("businessID");
                params.add("email");
                Document doc = DatabaseService.getUserInfoByID(userID, params);
                String email = doc.getString("email");
                String businessID = doc.getString("businessID");
                BufferedImage qrCode = null;
                try {
                    qrCode = QRCodeService.generateQRCodeImage("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                int responseCode = EmailService.sendEmailWithContent(email, "Your EZBag QRCode", "Welcome to EZBag! Below you will find your QR Code that customers can use to reach your business!", qrCode);
                message = "Cart verified";
//                returnDoc = "" +
//                        "<html>\n" +
//                        "<script>\n" +
//                        // TODO: test this
//                        "window.location.href = '/EZBagWebapp/#/login';\n" +
//                        "</script>\n" +
//                        "</html>\n";
                returnDoc = "User verified";
            }
        } else {
            System.out.println("User with ID does not exist");
//            returnDoc = "" +
//                    "<html>\n" +
//                    "<script>\n" +
//                    // TODO: test this
//                    "window.location.href = '/EZBagWebapp/#/';\n" +
//                    "</script>\n" +
//                    "</html>\n";
            returnDoc = "User not verified";

        }
        return returnDoc;
    }
}
