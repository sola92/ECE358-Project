<%-- 
    Document   : adminhome
    Created on : Nov 18, 2013, 2:04:48 AM
    Author     : Sola
--%>
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
          <div class="navbar-header">
            <a class="navbar-brand" href="adminhome.jsp">ECE356 Project</a>
          </div>
        </nav>
        <br></br><br></br>
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="list-group">
                  <a href="viewpatients.jsp" class="list-group-item">
                    View all patients
                  </a>
                  <a href="viewdoctors.jsp" class="list-group-item">
                    View all doctors
                  </a>
                  <a href="searchreviews.jsp" class="list-group-item">
                    Search Reviews
                  </a>                  
                </div>         
            </div>  
        </div> 
         <script src="/ece356Project/static/js/bootstrap.min.js"></script>            
    </body>
</html>
