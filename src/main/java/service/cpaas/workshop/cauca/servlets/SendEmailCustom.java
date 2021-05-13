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
import org.json.JSONObject;
import service.cpaas.workshop.cauca.http.SendEmailAPI;
import service.cpaas.workshop.cauca.log.EscribirEnLog;
import service.cpaas.workshop.cauca.util.ConstantsHttpResponse;
import service.cpaas.workshop.cauca.util.Funciones;

/**
 *
 * @author umansilla
 */
public class SendEmailCustom extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        new Funciones().setAccessControlHeadersJSON(response);
        new EscribirEnLog().crearLog("SendEmailCustom", request.getServletContext().getRealPath(""));
        JSONObject jsonPayLoad = new JSONObject(new Funciones().getRequestPayLoad(request));
        if (jsonPayLoad.has("to") && jsonPayLoad.has("subject") && jsonPayLoad.has("body")) {
            new EscribirEnLog().crearLog("To: " + jsonPayLoad.getString("to") + " Subject: " + jsonPayLoad.getString("subject") + " Body: " + jsonPayLoad.getString("body"), request.getServletContext().getRealPath(""));
            new SendEmailAPI().sendEmailRestLab197(jsonPayLoad.getString("to"), jsonPayLoad.getString("subject"), jsonPayLoad.getString("body"));
        } else {
            response.getWriter().println(ConstantsHttpResponse.RESPONSE_400("BadRequest"));
        }
    }

}
