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
        <title>Patient Sign Up</title>
    </head>
    <body>
        <nav class="navbar navbar-default" role="navigation">
          <!-- Brand and toggle get grouped for better mobile display -->
          <div class="navbar-header">
            <a class="navbar-brand" href="#">ECE356 Project</a>
          </div>
        </nav>
        <br></br><br></br><br></br>
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <form class="form-horizontal" role="form" method="post" action="PatientSignUpServlet">
                  <div class="form-group">
                    <label for="firstNameInput" class="col-sm-2 control-label">First Name</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" id="firstNameInput" placeholder="First Name">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="lastNameInput" class="col-sm-2 control-label">Last Name</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" id="lastNameInput" placeholder="Last Name">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="aliasInput" class="col-sm-2 control-label">Create Alias</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" id="aliasInput" placeholder="Alias">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputEmail3" class="col-sm-2 control-label">Enter Email</label>
                    <div class="col-sm-10">
                      <input type="email" class="form-control" id="inputEmail3" placeholder="Email">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputEmail4" class="col-sm-2 control-label">Confirm Email</label>
                    <div class="col-sm-10">
                      <input type="email" class="form-control" id="inputEmail4" placeholder="Email">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputPassword3" class="col-sm-2 control-label">Create Password</label>
                    <div class="col-sm-10">
                      <input type="password" class="form-control" id="inputPassword3" placeholder="Password">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputPassword4" class="col-sm-2 control-label">Confirm Password</label>
                    <div class="col-sm-10">
                      <input type="password" class="form-control" id="inputPassword4" placeholder="Password">
                    </div>
                  </div>
                  <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                      <button type="submit" class="btn btn-default">Sign Up</button>
                    </div>
                  </div>
                </form>           
            </div>  
        </div>             
    </body>
</html>
