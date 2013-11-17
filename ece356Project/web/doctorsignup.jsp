<%-- 
    Document   : doctorsignup
    Created on : Nov 16, 2013, 5:58:06 PM
    Author     : Sola
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="/ece356Project/static/css/bootstrap.min.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Doctor Sign up</title>
    </head>
    <body>
        <nav class="navbar navbar-default" role="navigation">
          <!-- Brand and toggle get grouped for better mobile display -->
          <div class="navbar-header">
            <a class="navbar-brand" href="#">ECE356 Project</a>
          </div>
        </nav>
        <div class="row">
            <div class="col-md-6 col-md-offset-3">
                <h3 style="text-align:center;">Create Doctor Account</h3>
                <br></br>
                <form class="form-horizontal" role="form">
                  <div class="form-group">
                    <label for="firstName" class="col-sm-2 control-label">First Name</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" name="firstName" 
                            id="firstName" placeholder="Enter your first name">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="lastName" class="col-sm-2 control-label">Last Name</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" name="lastName" 
                            id="lastName" placeholder="Enter your last name">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="password" class="col-sm-2 control-label">Password</label>
                    <div class="col-sm-10">
                      <input type="password" class="form-control" name="password" 
                            id="password" placeholder="Password">
                    </div>
                  </div>         
                  <div class="form-group">
                    <label for="extraPassword" class="col-sm-2 control-label">Re-Enter Password</label>
                    <div class="col-sm-10">
                      <input type="password" class="form-control" name="extraPassword" 
                            id="extraPassword" placeholder="Re-enter Password">
                    </div>
                  </div>                           
                  <div class="form-group">
                    <label for="Specialization" class="col-sm-2 control-label">Date of Birth</label>
                    <div class="col-sm-10">
                      <div class="row">

                        <div class="col-xs-3">
                          <select name="dobDay" class="form-control">
                            <option value="-1">Day</option>
                            <% for (int i = 1; i <= 31; i++) { %>
                               <option value="<%= i %>"><%= i %></option>
                            <% } %> 
                          </select>                           
                        </div>

                        <div class="col-xs-3">
                          <select name="dobMonth" class="form-control">
                            <option value="-1">Month</option>
                            <% String[] months = new String[] {
                                  "January", "February", "March", "April", "May",
                                  "June", "July", "August", "September", "October", "November",
                                  "December"
                            };
                            for (int i = 0; i < months.length; i++) { %>
                               <option value="<%= i %>"><%= months[i] %></option>
                            <% } %>                            
                          </select>                           
                        </div>                        

                        <div class="col-xs-3">
                          <select name="dobYear" class="form-control">
                            <option value="-1">Year</option>
                            <% for (int i = 2010; i >= 1940; i--) { %>
                               <option value="<%= i %>"><%= i %></option>
                            <% } %> 
                          </select>                            
                        </div>                        
                      </div>                                                               
                    </div>
                  </div>                   
                  <div class="form-group">
                    <label for="gender" class="col-sm-2 control-label">Gender</label>
                    <div class="col-sm-10">
                      <label class="radio-inline">
                        <input type="radio" name="gender" id="gender" value="1"> Male
                      </label>
                      <label class="radio-inline">
                        <input type="radio" name="gender" id="gender" value="2"> Female
                      </label>                      
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="Specialization" class="col-sm-2 control-label">Specialization</label>
                    <div class="col-sm-10">
                      <% String[] specializations = new String[] {
                            "Podiatrics", "Nuero", "Dental", "Other"
                      };
                      for (int i = 0; i < specializations.length; i++) { %>
                        <label class="checkbox-inline">
                          <input type="checkbox" name="specialization" id="specialization" value="1">
                           <%= specializations[i] %>
                        </label>         
                        <% if((i + 1) % 3 == 0) { %> </br> <% } %>                                         
                      <% } %>                                          
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="Specialization" class="col-sm-2 control-label">Year of License</label>
                    <div class="col-sm-10">
                      <div class="row">
                        <div class="col-xs-3">                      
                          <select name="licenseYear" class="form-control">
                            <% for (int i = 2013; i >= 1950; i--) { %>
                              <option value="<%= i %>"><%= i %></option>
                            <% } %> 
                          </select>     
                        </div>          
                      </div>               
                    </div>
                  </div>                                      
                  </br>                
                  <h4>Work Address</h4>
                  <div class="form-group">
                    <label for="workStreetAddress" class="col-sm-2 control-label">Street Address</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" name="workStreetAddress" 
                            id="workStreetAddress" placeholder="Enter your street address">
                    </div>
                  </div>               
                  <div class="form-group">
                    <label for="workCity" class="col-sm-2 control-label">City</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" name="workCity" 
                            id="workCity" placeholder="Enter your city">
                    </div>
                  </div>   
                  <div class="form-group">
                    <label for="workPostalCode" class="col-sm-2 control-label">Postal Code</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" name="workPostalCode" 
                            id="workPostalCode" placeholder="Enter your postal code">
                    </div>                                       
                  </div>   
                  <div class="form-group">
                    <label for="workProvince" class="col-sm-2 control-label">Province</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" name="workProvince" 
                            id="workProvince" placeholder="Enter your province code">
                    </div>                                
                  </div>  
                  </br>
                  <h4>Home Address</h4>
                  <div class="form-group">
                    <label for="homeStreetAddress" class="col-sm-2 control-label">Street Address</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" name="homeStreetAddress" 
                            id="homeStreetAddress" placeholder="Enter your street address">
                    </div>
                  </div>               
                  <div class="form-group">
                    <label for="homeCity" class="col-sm-2 control-label">City</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" name="homeCity" 
                            id="homeCity" placeholder="Enter your city">
                    </div>
                  </div>   
                  <div class="form-group">
                    <label for="homePostalCode" class="col-sm-2 control-label">Postal Code</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" name="homePostalCode"
                            id="homePostalCode" placeholder="Enter your postal code">
                    </div>                                       
                  </div>   
                  <div class="form-group">
                    <label for="homeProvince" class="col-sm-2 control-label">Province</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" name="homeProvince"
                            id="homeProvince" placeholder="Enter your province code">
                    </div>                                
                  </div>                                                                                                   
                  <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                      <button type="submit" class="btn btn-lg btn-default">Sign up</button>
                    </div>
                  </div>                 
                </form>           
            </div>  
        </div>
        <script src="/ece356Project/static/js/bootstrap.min.js"></script>            
    </body>
</html>
