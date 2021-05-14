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
                switch (request.getParameter("nivel")) {
                    case "bienvenida":
                        new EscribirEnLog().crearLog("BIENVENIDA", request.getServletContext().getRealPath(""));
                        CallConnectModel callConnect = new CallConnectModel(request);
                        new EscribirEnLog().crearLog(callConnect.toString(), request.getServletContext().getRealPath(""));
                        new InboundXMLWithBackEndActions(response).getBienvenida();
                        break;
                    case "menu":
                        new EscribirEnLog().crearLog("MENU", request.getServletContext().getRealPath(""));
                        GatherStatusModel gatherStatusMenu = new GatherStatusModel(request);
                        new EscribirEnLog().crearLog(gatherStatusMenu.toString(), request.getServletContext().getRealPath(""));
                        new InboundXMLWithBackEndActions(response).getMenuAction(gatherStatusMenu);
                        break;
                    case "opcion_1_1":
                        new EscribirEnLog().crearLog("opcion_1_1", request.getServletContext().getRealPath(""));
                        GatherStatusModel gatherStatusModel = new GatherStatusModel(request);
                        new EscribirEnLog().crearLog(gatherStatusModel.toString(), request.getServletContext().getRealPath(""));
                        byte[] bytes = gatherStatusModel.getSpeechResult().getBytes(StandardCharsets.ISO_8859_1);
                        String speechResultUtf8 = new String(bytes, StandardCharsets.UTF_8);
                        new EscribirEnLog().crearLog("Spech: "+speechResultUtf8, request.getServletContext().getRealPath(""));
                        new InboundXMLWithBackEndActions(response).opcion1_1(speechResultUtf8);
                        break;
                    case "numerodecuenta":
                        GatherStatusModel gatherStatusNumeroDeCuenta = new GatherStatusModel(request);
                        new EscribirEnLog().crearLog("NUMERO DE CUENTA", request.getServletContext().getRealPath(""));
                        new EscribirEnLog().crearLog(gatherStatusNumeroDeCuenta.toString(), request.getServletContext().getRealPath(""));
                        new InboundXMLWithBackEndActions(response).getNumeroDeCuentaValidacion(gatherStatusNumeroDeCuenta);
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
