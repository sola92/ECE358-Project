<%-- 
    Document   : reviewsearch_partial
    Created on : Nov 18, 2013, 5:23:10 AM
    Author     : Sola
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="ece356.model.Review"%>
<% List<Review> reviews = (List<Review>)request.getAttribute("reviews"); %>
<table class="table table-striped">
  <thead>
    <th>Review#</th> 
    <th>Patient</th> 
    <th>Rating</th> 
    <th>Patient</th> 
    <th>Review Date</th> 
    <th>Note</th> 
    <th>Doctor</th> 
    <th></th> 
  </thead>
  <tbody>
   <% for(Review r: reviews) { %>
        <tr>
            <th><%= r.getReviewID() %></th> 
            <th><%= r.getPatient().getFullName() %></th> 
            <th><%= r.getRating() %></th> 
            <th><%= r.getDoctor().getFullName() %></th> 
            <th><%= r.getReviewDate() %></th> 
            <th><%= r.getNote() %></th> 
            <th>
                <button type="button" data-r-id="<%= r.getReviewID() %>" 
                        onclick="DELETE(this);" class="btn btn-sm btn-danger">Delete</button>
            </th>
        </tr>
    <% } %>                        
  </tbody>
</table> 
