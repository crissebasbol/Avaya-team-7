/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.cpaas.workshop.cauca.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.xml.bind.DatatypeConverter;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import service.cpaas.workshop.cauca.util.Constants_Attributes;

/**
 *
 * @author umansilla
 */
public class IBMAPI {

    public JSONObject traducirTextoDeEspaniolAIngles(String texto) throws IOException {
        final String URI = Constants_Attributes.IBM_TRADUCTOR_URL + "/v3/translate?version=2018-05-01";
        final CloseableHttpClient client = HttpClients.createDefault();
        final HttpPost postMethod = new HttpPost(URI);
        postMethod.setHeader("Content-Type", "application/json");

        final String authString = "apikey" + ":" + Constants_Attributes.IBM_TRADUCTOR_API_KEY;
        final String authEncBytes = DatatypeConverter.printBase64Binary(authString.getBytes());
        postMethod.addHeader("Authorization", "Basic " + authEncBytes);
        JSONObject payLoad = new JSONObject();
        payLoad.put("text", new JSONArray().put(texto));
        payLoad.put("model_id", "es-en");
        StringEntity entity = new StringEntity(payLoad.toString(), StandardCharsets.UTF_8);
        postMethod.setEntity(entity);
        final CloseableHttpResponse response = client.execute(postMethod);
        final BufferedReader inputStream = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8));
        String line = "";
        final StringBuilder result = new StringBuilder();
        while ((line = inputStream.readLine()) != null) {
            result.append(line);
        }
        return new JSONObject(result.toString());
    }

    public JSONObject obtenerSentimientos(String textoEN) throws IOException {
        final String URI = Constants_Attributes.IBM_TONE_ANALIZER_URL + "/v3/tone?version=2017-09-21&text=" + URLEncoder.encode(textoEN);
        final CloseableHttpClient client = HttpClients.createDefault();
        final HttpGet getMethod = new HttpGet(URI);
        final String authString = "apikey" + ":" + Constants_Attributes.IBM_TONE_ANALIZER_API_KEY;
        final String authEncBytes = DatatypeConverter.printBase64Binary(authString.getBytes());
        getMethod.addHeader("Authorization", "Basic " + authEncBytes);
        final CloseableHttpResponse response = client.execute(getMethod);
        final BufferedReader inputStream = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8));
        String line = "";
        final StringBuilder result = new StringBuilder();
        while ((line = inputStream.readLine()) != null) {
            result.append(line);
        }
        return new JSONObject(result.toString());
    }
}
