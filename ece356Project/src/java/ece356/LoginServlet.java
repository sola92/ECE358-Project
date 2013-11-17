package ece356;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Sola
 */
public class LoginServlet extends HttpServlet {

    final String LOGIN_JSP = "index.jsp";
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
            //ProjectDBAO.getSpecializations();
            Boolean userExists = ProjectDBAO.verifyUserExists(alias, password);
            if(userExists) {
                HttpSession session = request.getSession(true);
                User u = ProjectDBAO.getPatientByAlias(alias);
                session.setAttribute("userAlias", alias);
                session.setAttribute("userIsPatient", true);
                response.sendRedirect(PATIENT_HOME_JSP);
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
