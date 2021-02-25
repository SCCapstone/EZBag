package org.routes;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.bson.Document;
import org.services.DatabaseService;
import org.services.Utils;


@Path("/search")
public class ProductSearchRoute {
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String doPost(String payload) {
        JsonObject payloadObject = new JsonParser().parse(payload).getAsJsonObject();
        Document insertDoc = new Document();
        if (payloadObject.has("query")
                && payloadObject.has("businessID"))
        {
            String resp = DatabaseService.searchProductsByBusinessIDQuery(payloadObject.get("businessID").getAsString(),
                    payloadObject.get("query").getAsString());
            return resp;
        } else
        {
            String message = "Product search requires: query, businessID";
            return Utils.generateResponse(false, message);
        }
    }
}
