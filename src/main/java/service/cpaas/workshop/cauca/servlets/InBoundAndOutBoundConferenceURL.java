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
import service.cpaas.workshop.cauca.log.EscribirEnLog;
import service.cpaas.workshop.cauca.models.CallConnectModel;
import service.cpaas.workshop.cauca.models.ConferenceStatusModel;
import service.cpaas.workshop.cauca.util.Constants_Attributes;
import service.cpaas.workshop.cauca.util.Funciones;

/**
 *
 * @author umansilla
 */
public class InBoundAndOutBoundConferenceURL extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        new Funciones().setAccessControlHeadersXML(response);
        try {
            if (request.getParameter("action") != null) {
                switch (request.getParameter("action")) {
                    case "callstatus":
                        CallConnectModel callConnect = new CallConnectModel(request);
                        new EscribirEnLog().crearLog(callConnect.toString(), request.getServletContext().getRealPath(""));
                        String result = "<Response>\n"
                                + "    <Redirect method=\"POST\">"+Constants_Attributes.CONFERENCE_INBOUND_XML_URL+"</Redirect>\n"
                                + "  <Say>You will never hear this.</Say>\n"
                                + "</Response>";

                        response.getWriter().println(result);
                        break;
                    case "conferencestatus":
                        ConferenceStatusModel conferenceStatus = new ConferenceStatusModel(request);
                        new EscribirEnLog().crearLog(conferenceStatus.toString(), request.getServletContext().getRealPath(""));
                        break;
                }
            }
        } catch (IOException e) {
            new EscribirEnLog().crearLog("Error: " + e.toString(), request.getServletContext().getRealPath(""));
            System.out.println("Error: " + e.toString());
        }
    }

}
