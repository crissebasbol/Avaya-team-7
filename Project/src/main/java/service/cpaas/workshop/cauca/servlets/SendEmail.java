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
import service.cpaas.workshop.cauca.http.SendEmailAPI;
import service.cpaas.workshop.cauca.log.EscribirEnLog;

/**
 *
 * @author umansilla
 */
public class SendEmail extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        new EscribirEnLog().crearLog("SendEmail", request.getServletContext().getRealPath(""));
        String pathInfo = request.getPathInfo(); // /{value}/test
        String[] pathParts = pathInfo.split("/");
        String part1 = pathParts[1]; // TO
        String part2 = pathParts[2]; // TIPO DE EMAIL
        new EscribirEnLog().crearLog("Send email to: " + part2, request.getServletContext().getRealPath(""));
        if (part1 != null && part2 != null) {
            switch (part2) {
                case "A":
                    new SendEmailAPI().sendEmailRestLab197(part1, "Email Tipo A", "Email Tipo A");
                    break;
            }

        }
    }
}
