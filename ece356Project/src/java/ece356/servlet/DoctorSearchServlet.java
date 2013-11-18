/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ece356.servlet;

import ece356.model.Doctor;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ece356.model.User;
import ece356.model.ProjectDBAO;
import java.util.List;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Sola
 */
public class DoctorSearchServlet extends HttpServlet {

    final String LOGIN_JSP = "index.jsp";
    final String PARTIAL_DOCTOR_SEARCH_JSP = "/WEB-INF/doctorsearch_partial.jsp";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        int currentUserID = -1;
        if( session.getAttribute("user") == null || 
            session.getAttribute("userIsPatient") == null || 
            !(Boolean)session.getAttribute("userIsPatient")
        ) {
            response.sendRedirect(LOGIN_JSP);
            return;
        } else {
            currentUserID = ((User)session.getAttribute("user")).getUserID();
        }


        String city          = request.getParameter("city");
        String province      = request.getParameter("province");
        String postalCode    = request.getParameter("postalCode"); 
        String streetAddress = request.getParameter("streetAddress");

        String firstName  = request.getParameter("firstName");
        String lastName   = request.getParameter("lastName");


        Integer gender   = null;
        String strGender = request.getParameter("gender");
        if(strGender != null && !strGender.isEmpty()) {
           gender = Integer.parseInt(strGender); 
        } 
        
        Integer licenseYearEnd   = null;
        String strLicenseYearEnd = request.getParameter("licenseYearEnd");
        if(strLicenseYearEnd != null && strLicenseYearEnd.trim() != "") {
           licenseYearEnd = Integer.parseInt(strLicenseYearEnd); 
        }  

        Integer licenseYearStart = null;
        String strLicenseYearStart = request.getParameter("licenseYearStart");
        if(strLicenseYearStart != null && strLicenseYearStart.trim() != "") {
           licenseYearStart = Integer.parseInt(strLicenseYearStart); 
        }  

        Double averageRatingEnd   = null;
        String strAverageRatingEnd = request.getParameter("averageRatingEnd");
        if(strAverageRatingEnd != null && strAverageRatingEnd.trim() != "") {
           averageRatingEnd = Double.parseDouble(strAverageRatingEnd); 
        }  

        Double averageRatingStart = null;
        String strAverageRatingStart = request.getParameter("averageRatingStart");
        if(strAverageRatingStart != null && strAverageRatingStart.trim() != "") {
           averageRatingStart = Double.parseDouble(strAverageRatingStart); 
        }  

        Boolean recommendedByFriend = null;
        String strRecommendedByFriend = request.getParameter("recommendedByFriend");
        if(strRecommendedByFriend != null && strRecommendedByFriend.trim() != "") {
           recommendedByFriend = Boolean.parseBoolean(strRecommendedByFriend);
        }  

        Integer specialization = null;
        String strSpecialization = request.getParameter("specialization");
        if(strSpecialization != null && strSpecialization.trim() != "") {
           specialization = Integer.parseInt(strSpecialization); 
        }          

        try {
            List<Doctor> doctors = ProjectDBAO.searchDoctors(
                currentUserID,
                firstName, lastName,
                streetAddress, postalCode, 
                city, province, 
                licenseYearStart, licenseYearEnd,
                averageRatingStart, averageRatingEnd,
                recommendedByFriend,
                gender,
                specialization
            );
            request.setAttribute("doctors", doctors);
            request.getRequestDispatcher(PARTIAL_DOCTOR_SEARCH_JSP).forward(request, response);            
        } catch(Exception e) {
            throw new ServletException(e);
        }
    }
}
