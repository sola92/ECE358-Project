<%-- 
    Document   : patientsearch_partial
    Created on : Nov 17, 2013, 3:17:40 AM
    Author     : Sola
--%>

<%@page import="java.util.List"%>
<%@page import="ece356.Patient"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<ul>
    <%
        List<Patient> patients = (List<Patient>)request.getAttribute("patients");
        for(Patient p: patients) { %>
            <li>
            	<%= p.getFirstName() %>
            </li>
        <% }
    %>
</ul>
