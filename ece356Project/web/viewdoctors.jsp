<%-- 
    Document   : viewdoctors
    Created on : Nov 18, 2013, 3:04:04 AM
    Author     : Sola
--%>
<%@page import="ece356.model.Address"%>
<%@page import="ece356.model.Doctor"%>
<%@page import="ece356.model.Gender"%>
<%@page import="ece356.model.ProjectDBAO"%>
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
        <title>Doctor Records</title>
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
                    <h2>All Doctors</h2>   
                </div> 
                <br></br>
                <div class="row" >
                    <table class="table table-striped">
                      <thead>
                        <th>Doctor#</th>  
                        <th>Full Name</th>
                        <th>Alias</th>
                        <th>Gender</th> 
                        <th>Date Of Birth</th> 
                        <th>Home Address</th> 
                        <th>Work Addresses</th> 
                        <th>Year of License</th> 
                      </thead>
                      <tbody>
                       <% for(Doctor d: ProjectDBAO.getAllDoctors()) { %>
                            <tr>
                                <td><%= d.getDoctorID() %></td>
                                <td><%= d.getFullName() %></td>
                                <td><%= d.getAlias() %></td>
                                <td><%= d.getGender() == Gender.Male ? "Male" : "Female" %></td>
                                <td><%= d.getDOB() %></td>
                                <td>
                                  <p>
                                      <%= d.getHomeAddress().getStreetName() %>
                                      <br/>
                                      <%= d.getHomeAddress().getCity() %>,
                                      <%= d.getHomeAddress().getProvince() %>
                                      <%= d.getHomeAddress().getPostalCode() %>
                                    </p>                                    
                                </td>
                                <td>
                                    <% for(Address wa: ProjectDBAO.getWorkAddressByDoctorID(d.getDoctorID())) { %>
                                        <p>
                                            <%= wa.getStreetName() %>
                                            <br/>
                                            <%= wa.getCity() %>,
                                            <%= wa.getProvince() %>
                                            <%= wa.getPostalCode() %>
                                          </p> 
                                    <% } %>     
                                </td>                                
                                <td><%= d.getLicenseYear() %></td>
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