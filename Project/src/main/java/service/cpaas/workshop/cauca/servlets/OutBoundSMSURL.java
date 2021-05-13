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
import service.cpaas.workshop.cauca.models.SmsStatusCallBack;
import service.cpaas.workshop.cauca.util.Funciones;

/**
 *
 * @author umansilla
 */
public class OutBoundSMSURL extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        new Funciones().setAccessControlHeadersXML(response);
        try {
            SmsStatusCallBack smsStatusCallBack = new SmsStatusCallBack(request);
            new EscribirEnLog().crearLog(smsStatusCallBack.toString(), request.getServletContext().getRealPath(""));
            response.getWriter().println();

        } catch (IOException e) {
            new EscribirEnLog().crearLog("Error: " + e.toString(), request.getServletContext().getRealPath(""));
            System.out.println("Error: " + e.toString());
        }
    }

}
