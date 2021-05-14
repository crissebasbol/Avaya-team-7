/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.cpaas.workshop.cauca.actions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import service.cpaas.workshop.cauca.http.APICPaaSWorkShop;
import service.cpaas.workshop.cauca.log.EscribirEnLog;
import service.cpaas.workshop.cauca.models.GatherStatusModel;
import service.cpaas.workshop.cauca.util.Constants_Attributes;

/**
 *
 * @author umansilla
 */
public class InboundXMLWithBackEndActions {

    private final HttpServletResponse response;

    public InboundXMLWithBackEndActions(HttpServletResponse response) {
        this.response = response;
    }

    public void getBienvenida() throws IOException {
        response.getWriter().println("<Response>\n"
                + "    <Pause length=\"2\"></Pause>\n"
                + "    <Say voice=\"woman\" language=\"es\">Hola bienvenido a Taxis R C P, Por favor seleccione una de las siguientes opciones.</Say>\n"
                + "    <Redirect method=\"POST\">" + Constants_Attributes.IVR_WITH_BACKEND_BIENVENIDA + "</Redirect>\n"
                + "</Response>\n"
                + "");
    }

    public void getMenuAction(GatherStatusModel gatherStatus) throws IOException {
        if (gatherStatus.getDigits().length() != 0) {
            switch (gatherStatus.getDigits()) {
                case "1":
                    response.getWriter().println("<Response>\n"
                            + "    <Redirect method=\"POST\">" + Constants_Attributes.NEIGH_ORIGIN + "</Redirect>\n"
                            + "</Response>\n"
                            + "");
                    break;
                case "2":
                    this.opcion2();
                    break;
                case "3":
                    this.opcion3(); 
                    break;
                default:
                    this.opcion4();
            }
        }
    }

    public void opcion1_1(String speechResultUtf8) throws IOException { 
        response.getWriter().println("<Response>\n"
                            + "    <Redirect method=\"POST\">" + Constants_Attributes.ADRESS_ORIGIN + "?spech1=" + speechResultUtf8 + "</Redirect>\n"
                            + "</Response>\n"
                            + "");
    }

    public void opcion1_2(String speech) throws IOException { 
        response.getWriter().println("<Response>\n"
                            + "    <Redirect method=\"POST\">" + Constants_Attributes.NEIGH_DEST + "?spech1=" + speech + "</Redirect>\n"
                            + "</Response>\n"
                            + "");
    }

    public void opcion1_3(String speech) throws IOException { 
        response.getWriter().println("<Response>\n"
                            + "    <Redirect method=\"POST\">" + Constants_Attributes.ADRESS_DEST + "?spech1=" + speech + "</Redirect>\n"
                            + "</Response>\n"
                            + "");
    }
    
    public void opcion1_4(String speech) throws IOException { 
        this.createOrder(speech);
    }

    private void createOrder(String speech) throws IOException {
        try {
            speech = speech.replace("+", " ");
            String[] parts = speech.split("_");
            final String URI = "http://api.taxisrcp.co/api/taxisrcp-core/auth/login";
            final CloseableHttpClient client = HttpClients.createDefault();
            final HttpPost postMethod = new HttpPost(URI);
            postMethod.setHeader("Content-Type", "application/json");

            //final String authString = "apikey" + ":" + Constants_Attributes.IBM_TRADUCTOR_API_KEY;
            //final String authEncBytes = DatatypeConverter.printBase64Binary(authString.getBytes());
            //postMethod.addHeader("Authorization", "Basic " + authEncBytes);
            JSONObject payLoad = new JSONObject();
            payLoad.put("phone", "3145324545");
            payLoad.put("password", "admin123");
            StringEntity entity = new StringEntity(payLoad.toString(), StandardCharsets.UTF_8);
            postMethod.setEntity(entity);
            final CloseableHttpResponse response = client.execute(postMethod);
            final BufferedReader inputStream = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8));
            String line = "";
            final StringBuilder result = new StringBuilder();
            while ((line = inputStream.readLine()) != null) {
                result.append(line);
            }
            JSONObject resposneJson = new JSONObject(result.toString());
            System.out.println("Su token es: ");
            String token = "Bearer " + new JSONObject(resposneJson.get("data").toString()).get("token").toString();
            System.out.println(token);

            final String URI_CREATE_ORDER = "http://api.taxisrcp.co/api/taxisrcp-core/orders";
            final CloseableHttpClient client_order = HttpClients.createDefault();
            final HttpPost postMethod_order = new HttpPost(URI_CREATE_ORDER);
            postMethod_order.setHeader("Content-Type", "application/json");

            postMethod_order.addHeader("Authorization", token);

            payLoad = new JSONObject();
            JSONObject whereTo = new JSONObject();
            JSONObject whereToLocation = new JSONObject();
            JSONObject whereFrom = new JSONObject();
            JSONObject whereFromLocation = new JSONObject();

            whereToLocation.put("lat", "1");
            whereToLocation.put("lng", "1");

            whereTo.put("neighborhood", parts[2]);
            whereTo.put("address", parts[3]);
            whereTo.put("location", whereToLocation);

            whereFromLocation.put("lat", "1");
            whereFromLocation.put("lng", "1");

            whereFrom.put("neighborhood", parts[0]);
            whereFrom.put("address", parts[1]);
            whereFrom.put("location", whereFromLocation);

            payLoad.put("whereTo", whereTo);
            payLoad.put("whereFrom", whereFrom);
            entity = new StringEntity(payLoad.toString(), StandardCharsets.UTF_8);
            postMethod_order.setEntity(entity);
            final CloseableHttpResponse responseOrder = client_order.execute(postMethod_order);
            final BufferedReader inputStreamOrder = new BufferedReader(new InputStreamReader(responseOrder.getEntity().getContent(), StandardCharsets.UTF_8));
            line = "";
            final StringBuilder resultOrder = new StringBuilder();
            while ((line = inputStreamOrder.readLine()) != null) {
                resultOrder.append(line);
            }

            JSONObject resposneJsonOrder = new JSONObject(resultOrder.toString());
            System.out.println("order:");
            System.out.println(resposneJsonOrder.toString());


        } catch (Exception e) {
            //TODO: handle exception
        }
        this.response.getWriter().println("<Response>\n"
                + "    <Pause length=\"2\"></Pause>\n"
                + "    <Say voice=\"woman\" language=\"es\">Tu orden ha sido creada exitosamente, espera nuestra confirmacion.</Say>\n"
                + "     <Hangup/>\n"
                + "</Response>\n"
                + "");
        
    }

    public void opcion2() throws IOException { 
        this.response.getWriter().println("<Response>\n"
            + "<Gather input=\"speech dtmf\" language=\"es-US\" action=\"https://api.zang.io/v2/Accounts/AC777c3e32105058d2149847999a01bff4/Agents/DF9fda65e4fcd440ce888a155dce982f7d/Xml/Hook\" method=\"POST\" finishOnKey=\"*\" timeout=\"10\" hints=\"dialogflow(projectid=newagent-irck;conversationprofileid=78x_iY-BQCmRMHDYGsYXEQ;method=StreamingAnalyzeContent)\"/>\n"
            + "</Response>");
    }

    public void opcion3() throws IOException { 
        this.response.getWriter().println("<Response>\n"
                + "    <Pause length=\"1\"></Pause>\n"
                + "    <Say voice=\"woman\" language=\"es\">Estamos redireccionando tu llamada con administracion, ten un buen día.</Say>\n"
                + "    <Dial>\n"
                + "      <Sip>9033175704054@186.28.237.130</Sip>\n"
                + "    </Dial>\n"
                + "</Response>\n"
                + "");
    }

    public void opcion4() throws IOException { 
        this.response.getWriter().println("<Response>\n"
                + "    <Pause length=\"1\"></Pause>\n"
                + "    <Say voice=\"woman\" language=\"es\">Estamos redireccionando tu llamada, ten un buen día.</Say>\n"
                + "    <Dial>\n"
                + "    <Conference startConferenceOnEnter=\"true\" record=\"true\" beep=\"true\" hangupOnStar=\"true\" maxParticipants=\"20\" recordCallbackUrl=\"http://demo7015781.mockable.io/saveConference\">SecurityConference</Conference>\n"
                + "    </Dial>\n"
                + "</Response>\n"
                + "");
    }

    public void getNumeroDeCuentaValidacion(GatherStatusModel gatherStatus) throws IOException {
        if (gatherStatus.getDigits().length() != 0) {
            if (gatherStatus.getDigits().length() == 6) {
                JSONObject json = new APICPaaSWorkShop().obtenerUsuarioPorNumeroDeCuenta(gatherStatus.getDigits().replaceAll(" ", "").trim(), Constants_Attributes.CUENTA_API);
                if (json.has("status") && json.has("message") && json.has("usuario")) {
                    String nombreUsuario = json.getJSONObject("usuario").getString("nombre");
                    String saldoActual = json.getJSONObject("usuario").getString("saldoactual");
                    String deudaActual = json.getJSONObject("usuario").getString("creditopromocion");
                    response.getWriter().println("<Response>\n"
                            + " <Say voice=\"female\" language=\"es-us\">Estimado " + nombreUsuario + " su saldo actual es de " + saldoActual + ". Actualmente tiene una deuda vigente de " + deudaActual + "</Say>\n"
                            + " <Pause length=\"1\"/> \n"
                            + "    <Redirect method=\"POST\">" + Constants_Attributes.IVR_WITH_BACKEND_BIENVENIDA + "</Redirect>\n"
                            + "</Response>\n"
                            + "");
                } else {
                    response.getWriter().println("<Response>\n"
                            + " <Say voice=\"female\" language=\"es-us\">El número de cuenta no exite</Say>\n"
                            + "    <Redirect method=\"POST\">" + Constants_Attributes.IVR_WITH_BACKEND_BIENVENIDA + "</Redirect>\n"
                            + "</Response>\n"
                            + "");
                }

            } else {
                response.getWriter().println("<Response>\n"
                        + " <Say voice=\"female\" language=\"es-us\">El número de cuenta no cumple con la longitud indicada.</Say>\n"
                        + "    <Redirect method=\"POST\">" + Constants_Attributes.IVR_WITH_BACKEND_BIENVENIDA + "</Redirect>\n"
                        + "</Response>\n"
                        + "");
            }
        }
    }
}
