
package ece356.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ece356.model.ProjectDBAO;
import ece356.model.User;
import java.sql.Date;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Sola
 */
@WebServlet(name = "CreateDoctorServlet", urlPatterns = {"/CreateDoctorServlet"})
public class CreateDoctorServlet extends HttpServlet {
    final String DOCTOR_HOME_JSP = "doctorhome.jsp";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("doctorsignup.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String homeCity          = request.getParameter("homeCity");
        String homeProvince      = request.getParameter("homeProvince");        
        String homePostalCode    = request.getParameter("homePostalCode");                                 
        String homeStreetAddress = request.getParameter("homeStreetAddress");


        String workCity          = request.getParameter("workCity");
        String workProvince      = request.getParameter("workProvince");                          
        String workPostalCode    = request.getParameter("workPostalCode");
        String workStreetAddress = request.getParameter("workStreetAddress");


        String[] strSpecializations  = request.getParameterValues("specialization");
        int[] specs  = new int[0];
        if(strSpecializations != null) {
           specs = new int[strSpecializations.length];
           for(int i = 0; i < strSpecializations.length; i++) {
               specs[i] = Integer.parseInt(strSpecializations[i]);
           }
        } 
        Integer gender            = Integer.parseInt(request.getParameter("gender"));
        Integer licenseYear       = Integer.parseInt(request.getParameter("licenseYear"));
        
        String alias     = request.getParameter("alias");
        String lastName  = request.getParameter("lastName");
        String password  = request.getParameter("password");
        String firstName = request.getParameter("firstName");

        Integer dobDay    = Integer.parseInt(request.getParameter("dobDay"));
        Integer dobYear   = Integer.parseInt(request.getParameter("dobYear"));                                                                                                                  
        Integer dobMonth  = Integer.parseInt(request.getParameter("dobMonth"));        
        try {
            int homeAddressID = ProjectDBAO.makeAddress(homeStreetAddress, homePostalCode, homeCity, homeProvince); 
            
            Date dob = Date.valueOf(dobYear + "-" + dobMonth + "-" + dobDay);
            int doctorID = ProjectDBAO.makeDoctor( firstName, lastName, alias, password, gender, 
                                    dob, homeAddressID, licenseYear, specs );
            int workAddressID = ProjectDBAO.makeAddress(workStreetAddress, workPostalCode, workCity, workProvince);
            int[] workAddressArray = {workAddressID};
            ProjectDBAO.addWorkAddresses(doctorID, workAddressArray);
            
            HttpSession session = request.getSession(true);
            User u = ProjectDBAO.getDoctorByAlias(alias);
            session.setAttribute("user", u);
            session.setAttribute("userIsDoctor", true);
            response.sendRedirect(DOCTOR_HOME_JSP);
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }
}
