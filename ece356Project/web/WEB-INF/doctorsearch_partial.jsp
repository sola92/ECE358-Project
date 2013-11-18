<%-- 
    Document   : doctorsearch_partial
    Created on : Nov 17, 2013, 11:23:29 PM
    Author     : Sola
--%>
<%@page import="java.util.List"%>
<%@page import="ece356.model.Doctor"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<ul style="list-style-type:None;padding:0px;" id="res">
    <%
        List<Doctor> doctors = (List<Doctor>)request.getAttribute("doctors");
        for(Doctor d: doctors) { %>
            <li>
                <a class="btn btn-default" href="fullReview.jsp?doctorID=<%=d.getDoctorID()%>" role="button">View Review</a>
                <a class="btn btn-default" href="rateDoctor.jsp?doctorID=<%=d.getDoctorID()%>" role="button">Rate this Doctor</a>
                <%= d.getFirstName() %>&nbsp<%= d.getLastName() %>
            </li>
        <% }
    %>
</ul>
