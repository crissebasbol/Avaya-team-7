/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.cpaas.workshop.cauca.actions;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import service.cpaas.workshop.cauca.http.APICPaaSWorkShop;
import service.cpaas.workshop.cauca.log.EscribirEnLog;
import service.cpaas.workshop.cauca.models.GatherStatusModel;
import service.cpaas.workshop.cauca.util.Constants_Attributes;
/**
 *
 * @author umansilla
 */
public class NotificacionPorVencimientoDePagoActions {

    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public NotificacionPorVencimientoDePagoActions(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public void getInicioXML() throws IOException {
        String usuario = request.getParameter("usuario");
        String cuenta = request.getParameter("cuenta");
        String deuda = request.getParameter("deuda");
        response.getWriter().println("<Response>\n"
                + "    <Redirect method=\"POST\">" + Constants_Attributes.NVP_SALUDO_INICIAL
                + "?nivel=inicio&usuario=" + usuario + "&cuenta=" + cuenta + "&deuda=" + deuda + "</Redirect>\n"
                + "</Response>\n"
                + "");
    }

    public void validarRespuesta(GatherStatusModel gatherStatus) throws IOException {
        String usuario = request.getParameter("usuario");
        String cuenta = request.getParameter("cuenta");
        String deuda = request.getParameter("deuda");
        if (gatherStatus.getDigits().length() != 0) {
            //SE VALIDA EL DIGITO QUE SE HA INSERTADO POR PARTE DEL USUARIO
            switch (gatherStatus.getDigits()) {
                case "1":
                    //SI EL DIGITO INSERTADO HA SIDO "1"
                    //SE OBTIENE EL USUARIO CON EL NUMERO DE CUENTA QUE VIENE EN LA URL
                    JSONObject jsonObjectUsuario = new APICPaaSWorkShop()
                            .obtenerUsuarioPorNumeroDeCuenta(cuenta, Constants_Attributes.CUENTA_API);
                    //SE ESCRIBE EN EL LOG
                    new EscribirEnLog().crearLog(jsonObjectUsuario
                            .toString(2), request.getServletContext().getRealPath(""));
                    //VALIDAMOS QUE EFECTIVAMENTE EXISTA UN USUARIO CON ESE NUMERO DE CUENTA
                    if (jsonObjectUsuario.has("usuario")) {
                        //SE EDITA EL CAMPO PIN AUTORIZACION DE LOS DATOS DEL USUARIO A 1 
                        //ASI NO SE LE VOLVERA A LLAMAR EN EL SIGUIENTE CICLO DE LLAMADAS
                        JSONObject jsonResponse = new APICPaaSWorkShop()
                                .editarUsuarioPorNumeroDeCuenta(cuenta, jsonObjectUsuario.getJSONObject("usuario"), 
                                        Constants_Attributes.CUENTA_API, 
                                        Constants_Attributes.TOKEN_CUENTA_API);
                        //ESCRIBIMOS EN EL LOG EL RESULTADO DEL USUARIO EDITADO
                        new EscribirEnLog()
                                .crearLog(jsonResponse.toString(2), request.getServletContext().getRealPath(""));
                        //SI LA RESPUESTA DEL API EN LA EDICION SE REALIZO CORRECTAMENTE RESPONDEMOS A CPAAS
                        if (jsonResponse.has("status") && jsonResponse.getString("status").equals("ok")) {
                            //SE GENERA XML PARA REDIRECCIONAR AL XML HOSTEADO EN CPAAS
                            response.getWriter().println("<Response>\n"
                                    + "    <Redirect method=\"POST\">" + Constants_Attributes.NVP_DESPEDIDA + "</Redirect>\n"
                                    + "</Response>\n"
                                    + "");
                        }
                    }
                    break;
                default:
                    response.getWriter().println("<Response>\n"
                            + "    <Redirect method=\"POST\">" + Constants_Attributes.NVP_SALUDO_INICIAL
                            + "?nivel=inicio&usuario=" + usuario + "&cuenta=" + cuenta + "&deuda=" + deuda + "</Redirect>\n"
                            + "</Response>\n"
                            + "");
                    break;
            }
        } else {
            response.getWriter().println("<Response>\n"
                    + "    <Redirect method=\"POST\">" + Constants_Attributes.NVP_SALUDO_INICIAL
                    + "?nivel=inicio&usuario=" + usuario + "&cuenta=" + cuenta + "&deuda=" + deuda + "</Redirect>\n"
                    + "</Response>\n"
                    + "");
        }

    }
}
