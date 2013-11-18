<%-- 
    Document   : viewdoctors
    Created on : Nov 18, 2013, 3:04:04 AM
    Author     : Sola
--%>
<%@page import="ece356.model.Address"%>
<%@page import="ece356.model.Doctor"%>
<%@page import="ece356.model.Gender"%>
<%@page import="ece356.model.ProjectDBAO"%>
<%
    Object x = session.getAttribute("user");
    if(x == null || session.getAttribute("userIsAdmin") == null || !(Boolean)session.getAttribute("userIsAdmin")) {
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
        <title>Doctor Search</title>
    </head>
    <body>
        <script type="text/javascript">
            window.CURRENT_USER_ID = <%= ((User)session.getAttribute("user")).getUserID() %>;
        </script>
        <nav class="navbar navbar-default" role="navigation">
          <!-- Brand and toggle get grouped for better mobile display -->
          <div class="navbar-header">
            <a class="navbar-brand" href="patienthome.jsp">ECE356 Project</a>
          </div>
        </nav>
        <br></br>
        <div class="row">
            <form >
                <div class="col-md-10 col-md-offset-1">
                    <div class="row well well-lg">  
                        <div class="col-md-4 ">
                            <h4>Work Address</h4>
                            <div class="form-group">
                                <label for="startDate">Street Address</label>
                                <input type="text" class="form-control" name="streetAddress" 
                                            id="streetAddress" placeholder="Enter a street address">
                            </div>
                            <div class="form-group">
                            <label for="province">Province</label>
                            <select class="form-control" name="province" id="province">
                                <option value="" selected>None</option>
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
                            <div class="form-group">
                                <label for="city">City</label>
                                <input type="text" class="form-control" name="city" 
                                            id="city" placeholder="Enter a city">
                            </div>
                            <div class="form-group">
                                <label for="postalCode">Postal Code</label>
                                <input type="text" class="form-control" name="postalCode" 
                                        id="postalCode" placeholder="Enter a postal code">
                            </div> 
                        </div>
                        <div class="col-md-4 ">
                            <h4>Personal/Professional Info</h4>
                            <div class="form-group">
                                <label for="postalCode">First Name</label>
                                <input type="text" class="form-control" name="firstName" 
                                        id="firstName" placeholder="Enter a first name">
                            </div>     
                           <div class="form-group">
                                    <label for="lastName">Last Name</label>
                                    <input type="text" class="form-control" name="lastName" 
                                            id="lastName" placeholder="Enter a last name">
                            </div>          
                           <div class="form-group">
                                <label for="licenseYearStart">Lowest License Year</label>
                                <select name="licenseYearStart" class="form-control">
                                    <option value="">None</option>
                                    <% for (int i = 1950; i <= 2013; i++) { %>
                                      <option value="<%= i %>"><%= i %></option>
                                    <% } %> 
                                </select>  
                            </div>  
                           <div class="form-group">
                                <label for="licenseYearEnd">Highest License Year</label>
                                <select name="licenseYearEnd" class="form-control">
                                    <option value="">None</option>
                                    <% for (int i = 2013; i >= 1950; i--) { %>
                                      <option value="<%= i %>"><%= i %></option>
                                    <% } %> 
                                </select>  
                            </div>                                                                                      
                        </div>
                        <div class="col-md-4">
                            <h4>Reviews</h4>
                            <div class="form-group">
                                <label for="averageRatingStart">Lowest Average Rating</label>
                                <select name="averageRatingStart" class="form-control">
                                    <option value="">None</option>
                                    <% for (float i = 0; i <= 5; i += 0.5) { %>
                                      <option value="<%= i %>"><%= i %></option>
                                    <% } %> 
                                </select>  
                            </div>  
                            <div class="form-group">
                                <label for="averageRatingEnd">Highest License Year</label>
                                <select name="averageRatingEnd" class="form-control">
                                    <option value="">None</option>
                                    <% for (float i = 0; i <= 5; i += 0.5) { %>
                                      <option value="<%= i %>"><%= i %></option>
                                    <% } %> 
                                </select>  
                            </div> 
                            <div class="form-group">
                                <label for="recommendedByFriend" class="control-label">Recommended By Friend ?</label>
                                <br>
                                <label class="radio-inline">
                                  <input type="radio" name="recommendedByFriend" 
                                            id="recommendedByFriend" value="`    " checked> None
                                </label>
                                <label class="radio-inline">
                                  <input type="radio" name="recommendedByFriend" 
                                            id="recommendedByFriend" value="true"> Yes
                                </label>
                                <label class="radio-inline">
                                  <input type="radio" name="recommendedByFriend" 
                                            id="recommendedByFriend" value="false"> No
                                </label>
                            </div>   
                            <div class="form-group">
                                <button id="searchButton" type="button" 
                                    class="btn btn-primary btn-lg btn-lg btn-block">Search</button>
                            </div>                                                      
                        </div>
                        
                    </div> 
                    <br></br>
                    <div class="row" id="results">
                        <h4>Results</h4>
                        <ul></ul>       
                    </div>                 
                </div>
            </form>                     
        </div>    
        <script src="/ece356Project/static/js/jquery-1.10.2.min.js"></script> 
        <script src="/ece356Project/static/js/bootstrap.min.js"></script>   
        <script type="text/javascript">
            $(document).ready(function () {    
                $('#searchButton').click(function() {
                    var data = $('form').serialize();
                    console.log(data);
                    $.ajax({
                      url: "DoctorSearch",
                      type: "POST",
                      data: data
                    }).done(function( html ) {
                        $("#results").html(html);
                    });
                });       
            });
        </script>         
    </body>
</html>
