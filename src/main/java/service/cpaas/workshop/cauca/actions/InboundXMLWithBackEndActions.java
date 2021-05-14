/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.cpaas.workshop.cauca.actions;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import service.cpaas.workshop.cauca.http.APICPaaSWorkShop;
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
                    response.getWriter().println("<Response>\n"
                            + "    <Redirect method=\"POST\">" + Constants_Attributes.IVR_WITH_BACK_END_NUMERO_DE_CUENTA + "</Redirect>\n"
                            + "</Response>\n"
                            + "");

                    break;
                case "3":
                    response.getWriter().println("<Response>\n"
                            + "    <Say voice=\"woman\" language=\"es\">Opcion 3 seleccionada</Say>\n"
                            + "</Response>\n"
                            + "");
                    break;
                default:
                    response.getWriter().println("<Response>\n"
                    + "    <Say voice=\"woman\" language=\"es\">Muchas gracias por usar nuestros servicios</Say>\n"
                    + "     <Hangup/>\n"
                    + "</Response>\n"
                    + "");
                    break;
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
        response.getWriter().println("<Response>\n"
                            + "    <Say voice=\"woman\" language=\"es\">Recibido, direccion "+speech+", enviaremos un vehículo, gracias por usar nuestros servicios</Say>\n"
                            + "     <Hangup/>\n"
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
