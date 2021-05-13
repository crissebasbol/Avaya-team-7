/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.cpaas.workshop.cauca.servlets;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.cpaas.workshop.cauca.actions.NotificacionPorVencimientoDePagoActions;
import service.cpaas.workshop.cauca.log.EscribirEnLog;
import service.cpaas.workshop.cauca.models.CallConnectModel;
import service.cpaas.workshop.cauca.models.GatherStatusModel;
import service.cpaas.workshop.cauca.util.Funciones;
/**
 *
 * @author umansilla
 */
public class NotificacionPorVencimientoDePago extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        new Funciones().setAccessControlHeadersXML(response);
        try {
            if (request.getParameter("nivel") != null) {
                switch (request.getParameter("nivel")) {
                    case "inicio":
                        CallConnectModel callConnect = new CallConnectModel(request);
                        new EscribirEnLog().crearLog("INICIO", request.getServletContext().getRealPath(""));
                        new EscribirEnLog().crearLog(callConnect.toString(), request.getServletContext().getRealPath(""));
                        new NotificacionPorVencimientoDePagoActions(request, response).getInicioXML();
                        break;
                    case "respuesta":
                        GatherStatusModel gatherStatus = new GatherStatusModel(request);
                        new EscribirEnLog().crearLog("RESPUESTA", request.getServletContext().getRealPath(""));
                        new EscribirEnLog().crearLog(gatherStatus.toString(), request.getServletContext().getRealPath(""));
                        new NotificacionPorVencimientoDePagoActions(request, response).validarRespuesta(gatherStatus);
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            new EscribirEnLog().crearLog("Error: " + e.toString(), request.getServletContext().getRealPath(""));
            System.out.println("Error: " + e.toString());
        }
    }

}
