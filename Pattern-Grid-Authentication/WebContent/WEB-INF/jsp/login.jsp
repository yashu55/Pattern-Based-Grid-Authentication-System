<%@page import="com.patternGrid.dto.User"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta name="Description" content="Enter your description here" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.0/css/all.min.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.14.0/css/all.min.css">
    <title>Login</title>
</head>

<body class="bg-light">

<!-- NavBar -->
<jsp:include page="navbar.jsp" />  

<% 
boolean beforeRandomPattern = (Boolean)session.getAttribute("loginValue"); 
String[] randomPatternGrid = (String[])request.getAttribute("randomPatternGrid");
User user = (User)request.getAttribute("user");
String userIdValue = "";
if(user != null){
	userIdValue = user.getUserId();
}
%>


 <!-- Teacher Login Form -->
   <div class="container-fluid my-4 ">
    <div class="row justify-content-around">
        <!--Login Page -->
        <div class="col-lg-6 bg-light shadow px-4 pt-4">
            <h4 class="display-4 ">
                <i class="fa fa-sign-in-alt"></i> Login</h4>
            <p class="text-secondary">Enter login details.</p>
            <hr>
            <!--Login form -->
            <form  method="post" action="login">
              
              
        <!-- User ID Logic -->
                <div class="form-group">
                    <label for="userId">User Id</label>
                    <input type="text" class="form-control" name="userId" id="userId" aria-describedby="userIdHelpId"
                        placeholder="Enter User Id" value="<%=userIdValue%>" required
                       <%if(beforeRandomPattern){%>readonly<%};%>>
                    <small id="userIdHelpId" class="form-text text-muted">*Required</small>
                </div>
                
                
                
                
        <!-- Random Grid Logic -->   
        
        <% if(beforeRandomPattern){ %>           
               <div class="container-fluid m-0 p-0">
                <p>Pattern Grid</p>
                 <div class="row d-flex justify-content-center" >
                   <div class="col d-flex justify-content-center align-items-center" style="height:250px;">
                  <svg  version="1.1" height="100%" viewBox="0 0 300 300">
                   
                    <circle cx="50" cy="50" r="40" stroke="black" stroke-width="5" fill="#87CEFA" fill-opacity="0.5" />
                    <text x="50" y="50" text-anchor="middle" stroke="black" font-size="3em" dy=".3em">ch</text>
                   
                    <circle cx="150" cy="50" r="40" stroke="black" stroke-width="5" fill="#87CEFA" fill-opacity="0.5" />
                    <text x="150" y="50" text-anchor="middle" stroke="black" font-size="3em" dy=".3em">1R</text>
                    
                    <circle cx="250" cy="50" r="40" stroke="black" stroke-width="5" fill="#87CEFA" fill-opacity="0.5" />
                    <text x="250" y="50" text-anchor="middle" stroke="black" font-size="3em" dy=".3em">tU</text>
                    
                    <circle cx="50" cy="150" r="40" stroke="black" stroke-width="5" fill="#87CEFA" fill-opacity="0.5" />
                    <text x="50" y="150" text-anchor="middle" stroke="black" font-size="3em" dy=".3em">8x</text>
                    
                    <circle cx="150" cy="150" r="40" stroke="black" stroke-width="5" fill="#87CEFA" fill-opacity="0.5" />
                    <text x="150" y="150" text-anchor="middle" stroke="black" font-size="3em" dy=".3em">uI</text>
                    
                    <circle cx="250" cy="150" r="40" stroke="black" stroke-width="5" fill="#87CEFA" fill-opacity="0.5" />
                    <text x="250" y="150" text-anchor="middle" stroke="black" font-size="3em" dy=".3em">p0</text>
                    
                     <circle cx="50" cy="250" r="40" stroke="black" stroke-width="5" fill="#87CEFA" fill-opacity="0.5" />
                    <text x="50" y="250" text-anchor="middle" stroke="black" font-size="3em" dy=".3em">q2</text>
                    
                    <circle cx="150" cy="250" r="40" stroke="black" stroke-width="5" fill="#87CEFA" fill-opacity="0.5" />
                    <text x="150" y="250" text-anchor="middle" stroke="black" font-size="3em" dy=".3em">v4</text>
                    
                    <circle cx="250" cy="250" r="40" stroke="black" stroke-width="5" fill="#87CEFA" fill-opacity="0.5" />
                    <text x="250" y="250" text-anchor="middle" stroke="black" font-size="3em" dy=".3em">77</text>
                    
                  </svg> 
                </div>
                </div>
                </div>
          <%}; %>
          
                <%if(beforeRandomPattern){ %> 	
                <% for(int i = 0; i < randomPatternGrid.length; i++) {%>
                	<%=randomPatternGrid[i]%>
                	<%}; %>
                <%};%>
            
              
         <!--Pattern Password Logic -->
              <%if(beforeRandomPattern){ %>
                 <div class="form-group">
                    <label for="userPatternPassword">Pattern Password</label>
                    <input type="password" class="form-control" name="userPatternPassword" id="userPatternPassword" placeholder="Pattern Password"
                         required>
                    <small id="userPatternPasswordHelpId" class="form-text text-muted">*Required</small>
                </div>
              <%}; %>
               
               
               
      <!--Submit Btn Logic -->          
                <div class="form-group">
              <%   if(!beforeRandomPattern){ %>
                    <button type="submit" class="btn btn-primary">Next</button>
                <%}else{ %>        
                    <button type="submit" class="btn btn-primary ">Submit</button>
                <%}; %>    
                </div>
            </form>
        </div>
    </div>
</div>


    
    






<!-- Footer --> 
<jsp:include  page="footer.jsp" />   

 <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.1/umd/popper.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>

</html>