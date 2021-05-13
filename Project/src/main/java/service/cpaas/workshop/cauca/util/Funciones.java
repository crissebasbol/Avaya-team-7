/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.cpaas.workshop.cauca.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author umansilla
 */
public class Funciones {

    public void setAccessControlHeadersXML(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", Constants.ACCESS_ORIGIN);
        response.setHeader("Access-Control-Allow-Credentials", Constants.ACCESS_CREDENTIALS);
        response.setHeader("Access-Control-Allow-Methods", Constants.ACCESS_METHODS);
        response.setHeader("Access-Control-Allow-Headers", Constants.ACCESS_HEADERS);
        response.setHeader("Content-Type", Constants.CONTENT_TYPE_XML);
    }

    public void setAccessControlHeadersJSON(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", Constants.ACCESS_ORIGIN);
        response.setHeader("Access-Control-Allow-Credentials", Constants.ACCESS_CREDENTIALS);
        response.setHeader("Access-Control-Allow-Methods", Constants.ACCESS_METHODS);
        response.setHeader("Access-Control-Allow-Headers", Constants.ACCESS_HEADERS);
        response.setHeader("Content-Type", Constants.CONTENT_TYPE_JSON);
    }
    
     public String getFullURL(HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        return requestURL;
    }

    public String getRequestPayLoad(HttpServletRequest request) throws IOException {
        StringBuilder buffer = new StringBuilder();
        InputStream inputStream = request.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        return buffer.toString();
    }
}
