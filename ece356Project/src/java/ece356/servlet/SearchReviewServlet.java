
package ece356.servlet;


import ece356.model.ProjectDBAO;
import ece356.model.Review;
import ece356.model.User;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Sola
 */
public class SearchReviewServlet extends HttpServlet {

    final String LOGIN_JSP = "index.jsp";
    final String PARTIAL_REVIEW_SEARCH_JSP = "/WEB-INF/reviewsearch_partial.jsp";
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        if(session.getAttribute("user") == null) {
            response.sendRedirect(LOGIN_JSP);
            return;
        }        
        try {
            String query   = request.getParameter("query");
            String endDate = request.getParameter("endDate");
            String startDate = request.getParameter("startDate");
            List<Review> reviews = ProjectDBAO.searchReviews(query, startDate, endDate);
            request.setAttribute("reviews", reviews);
            request.getRequestDispatcher(PARTIAL_REVIEW_SEARCH_JSP).forward(request, response);
        } catch (Exception ex) {
            throw new ServletException(ex);
        }        
    }
}
