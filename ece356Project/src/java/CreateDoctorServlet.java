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

        String gender            = request.getParameter("gender");
        String licenseYear       = request.getParameter("licenseYear");
        String[] specializations = request.getParameterValues("specialization[]");

        
        String lastName  = request.getParameter("lastName");
        String password  = request.getParameter("password");
        String firstName = request.getParameter("firstName");
                                             
        String dobDay    = request.getParameter("dobDay");
        String dobYear   = request.getParameter("dobYear");                                                                                                                  
        String dobMonth  = request.getParameter("dobMonth");
    }
}
