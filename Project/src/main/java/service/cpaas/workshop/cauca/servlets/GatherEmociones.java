/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.cpaas.workshop.cauca.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.cpaas.workshop.cauca.actions.GatherEmocionesActions;
import service.cpaas.workshop.cauca.log.EscribirEnLog;
import service.cpaas.workshop.cauca.models.CallConnectModel;
import service.cpaas.workshop.cauca.models.GatherStatusModel;
import service.cpaas.workshop.cauca.util.Constants_Attributes;
import service.cpaas.workshop.cauca.util.Funciones;

/**
 *
 * @author umansilla
 */
public class GatherEmociones extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            new Funciones().setAccessControlHeadersXML(response);
            if (request.getParameter("action") != null) {
                switch (request.getParameter("action")) {
                    case "inicio":
                        new EscribirEnLog().crearLog("Inicio", request.getServletContext().getRealPath(""));
                        CallConnectModel callConnectModel = new CallConnectModel(request);
                        new EscribirEnLog().crearLog(callConnectModel.toString(), request.getServletContext().getRealPath(""));
                        response.getWriter().println("<Response>\n"
                                + "    <Redirect method=\"POST\">" + Constants_Attributes.GATHER_EMOCIONES_INBOUND_XML + "</Redirect>\n"
                                + "</Response>\n"
                                + "");
                        break;
                    case "analize":
                        new EscribirEnLog().crearLog("Analize", request.getServletContext().getRealPath(""));
                        GatherStatusModel gatherStatusModel = new GatherStatusModel(request);
                        new EscribirEnLog().crearLog(gatherStatusModel.toString(), request.getServletContext().getRealPath(""));
                        byte[] bytes = gatherStatusModel.getSpeechResult().getBytes(StandardCharsets.ISO_8859_1);
                        String speechResultUtf8 = new String(bytes, StandardCharsets.UTF_8);
                        new GatherEmocionesActions(response).analizarSentimientos(speechResultUtf8, request.getServletContext().getRealPath(""));
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
            new EscribirEnLog().crearLog("Error: " + e.toString(), request.getServletContext().getRealPath(""));

        }
    }

}
