/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Sola
 */
@WebServlet(name = "CreateDoctorServlet", urlPatterns = {"/CreateDoctorServlet"})
public class CreateDoctorServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CreateDoctorServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateDoctorServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String homeCity       = request.getParameter("homeCity");
        String homeProvince   = request.getParameter("homeProvince");        
        String homePostalCode = request.getParameter("homePostalCode");                                 
        String homeStreetAddress = request.getParameter("homeStreetAddress");

        
        String workCity       = request.getParameter("workCity");
        String workProvince   = request.getParameter("workProvince");                          
        String workPostalCode = request.getParameter("workPostalCode");
        String workStreetAddress = request.getParameter("workStreetAddress");

        String gender      = request.getParameter("gender");
        String licenseYear = request.getParameter("licenseYear");
        String[] specializations = request.getParameterValues("specialization[]");

        
        String lastName  = request.getParameter("lastName");
        String password  = request.getParameter("password");
        String firstName = request.getParameter("firstName");
                                             
        String dobDay   = request.getParameter("dobDay");
        String dobYear  = request.getParameter("dobYear");                                                                                                                  
        String dobMonth = request.getParameter("dobMonth");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
