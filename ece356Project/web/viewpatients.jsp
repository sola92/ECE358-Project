<%-- 
    Document   : viewpatients
    Created on : Nov 18, 2013, 2:25:03 AM
    Author     : Sola
--%>
<%@page import="ece356.model.ProjectDBAO"%>
<%@page import="ece356.model.Patient"%>
<%
    Object x = session.getAttribute("user");
    if(x == null || session.getAttribute("userIsAdmin") == null || !(Boolean)session.getAttribute("userIsAdmin")) {
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
        <title>Patient Records</title>
    </head>
    <body>
        <nav class="navbar navbar-default" role="navigation">
          <div class="navbar-header">
            <a class="navbar-brand" href="adminhome.jsp">ECE356 Project</a>
          </div>
        </nav>

        <div class="row">
            <div class="col-md-8 col-md-offset-2">
                <div class="row">  
                    <h2>Patients</h2>   
                </div> 
                <br></br>
                <div class="row" >
                    <table class="table table-striped">
                      <thead>
                        <th>Patient#</th>
                        <th>Full Name</th>
                        <th>Alias</th>
                        <th>Email Address</th>
                      </thead>
                      <tbody>
                       <% for(Patient p: ProjectDBAO.getAllPatients()) { %>
                            <tr>
                                <td><%= p.getPatientID() %></td>
                                <td><%= p.getFullName() %></td>
                                <td><%= p.getAlias() %></td>
                                <td><%= p.getEmail() %></td>
                            </tr>
                        <% } %>                        
                      </tbody>
                    </table>                       
                </div>                 
            </div>                     
        </div>    
        <script src="/ece356Project/static/js/jquery-1.10.2.min.js"></script> 
        <script src="/ece356Project/static/js/bootstrap.min.js"></script>            
    </body>
</html>