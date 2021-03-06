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
        <div class="row">
            <% 
            String firstNameError = "";
            if (request.getAttribute("errorWithFirstName") != null) {
              firstNameError = "has-error";
            }  
            String lastNameError = "";
            if (request.getAttribute("errorWithLastName") != null) {
              lastNameError = "has-error";
            }  
            String aliasError = "";
            if (request.getAttribute("errorWithAlias") != null) {
              aliasError = "has-error";
            } 
            String emailError = "";
            if (request.getAttribute("errorWithEmail") != null) {
              emailError = "has-error";
            }
            
            String passwordError = "";
            if (request.getAttribute("errorWithPassword") != null) {
              passwordError = "has-error";
            }
            %>
            <div class="col-md-6 col-md-offset-3">
                <h3 style="text-align:center;">Create Patient Account</h3>
                <br></br>              
                <form id="patientForm" class="form-horizontal" role="form" action="PatientSignupServlet" method="POST">
                  <div class="form-group  <%= firstNameError %>">
                    <label for="firstNameInput" class="col-sm-2 control-label">First Name</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" name="firstNameInput" id="firstNameInput" placeholder="First Name">
                    </div>
                  </div>
                  <div class="form-group  <%= lastNameError %>">
                    <label for="lastNameInput" class="col-sm-2 control-label">Last Name</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" name="lastNameInput" id="lastNameInput" placeholder="Last Name">
                    </div>
                  </div>
                  <div class="form-group  <%= aliasError %>">
                    <label for="aliasInput" class="col-sm-2 control-label">Create Alias</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" name="aliasInput" id="aliasInput" placeholder="Alias">
                    </div>
                  </div>
                  <div class="form-group  <%= emailError %>">
                    <label for="inputEmail3" class="col-sm-2 control-label">Enter Email</label>
                    <div class="col-sm-10">
                      <input type="email" class="form-control" name="inputEmail3" id="inputEmail3" placeholder="Email">
                    </div>
                  </div>
                  <div class="form-group  <%= passwordError %>">
                    <label for="inputPassword3" class="col-sm-2 control-label">Create Password</label>
                    <div class="col-sm-10">
                      <input type="password" class="form-control" name="inputPassword3" id="inputPassword3" placeholder="Password">
                    </div>
                  </div>
                  <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10 pull-right">
                      <button type="submit" class="btn btn-default btn-lg pull-right">Sign Up</button>
                    </div>
                  </div>
                </form>           
            </div>  
        </div>   
<!--        <script src="/ece356Project/static/js/jquery-1.10.2.min.js"></script> 
        <script type="text/javascript">
            $(document).ready(function() {
               $("#submitButton").submit(function() {
                   var hasError = 0;
                   $(".form-group").each(function(){
                       if($(this).find("input").val().trim() === "") {
                           $(this).class += "has-error";
                           hasError = 1;
                       }
                   });
                   if(hasError !== 0) {
                   $("#form").submit();
                   }
               });
               
            });
        </script>-->
    </body>
</html>
