/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.cpaas.workshop.cauca.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import service.cpaas.workshop.cauca.util.Constants_Attributes;

/**
 *
 * @author umansilla
 */
public class SendEmailAPI {

    public void sendEmailRestLab197(String to, String subject, String body) throws IOException {
        final String URI = "http://" + Constants_Attributes.SEND_EMAIL_COLLABORATORY_FQDN + "/services/EventingConnector/events";
        final CloseableHttpClient client = HttpClients.createDefault();
        final HttpPost postMethod = new HttpPost(URI);
        postMethod.setHeader("Content-Type", "application/json");
        JSONObject payLoad = new JSONObject();
        payLoad.put("family", "AAADEVSendEmailRest");
        payLoad.put("type", "AAADEVSendEmailRestType");
        payLoad.put("version", "1.0");
        payLoad.put("eventBody", new JSONObject()
                .put("subjectString", subject)
                .put("bodyString", body)
                .put("toDestinationString", to)
        );
        //System.out.println(payLoad.toString(2));
        StringEntity entity = new StringEntity(payLoad.toString(), StandardCharsets.ISO_8859_1);
        postMethod.setEntity(entity);
        final CloseableHttpResponse response = client.execute(postMethod);
        final BufferedReader inputStream = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), StandardCharsets.ISO_8859_1));
        String line = "";
        final StringBuilder result = new StringBuilder();
        while ((line = inputStream.readLine()) != null) {
            result.append(line);
        }
        System.out.println(result.toString());
    }
}
