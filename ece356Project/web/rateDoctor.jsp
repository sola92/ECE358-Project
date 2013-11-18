<%-- 
    Document   : rateDoctor
    Created on : Nov 18, 2013, 2:46:50 AM
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
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <link rel="stylesheet" href="/ece356Project/static/css/bootstrap.min.css" />
        <title>Rate Doctor</title>
    </head>
    <body>
        <nav class="navbar navbar-default" role="navigation">
          <!-- Brand and toggle get grouped for better mobile display -->
          <div class="navbar-header">
            <a class="navbar-brand" href="#">ECE356 Project</a>
          </div>
        </nav>
        <div class="row">
            <div class="col-md-6 col-md-offset-3">
                <h3 style="text-align:center;">Rate Doctor <%=d.getFullName()%></h3>
                <br></br>              
                <form class="form-horizontal" role="form" action="RateDoctorServlet" method="POST">
                  <input type="hidden" name="doctorID" value="<%= d.getDoctorID() %>" />
                  <div class="form-group">
                    <label for="note" class="col-sm-2 control-label">Comment</label>
                    <div class="col-sm-10">
                        <textarea name="note" cols="50" rows="5">
                        </textarea><br>
                    </div>
                  </div>
                  <div class="form-group">
                      <label for="rating" class="col-sm-2 control-label">Rating</label>
                    <div class="col-sm-10">
                      <div class="row">
                        <div class="col-xs-3">                      
                          <select name="rating" class="form-control">
                            <% for (double i = 0; i <= 5; i+=0.5) { %>
                              <option value="<%= i %>"><%= i %></option>
                            <% } %> 
                          </select>     
                        </div>          
                      </div>               
                    </div>
                  </div> 
                  <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10 pull-right">
                      <button type="submit" class="btn btn-default btn-lg pull-right">Submit Review</button>
                    </div>
                  </div>
                </form>           
            </div>  
        </div>             
    </body>
</html>
