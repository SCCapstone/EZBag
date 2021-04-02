package org.services;

import com.google.gson.JsonObject;
import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

public class Utils {
    public static String generateResponse(Boolean success, String message) {
        JsonObject resp = new JsonObject();
        resp.addProperty("message", message);
        if (success)
        {
            resp.addProperty("status", "success");
            return resp.toString();
        }
        resp.addProperty("status", "failure");
        return resp.toString();
    }
    public static boolean isEAN(String barcode)
    {
        return (barcode.length()==13 || barcode.length()==8);
    }
    public static boolean isUPC(String barcode)
    {
        return barcode.length()==12;
    }

    public static Properties getPropertiesFile(String fileName)
    {
        FileInputStream fis = null;
        Properties prop = null;
        try {
            fis = new FileInputStream(fileName);
            prop = new Properties();
            prop.load(fis);
        } catch(FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return prop;
    }

    public static String generateCartHash() {
        Random rand = new Random();
        String alphaNumericString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 20; i+=1) {
            builder.append(alphaNumericString.charAt(rand.nextInt(alphaNumericString.length())));
        }
        return builder.toString();
    }

    public static String generateNonce(int num) {
        String symbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        String nonce = "";
        Random rand = new Random();
        for (int i = 0; i<num; i++) {
            int loc = rand.nextInt(symbols.length());
            nonce += symbols.substring(loc,loc+1);
        }
        return nonce;
    }

    // CREDIT TO: https://developer.okta.com/blog/2018/10/31/jwts-with-java
    public static String createJWT(String id, String issuer, String subject, long ttlMillis) {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(DatabaseService.SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        //if it has been specified, let's add the expiration
        if (ttlMillis > 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    // CREDIT TO: https://developer.okta.com/blog/2018/10/31/jwts-with-java
    public static Claims decodeJWT(String jwt) {
        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(DatabaseService.SECRET_KEY))
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

    public static boolean validToken(String token, String businessID) {
        // TODO: get token claims, get email, get businessID of user, make sure matches requested
        // if token decode fails with expired exception then return false
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(DatabaseService.SECRET_KEY))
                    .parseClaimsJws(token).getBody();
            System.out.println("User Token Claims: " + claims.toString());
            // check if claims business ID matches ask
            String claimsBusinessID = claims.getSubject();
            return claimsBusinessID.equals(businessID);
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token expired");
        } catch (SignatureException e) {
            System.out.println("Signature error occurred while parsing JWT");
        } catch(Exception e){
            System.out.println("Error occurred while parsing JWT");
        }
        return false;
    }

}
