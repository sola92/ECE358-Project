package ece356.servlet;

import ece356.model.Patient;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ece356.model.ProjectDBAO;
import ece356.model.User;
import java.util.List;
import javax.servlet.http.HttpSession;
/**
 *
 * @author Sola
 */
public class PatientSearchServlet extends HttpServlet {

    final String PATIENT_SEARCH_JSP = "patientsearch.jsp";
    final String PARTIAL_PATIENT_SEARCH_JSP = "/WEB-INF/patientsearch_partial.jsp";
    final String LOGIN_JSP = "index.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(PATIENT_SEARCH_JSP);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        if(session.getAttribute("user") == null) {
            response.sendRedirect(LOGIN_JSP);
        }        
        try {
            String alias = request.getParameter("query");
            User currentUser = (User)session.getAttribute("user");
            List<Patient> patients = ProjectDBAO.searchPatientsByAlias(currentUser.getUserID(), alias);
            request.setAttribute("patients", patients);
            request.getRequestDispatcher(PARTIAL_PATIENT_SEARCH_JSP).forward(request, response);
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

}
