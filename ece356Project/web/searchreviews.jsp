<%-- 
    Document   : searchreviews
    Created on : Nov 18, 2013, 4:36:27 AM
    Author     : Sola
--%>
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
                        <div class="row">
                            <div class="col-md-4 ">                          
                                <div class="form-group">
                                    <label for="streetAddress">Keyword</label>
                                    <input type="text" class="form-control" name="query" 
                                           id="query" placeholder="Enter a street address" />
                                </div>                              
                            </div>                        
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label for="streetAddress">Start Date (YYYY-MM-DD)</label>
                                    <input type="text" class="form-control" name="startDate" 
                                           id="startDate" placeholder="Enter the start date" />
                                </div>                         
                            </div>
                            <div class="col-md-4 ">
                                <div class="form-group">
                                    <label for="streetAddress">End Date (YYYY-MM-DD)</label>
                                    <input type="text" class="form-control" name="endDate" 
                                           id="endDate" placeholder="Enter the end date" />
                                </div>                                                       
                            </div>                              
                        </div>
                        <div class="row">
                            <div class="col-md-12"> 
                                <button type="button" id="searchButton" 
                                        class="btn btn-primary pull-right">Search</button>
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
                window.DELETE = function (elem) {
                    $.ajax({
                      url: "DeleteReview",
                      type: "POST",
                      data: { "reviewID": $(elem).data('r-id') }
                    }).done(function() {
                        $(elem).parent().parent().fadeOut();
                    });
                };
                
                $('#searchButton').click(function() {
                    var data = $('form').serialize();
                    console.log(data);
                    $.ajax({
                      url: "SearchReview",
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