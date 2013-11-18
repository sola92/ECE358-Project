<%-- 
    Document   : patientsearch.jsp
    Created on : Nov 17, 2013, 2:54:23 AM
    Author     : Sola
--%>
<%@page import="ece356.model.User"%>
<%
    Object x = session.getAttribute("user");
    if(x == null || session.getAttribute("userIsPatient") == null || !(Boolean)session.getAttribute("userIsPatient")) {
        response.sendRedirect("index.jsp");
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="/ece356Project/static/css/bootstrap.min.css" />
        <title>Patient Search</title>
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
            <div class="col-md-10 col-md-offset-1">
                <div class="row">  
                    <h4>Search Patients</h4>
                    <input type="text" class="form-control" name="query" 
                                id="query" placeholder="Enter a username">     
                </div> 
                <br></br>
                <div class="row" id="results">
                    <ul></ul>       
                </div>                 
            </div>                     
        </div>    
        <script src="/ece356Project/static/js/jquery-1.10.2.min.js"></script> 
        <script src="/ece356Project/static/js/bootstrap.min.js"></script>   
        <script type="text/javascript">
            $(document).ready(function () {
                window.FOLLOW = function (elem) {
                    elem = $(elem);
                    var followerID = window.CURRENT_USER_ID,
                        followeeID = elem.data("p-id");
                    $.ajax({
                      url: "FollowPatient",
                      type: "POST",
                      data: { "followerID": followerID, "followeeID": followeeID }
                    }).done(function( ) {
                        elem.parent().fadeOut();
                    }); 
                }                
                $('#query').keyup(function() {
                    var query = $(this).val();
                    $.ajax({
                      url: "PatientSearch",
                      type: "POST",
                      data: { "query": query }
                    }).done(function( html ) {
                        $("#results").html(html);
                    });
                });
            });
        </script>         
    </body>
</html>

