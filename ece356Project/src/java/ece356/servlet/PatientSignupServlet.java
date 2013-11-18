package ece356.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import ece356.model.ProjectDBAO;
import ece356.model.User;
import javax.servlet.http.HttpSession;
/**
 *
 * @author vincent
 */
@WebServlet(urlPatterns = {"/PatientSignup"})
public class PatientSignupServlet extends HttpServlet {
    final String SIGNUP_JSP       = "patientSignUp.jsp";
    final String PATIENT_HOME_JSP = "patienthome.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(SIGNUP_JSP);
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
            boolean hasError = false;
            if (firstName == null || firstName.isEmpty()) {
                request.setAttribute("errorWithFirstName", true);
                hasError = true;
            }
            if (lastName == null || lastName.isEmpty()) {
                request.setAttribute("errorWithLastName", true);
                hasError = true;
            } 
            if (alias == null || alias.isEmpty()) {
                request.setAttribute("errorWithAlias", true);
                hasError = true;
            } 
            if (email1 == null || email1.isEmpty()) {
                request.setAttribute("errorWithEmail", true);
                hasError = true;
            } 
            if (password1 == null || password1.isEmpty()) {
                request.setAttribute("errorWithPassword", true);
                hasError = true;
            } 
            if (!ProjectDBAO.aliasIsFree(alias)) {
                request.setAttribute("errorWithAlias", true);
                hasError = true;
            }
            if (hasError) {
                request.getRequestDispatcher(SIGNUP_JSP).forward(request, response);
            } else {
                ProjectDBAO.makePatient(firstName, lastName, alias, password1, email1);
                HttpSession session = request.getSession(true);
                User u = ProjectDBAO.getPatientByAlias(alias);
                session.setAttribute("user", u);
                session.setAttribute("userIsPatient", true);
                
                response.sendRedirect(PATIENT_HOME_JSP);
            }
        } catch(Exception e) {
            throw new ServletException(e);
        }
     }
}
