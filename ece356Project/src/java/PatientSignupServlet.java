

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

import ece356.project.model.ProjectDBAO;
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
        try {
            String firstName = request.getParameter("firstNameInput");
            String lastName = request.getParameter("lastNameInput");
            String alias = request.getParameter("aliasInput");
            String email1 = request.getParameter("inputEmail3");
            String email2 = request.getParameter("inputEmail4");
            String password1 = request.getParameter("inputPassword3");
            String password2 = request.getParameter("inputPassword4");
            
//            if(!email1.equals(email2)) {
//                //error
//            } else if(!password1.equals(password2)) {
//                //error
//            } else if(ProjectDBAO.queryAlias(alias) > 0) {
//                //error
//            } else {
                //create user and populate db
                ProjectDBAO.addPatient(firstName, lastName, alias, password1, email1);
                //go to another page
//            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PatientSignupServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PatientSignupServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
