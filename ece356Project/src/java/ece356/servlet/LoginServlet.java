package ece356.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ece356.model.ProjectDBAO;
import ece356.model.User;
/**
 *
 * @author Sola
 */
public class LoginServlet extends HttpServlet {

    final String LOGIN_JSP        = "index.jsp";
    final String ADMIN_HOME_JSP   = "adminhome.jsp";
    final String DOCTOR_HOME_JSP  = "doctorhome.jsp";
    final String PATIENT_HOME_JSP = "patienthome.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(LOGIN_JSP);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String alias      = request.getParameter("alias");
        String password   = request.getParameter("password");  
        try {
            if(ProjectDBAO.verifyUserExists(alias, password)) {
                HttpSession session = request.getSession(true);
                if(ProjectDBAO.userIsPatient(alias)) {
                    User u = ProjectDBAO.getPatientByAlias(alias);
                    session.setAttribute("user", u);
                    session.setAttribute("userIsPatient", true);
                    response.sendRedirect(PATIENT_HOME_JSP);
                } else if(ProjectDBAO.userIsDoctor(alias)) {
                    User u = ProjectDBAO.getDoctorByAlias(alias);
                    session.setAttribute("user", u);
                    session.setAttribute("userIsDoctor", true);
                    response.sendRedirect(DOCTOR_HOME_JSP);
                } else {    
                    User u = ProjectDBAO.getAdminByAlias(alias);
                    session.setAttribute("user", u);
                    session.setAttribute("userIsAdmin", true);  
                    response.sendRedirect(ADMIN_HOME_JSP);
                }
            } else {
                request.setAttribute("errorWithLogin", true);
                request.setAttribute("alias", alias);
                request.setAttribute("password", password);
                request.getRequestDispatcher(LOGIN_JSP).forward(request, response);
            }
        } catch(Exception e) {
            throw new ServletException(e);
        }        
    }  
}
