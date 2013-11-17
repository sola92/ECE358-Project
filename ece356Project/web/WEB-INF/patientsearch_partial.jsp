<%-- 
    Document   : patientsearch_partial
    Created on : Nov 17, 2013, 3:17:40 AM
    Author     : Sola
--%>

<%@page import="java.util.List"%>
<%@page import="ece356.model.Patient"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<ul style="list-style-type:None;padding:0px;" id="res">
    <%
        List<Patient> patients = (List<Patient>)request.getAttribute("patients");
        for(Patient p: patients) { %>
            <li>
                <button data-p-id="<%= p.getPatientID() %>" 
                        data-follower-id="<%= p.getPatientID() %>" 
                        class="btn btn-default"
                        onclick="FOLLOW(this);">Follow</button>
                <%= p.getFirstName() %>&nbsp<%= p.getLastName() %>
            </li>
        <% }
    %>
</ul>
