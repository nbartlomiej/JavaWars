/*
 * JavaWars - browser game that teaches Java
 * Copyright (C) 2008-2009  Bartlomiej N (nbartlomiej@gmail.com)
 * 
 * This file is part of JavaWars. JavaWars is free software: you can 
 * redistribute it and/or modify it under the terms of the GNU General 
 * Public License as published by the Free Software Foundation, either 
 * version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 * 
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javawars.server.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javawars.client.data.SessionConstants;
import javawars.client.services.ServiceProvider;
import javawars.server.dao.UserDAO;
import javawars.domain.User;
import javawars.domain.exceptions.Enum.Parameter;
import javawars.domain.exceptions.IncorrectParameterException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.Resource;
import org.springframework.web.context.support.ServletContextResource;

/**
 *
 * @author bartlomiej
 */
public class Register extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // resource and factory necessary to access the rpc servlet's dispatcher beans
        Resource resource = new ServletContextResource(getServletContext(), "/WEB-INF/rpc-servlet.xml");
        BeanFactory factory = new XmlBeanFactory(resource);

        // getting access to services
        ServiceProvider serviceProvider = (ServiceProvider) factory.getBean("serviceProvider");


        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String repeatedPassword = request.getParameter("repeatedPassword");
        String studentID = request.getParameter("studentID");
        try {
            if (password.equals(repeatedPassword)==false) throw new IncorrectParameterException(Parameter.PASSWORD, javawars.domain.exceptions.Enum.Error.MALFORMED);
            
            Long userID = serviceProvider.newUser(login, password, "", studentID, request.getRemoteAddr(), false);
            
            // Getting the full user data from the database
            UserDAO userDAO = (UserDAO) factory.getBean("userDAO");
            User currentUser = userDAO.getUser(userID);
            
            // Putting 'em into the SessionConstants object...
            SessionConstants sessionConstants = new SessionConstants(currentUser);
            
            // When the SessionConstants object is put into the user's session
            // the rest of the application knows that it is dealing with a logged-in
            // user; 
            request.getSession(true).setAttribute("sessionConstants", sessionConstants);
            
            // index.jsp will recognise that the user is logged in and will redirect 
            // him further, to the proper application
            response.sendRedirect("");
            
            /*
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            
            // printing information about an error
            out.println("<html><body>");
            out.println("Twoje konto zostało utworzone - poczekaj na aktywację konta przez administratora");
            out.println("</body></html>");
            out.close(); */
            
        } catch (IncorrectParameterException ex) {
            
            // setting up all the variables necessary for 
            // printing the output
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            
            // printing information about an error
            out.println("<html><body>");
            out.println("error: " + ex.getParameter() + ", " + ex.getError() + ".");
            out.println("</body></html>");
            out.close();

        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
