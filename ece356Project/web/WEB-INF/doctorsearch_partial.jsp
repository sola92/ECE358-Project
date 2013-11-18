<%-- 
    Document   : doctorsearch_partial
    Created on : Nov 17, 2013, 11:23:29 PM
    Author     : Sola
--%>
<%@page import="java.util.List"%>
<%@page import="ece356.model.Doctor"%>
<%@page import="ece356.model.Gender"%>
<%@page import="ece356.model.Address"%>
<%@page import="ece356.model.Specialization"%>
<%@page import="ece356.model.ProjectDBAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% List<Doctor> doctors = (List<Doctor>)request.getAttribute("doctors"); %>
<table class="table table-striped">
  <thead>
    <th>Doctor#</th>  
    <th>Full Name</th>
    <th>Average Rating</th>
    <th>Alias</th>
    <th>Gender</th> 
    <th>Specializations</th> 
    <th>Year of License</th> 
    <th>Work Addresses</th> 
    <th></th> 
  </thead>
  <tbody>
   <% for(Doctor d: doctors) { %>
        <tr>
            <td><%= d.getDoctorID() %></td>
            <td><%= d.getFullName() %></td>
            <td><%= d.getAverageRating() %></td>
            <td><%= d.getAlias() %></td>
            <td><%= d.getGender() == Gender.Male ? "Male" : "Female" %></td>   
            <td>
                <% for(Specialization s: ProjectDBAO.getSpecializationsForDoctor(d.getDoctorID())) { %>
                      <%= s.getName() %>
                      <br/>
                <% } %>     
            </td>  
            <td><%= d.getLicenseYear() %></td>
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
            <td>
               <a class="btn btn-sm btn-success" href="fullReview.jsp?doctorID=<%=d.getDoctorID()%>"  role="button">View Reviews</a>
               <a role="button" href="rateDoctor.jsp?doctorID=<%=d.getDoctorID()%>" class="btn btn-sm btn-info">Rate</a>               
            </td>             
        </tr>
    <% } %>                        
  </tbody>
</table>                       
