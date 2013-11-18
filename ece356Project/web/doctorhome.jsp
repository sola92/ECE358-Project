<%@page import="ece356.model.ProjectDBAO"%>
<%@page import="ece356.model.Doctor"%>
<%
    
  Object x = session.getAttribute("user");
  if( x == null || 
      session.getAttribute("userIsDoctor") == null || 
      !(Boolean)session.getAttribute("userIsDoctor")) {
      response.sendRedirect("index.jsp");
      return;
  }
  Doctor d = (Doctor)x;
  if(d == null) {
      response.sendRedirect("index.jsp");
      return;
  }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="/ece356Project/static/css/bootstrap.min.css" />
        <title>Doctor Home</title>
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
                <h3 style="text-align:center;">Change Doctor Account</h3>
                <br></br>
                <form class="form-horizontal" action="" method="POST" role="form">                  
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
                             id="lastName" placeholder="Enter your last name" value=<%= d.getLastName() %> >
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
                                                                                                                     
                  <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                      <button type="submit" class="btn btn-lg btn-default">Save Changes</button>
                    </div>
                  </div>                 
                </form>           
            </div>  
        </div>
        <script src="/ece356Project/static/js/jquery-1.10.2.min.js"></script> 
        <script src="static/js/bootstrap.min.js"></script>            
    </body>
</html>