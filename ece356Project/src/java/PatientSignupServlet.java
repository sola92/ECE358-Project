

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author vincent
 */
@WebServlet(urlPatterns = {"/PatientSignup"})
public class PatientSignupServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("patientSignUp.jsp");
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String firstName = request.getParameter("firstNameInput");
        String lastName  = request.getParameter("lastNameInput");
        String alias     = request.getParameter("aliasInput");
        String email1    = request.getParameter("inputEmail3");
        String password1 = request.getParameter("inputPassword3");
        try {
            ProjectDBAO.makePatient(firstName, lastName, alias, password1, email1);
        } catch(Exception e) {
            throw new ServletException(e);
        }
     }
}
