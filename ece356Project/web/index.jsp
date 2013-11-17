<%-- 
    Document   : index
    Created on : Nov 16, 2013, 4:18:40 PM
    Author     : vincent
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="/ece356Project/static/css/bootstrap.min.css" />
        <title>Login</title>
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
                <form role="form" action="LoginServlet" method="POST">
                  <div class="form-group">
                    <label for="alias">Username</label>
                    <input type="text" name="alias" class="form-control" id="alias" placeholder="Enter email">
                  </div>
                  <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" class="form-control" name="password" id="password" placeholder="Password">
                  </div>
                  <button type="submit" class="btn btn-primary">Submit</button>
                  <a class="btn btn-default" href="patientSignUp.jsp">Patient Sign Up</a>
                  <a class="btn btn-default" href="doctorsignup.jsp">Doctor Sign Up</a>
                </form>           
            </div>  
        </div> 
         <script src="/ece356Project/static/js/bootstrap.min.js"></script>            
    </body>
</html>
