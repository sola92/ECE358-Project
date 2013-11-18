/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ece356.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ece356.model.ProjectDBAO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Sola
 */
public class DeleteReviewServlet extends HttpServlet {
	final String LOGIN_JSP = "index.jsp";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        if(session.getAttribute("user") == null) {
            throw new ServletException();
        }              	
        int review = Integer.parseInt(request.getParameter("reviewID"));
        try {
            ProjectDBAO.deleteReview(review);
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }
}
