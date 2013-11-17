<%-- 
    Document   : index
    Created on : Nov 16, 2013, 4:18:40 PM
    Author     : vincent
--%>
<%
    if(session.getAttribute("userIsPatient") == null || !(Boolean)session.getAttribute("userIsPatient")) {
        response.sendRedirect("index.jsp");
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="/ece356Project/static/css/bootstrap.min.css" />
        <title>Home</title>
    </head>
    <body>
        <nav class="navbar navbar-default" role="navigation">
          <!-- Brand and toggle get grouped for better mobile display -->
          <div class="navbar-header">
            <a class="navbar-brand" href="#">ECE356 Project</a>
          </div>
        </nav>
        <br></br><br></br>
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="list-group">
                  <a href="patientsearch.jsp" class="list-group-item active">
                    Find Other Patients
                  </a>
                  <a href="#" class="list-group-item">Search For Doctors</a>
                </div>         
            </div>  
        </div> 
         <script src="/ece356Project/static/js/bootstrap.min.js"></script>            
    </body>
</html>
