/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ece356;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Sola
 */
public class FollowPatientServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String followerID = request.getParameter("followerID");
        String followeeID = request.getParameter("followeeID");
        try {
            ProjectDBAO.makeFriendship(Integer.parseInt(followerID), Integer.parseInt(followeeID));
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

}
