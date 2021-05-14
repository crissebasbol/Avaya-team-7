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
import service.cpaas.workshop.cauca.actions.InboundXMLWithBackEndActions;
import service.cpaas.workshop.cauca.log.EscribirEnLog;
import service.cpaas.workshop.cauca.models.CallConnectModel;
import service.cpaas.workshop.cauca.models.GatherStatusModel;
import service.cpaas.workshop.cauca.util.Funciones;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author umansilla
 */
public class InboundXMLWithBackEnd extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        new Funciones().setAccessControlHeadersXML(response);
        try {
            if (request.getParameter("nivel") != null) {
                if (request.getParameter("nivel").equals("bienvenida")) {
                    new EscribirEnLog().crearLog("BIENVENIDA", request.getServletContext().getRealPath(""));
                    CallConnectModel callConnect = new CallConnectModel(request);
                    new EscribirEnLog().crearLog(callConnect.toString(), request.getServletContext().getRealPath(""));
                    new InboundXMLWithBackEndActions(response).getBienvenida();
                } else if (request.getParameter("nivel").equals("menu")) {
                    new EscribirEnLog().crearLog("MENU", request.getServletContext().getRealPath(""));
                    GatherStatusModel gatherStatusMenu = new GatherStatusModel(request);
                    new EscribirEnLog().crearLog(gatherStatusMenu.toString(), request.getServletContext().getRealPath(""));
                    new InboundXMLWithBackEndActions(response).getMenuAction(gatherStatusMenu);
                } else if (request.getParameter("nivel").equals("opcion_1_1")) {
                    new EscribirEnLog().crearLog("opcion_1_1", request.getServletContext().getRealPath(""));
                    GatherStatusModel gatherStatusModel = new GatherStatusModel(request);
                    byte[] bytes = gatherStatusModel.getSpeechResult().getBytes(StandardCharsets.ISO_8859_1);
                    String speechResultUtf8 = new String(bytes, StandardCharsets.UTF_8);
                    new EscribirEnLog().crearLog(gatherStatusModel.toString(), request.getServletContext().getRealPath(""));
                    String speech = (speechResultUtf8).replace(" ", "+");
                    new EscribirEnLog().crearLog("Spech: "+speech, request.getServletContext().getRealPath(""));
                    new InboundXMLWithBackEndActions(response).opcion1_1(speech);
                } else if (request.getParameter("nivel").equals("opcion_1_2")) {
                    new EscribirEnLog().crearLog("opcion_1_2", request.getServletContext().getRealPath(""));
                    GatherStatusModel gatherStatusModel = new GatherStatusModel(request);
                    byte[] bytes = gatherStatusModel.getSpeechResult().getBytes(StandardCharsets.ISO_8859_1);
                    String speechResultUtf8 = new String(bytes, StandardCharsets.UTF_8);
                    new EscribirEnLog().crearLog("Spech1: "+request.getParameter("spech1"), request.getServletContext().getRealPath(""));
                    new EscribirEnLog().crearLog("Spech2: "+speechResultUtf8, request.getServletContext().getRealPath(""));
                    String speech = (request.getParameter("spech1")+"_"+speechResultUtf8).replace(" ", "+");
                    new EscribirEnLog().crearLog("Spech: "+speech, request.getServletContext().getRealPath(""));
                    new InboundXMLWithBackEndActions(response).opcion1_2(speech);
                } else if (request.getParameter("nivel").equals("opcion_1_3")) {
                    new EscribirEnLog().crearLog("opcion_1_3", request.getServletContext().getRealPath(""));
                    GatherStatusModel gatherStatusModel = new GatherStatusModel(request);
                    byte[] bytes = gatherStatusModel.getSpeechResult().getBytes(StandardCharsets.ISO_8859_1);
                    String speechResultUtf8 = new String(bytes, StandardCharsets.UTF_8);
                    new EscribirEnLog().crearLog("Spech1: "+request.getParameter("spech1"), request.getServletContext().getRealPath(""));
                    new EscribirEnLog().crearLog("Spech2: "+speechResultUtf8, request.getServletContext().getRealPath(""));
                    String speech = (request.getParameter("spech1")+"_"+speechResultUtf8).replace(" ", "+");
                    new EscribirEnLog().crearLog("Spech: "+speech, request.getServletContext().getRealPath(""));
                    new InboundXMLWithBackEndActions(response).opcion1_3(speech);
                } else if (request.getParameter("nivel").equals("opcion_1_4")) {
                    new EscribirEnLog().crearLog("opcion_1_4", request.getServletContext().getRealPath(""));
                    GatherStatusModel gatherStatusModel = new GatherStatusModel(request);
                    byte[] bytes = gatherStatusModel.getSpeechResult().getBytes(StandardCharsets.ISO_8859_1);
                    String speechResultUtf8 = new String(bytes, StandardCharsets.UTF_8);
                    new EscribirEnLog().crearLog("Spech1: "+request.getParameter("spech1"), request.getServletContext().getRealPath(""));
                    new EscribirEnLog().crearLog("Spech2: "+speechResultUtf8, request.getServletContext().getRealPath(""));
                    String speech = (request.getParameter("spech1")+"_"+speechResultUtf8).replace(" ", "+");
                    new EscribirEnLog().crearLog("Spech: "+speech, request.getServletContext().getRealPath(""));
                    new InboundXMLWithBackEndActions(response).opcion1_4(speech);
                } 
            }
        } catch (Exception e) {
            new EscribirEnLog().crearLog("Error: " + e.toString(), request.getServletContext().getRealPath(""));
            System.out.println("Error: " + e.toString());
        }
    }

}
