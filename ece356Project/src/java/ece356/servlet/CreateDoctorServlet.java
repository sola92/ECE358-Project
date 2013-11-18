
package ece356.servlet;

import ece356.model.Gender;
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
    final String DOCTOR_SIGNUP_JSP = "doctorsignup.jsp";
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

        Integer gender            = Integer.parseInt(request.getParameter("gender"));
        Integer licenseYear       = Integer.parseInt(request.getParameter("licenseYear"));
        String[] strSpecializations = null;
        if (request.getParameterValues("specialization") != null) {
              strSpecializations = request.getParameterValues("specialization");
        }
        int[] specs  = new int[0];
        if(strSpecializations != null) {
           specs = new int[strSpecializations.length];
           for(int i = 0; i < strSpecializations.length; i++) {
               specs[i] = Integer.parseInt(strSpecializations[i]);
           }
        } 

        String alias     = request.getParameter("alias");
        String lastName  = request.getParameter("lastName");
        String password  = request.getParameter("password");
        String password2  = request.getParameter("extraPassword");
        String firstName = request.getParameter("firstName");

        Integer dobDay    = Integer.parseInt(request.getParameter("dobDay"));
        Integer dobYear   = Integer.parseInt(request.getParameter("dobYear"));                                                                                                                  
        Integer dobMonth  = Integer.parseInt(request.getParameter("dobMonth"));        
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
            if (password == null || password.isEmpty() || !password.equals(password2)) {
                request.setAttribute("errorWithPassword", true);
                hasError = true;
            } 
            if (!ProjectDBAO.aliasIsFree(alias)) {
                request.setAttribute("errorWithAlias", true);
                hasError = true;
            }
            if (homeCity          == null || homeCity.isEmpty()) {
                request.setAttribute("errorWithHomeAddress", true);
                hasError = true;
            }             
            if (homeProvince      == null || homeProvince.isEmpty()) {
                request.setAttribute("errorWithHomeAddress", true);
                hasError = true;
            } 
            if (homePostalCode    == null || homePostalCode.isEmpty()) {
                request.setAttribute("errorWithHomeAddress", true);
                hasError = true;
            } 
            if (homeStreetAddress == null || homeStreetAddress.isEmpty()) {
                request.setAttribute("errorWithHomeAddress", true);
                hasError = true;
            } 
            if (workCity          == null || workCity.isEmpty()) {
                request.setAttribute("errorWithWorkAddress", true);
                hasError = true;
            }
            if (workProvince      == null || workProvince.isEmpty()) {
                request.setAttribute("errorWithWorkAddress", true);
                hasError = true;
            }
            if (workPostalCode    == null || workPostalCode.isEmpty()) {
                request.setAttribute("errorWithWorkAddress", true);
                hasError = true;
            }
            if (workStreetAddress == null || workStreetAddress.isEmpty()) {
                request.setAttribute("errorWithWorkAddress", true);
                hasError = true;
            }
            if (!(gender == Gender.Male || gender == Gender.Female)) {
                request.setAttribute("errorWithGender", true);
                hasError = true;
            }
            if (licenseYear <= 0) {
                request.setAttribute("errorWithLicense", true);
                hasError = true;
            }
            if (strSpecializations == null) {
                request.setAttribute("errorWithSpecs", true);
                hasError = true;
            }
            if (dobDay   < 0) {
                request.setAttribute("errorWithDob", true);
                hasError = true;
            }
            if (dobYear  < 0) {
                request.setAttribute("errorWithDob", true);
                hasError = true;
            }
            if (dobMonth < 0) {
                request.setAttribute("errorWithDob", true);
                hasError = true;
            }
            if (hasError) {
                request.getRequestDispatcher(DOCTOR_SIGNUP_JSP).forward(request, response);
            } else {
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
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }
}
