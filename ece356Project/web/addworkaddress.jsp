<%-- 
    Document   : doctorsignup
    Created on : Nov 18, 2013, 1:04:06 PM
    Author     : Vincent
--%>
<%@page import="java.util.List"%>
<%@page import="ece356.model.ProjectDBAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="static/css/bootstrap.min.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Doctor Sign up</title>
    </head>
    <body>
        <nav class="navbar navbar-default" role="navigation">
          <!-- Brand and toggle get grouped for better mobile display -->
          <div class="navbar-header">
            <a class="navbar-brand" href="doctorhome.jsp">ECE356 Project</a>
          </div>
        </nav>
        <div class="row">
            <% 
            String workAddressError = "";
            if (request.getAttribute("errorWithWorkAddress") != null) {
              workAddressError = "has-error";
            }           
            %>
            <div class="col-md-6 col-md-offset-3">
                <h3 style="text-align:center;">Create Doctor Account</h3>
                <br></br>
                <form class="form-horizontal" action="AddWorkAddressServlet" method="POST" role="form">
                  <h4>Work Address</h4>
                  <div class="form-group <%= workAddressError %>">
                    <label for="workStreetAddress" class="col-sm-2 control-label">Street Address</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" name="workStreetAddress" 
                            id="workStreetAddress" placeholder="Enter your street address">
                    </div>
                  </div>               
                  <div class="form-group <%= workAddressError %>">
                    <label for="workCity" class="col-sm-2 control-label">City</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" name="workCity" 
                            id="workCity" placeholder="Enter your city">
                    </div>
                  </div>   
                  <div class="form-group <%= workAddressError %>">
                    <label for="workPostalCode" class="col-sm-2 control-label">Postal Code</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" name="workPostalCode" 
                            id="workPostalCode" placeholder="Enter your postal code">
                    </div>                                       
                  </div>   
                  <div class="form-group <%= workAddressError %>">
                    <label for="workProvince" class="col-sm-2 control-label">Province</label>
                    <div class="col-sm-10">
                      <select class="form-control" name="workProvince" id="workProvince">
                          <option value="Ontario">Ontario</option>
                          <option value="Québec">Québec</option>
                          <option value="British Columbia">British Columbia</option>
                          <option value="Alberta">Alberta</option>
                          <option value="Nova Scotia">Nova Scotia</option>
                          <option value="New Brunswick">New Brunswick</option>
                          <option value="Manitoba">Manitoba</option>
                          <option value="Saskatchewan">Saskatchewan</option>
                          <option value="Prince Edward Island">Prince Edward Island</option>
                          <option value="Newfoundland and Labrador">Newfoundland and Labrador</option>
                      </select> 
                    </div>                                                    
                  </div>  
                                                                                                                    
                  <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                      <button type="submit" class="btn btn-lg btn-default">Add Address</button>
                    </div>
                  </div>                 
                </form>           
            </div>  
        </div>
        <script src="/ece356Project/static/js/jquery-1.10.2.min.js"></script> 
        <script src="static/js/bootstrap.min.js"></script>            
    </body>
</html>
