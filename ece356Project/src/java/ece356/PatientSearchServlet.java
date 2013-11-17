package ece356;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Sola
 */
public class PatientSearchServlet extends HttpServlet {

    final String PATIENT_SEARCH_JSP = "patientsearch.jsp";
    final String PARTIAL_PATIENT_SEARCH_JSP = "/WEB-INF/patientsearch_partial.jsp";
     

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(PATIENT_SEARCH_JSP);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String alias = request.getParameter("query");
            request.setAttribute("patients", ProjectDBAO.searchPatientsByAlias(alias));
            request.getRequestDispatcher(PARTIAL_PATIENT_SEARCH_JSP).forward(request, response);
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

}
