<%-- 
    Document   : fullReview
    Created on : Nov 18, 2013, 2:01:42 AM
    Author     : vincent
--%>
<%@page import="java.util.List"%>
<%@page import="ece356.model.ProjectDBAO"%>
<%@page import="ece356.model.Doctor"%>
<%@page import="ece356.model.Review"%>
<%
    
  Object x = session.getAttribute("user");
  if( x == null || 
      session.getAttribute("userIsPatient") == null || 
      !(Boolean)session.getAttribute("userIsPatient")) {
      response.sendRedirect("index.jsp");
      return;
  }
  if (request.getParameter("doctorID") == null) {
      response.sendRedirect("doctorsearch.jsp");
      return;
  } 
  
  int doctorID = Integer.parseInt(request.getParameter("doctorID"));
  Doctor d = ProjectDBAO.getDoctorByID(doctorID);
  if(d == null) {
      response.sendRedirect("index.jsp");
      return;
  }
  List<Review> reviews = ProjectDBAO.getReviewsByDoctorID(d.getDoctorID());
  
//  List<Address> workAddressArray = ProjectDBAO.getWorkAddressByDoctorID(d.getDoctorID());
  
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="/ece356Project/static/css/bootstrap.min.css" />
        <title>Login</title>
    </head>
    <body>
        <nav class="navbar navbar-default" role="navigation">
          <!-- Brand and toggle get grouped for better mobile display -->
          <div class="navbar-header">
            <a class="navbar-brand" href="patienthome.jsp">ECE356 Project</a>
          </div>
        </nav>
        <br></br><br></br>
        <div class="row">
            <div class="col-md-8">
                <%if(reviews.size()>0) {%>
                <h3>Reviews for <%= d.getFullName() %> </h3>
                
                <table class="table table-striped">
                <thead>
                  <th>Reviewer</th>  
                  <th>Date</th>
                  <th>Rating</th>
                  <th>Comment</th>
                </thead>
                <tbody>
                 <% for(Review r: reviews) { %>
                      <tr>
                          <td><%= r.getPatient().getFullName() %></td>
                          <td><%= r.getReviewDate() %></td>
                          <td><%= r.getRating() %></td>
                          <td><%= r.getNote() %></td>               
                      </tr>
                  <% } %>                        
                </tbody>
              </table>  
                <%} else {%>
                    <h3>Sorry there are no reviews for this doctor</h3>
                <%}%>
            </div>             
        </div> 
         <script src="/ece356Project/static/js/bootstrap.min.js"></script>            
    </body>
</html>

