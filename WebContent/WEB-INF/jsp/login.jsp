<%@page import="com.google.gson.Gson"%>
<%@page
	import="org.springframework.http.converter.json.GsonBuilderUtils"%>
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
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.0/css/all.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.14.0/css/all.min.css">
<title>Login</title>
</head>
<body class="bg-light">
	<!-- NavBar -->
	<jsp:include page="navbar.jsp" />
	<br>
	<br>
	<%
		Boolean beforeRandomPattern = (Boolean) session.getAttribute("loginValue");
		if (beforeRandomPattern == null)
			beforeRandomPattern = false;
		String[][] randomPatternGrid = (String[][]) request.getAttribute("randomPatternGrid");
		User user = (User) request.getAttribute("user");
		String userIdValue = "";
		String randomPatternGridJSON = "";

		if (user != null) {
			userIdValue = user.getUserId();
		}

		if (randomPatternGrid != null) {
			{
				Gson gson = new Gson();
				randomPatternGridJSON = gson.toJson(randomPatternGrid);
			}
		}
	%>
	<!--  Login Form -->
	<div class="container-fluid my-4 ">
		<div class="row justify-content-around">
			<!--Login Page -->
			<div class="col-lg-6 bg-light shadow px-4 pt-4">
				<h4 class="display-4 ">
					<i class="fa fa-sign-in-alt"></i> Login
				</h4>
				<p class="text-secondary">Enter login credentials.</p>
				<hr>
				<div id= "alertMsg" class="alert  alert-dismissible fade show"
					role="alert">
					
					<button type="button" class="close" data-dismiss="alert"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<!--Login form -->
				<form class="" method="post" action="login" id="loginForm">
					<!-- User ID Logic -->
					<div class="form-group">
						<label for="userId">User Id<span style="color:red;">*</span></label> <input type="text"
							class="form-control" name="userId" id="userId"
							aria-describedby="userIdHelpId" placeholder="Enter User Id"
							pattern="^[a-zA-Z][a-zA-Z0-9_]{3,25}$"
							title="User Id should start with alphabet. It can be alphanumeric with 4-25 characters."
							value="<%=userIdValue%>" required <%if (beforeRandomPattern) {%>
							readonly <%} ;%>>
					</div>
					<!-- Random Grid Logic -->
					<%
						if (beforeRandomPattern) {
					%>
					<%
						int row = (Integer) request.getAttribute("row");
							int col = (Integer) request.getAttribute("col");
							int cx = 50;
							int cy = 50;
					%>
					<div class="container-fluid m-0 p-0">
						<h3 class="text-center">Pattern Grid</h3>
						<div class="row d-flex justify-content-center">
							<div class="col d-flex justify-content-center align-items-center"
								style="height: 250px;">
								<svg version="1.1" height="100%"
									viewBox="0 0 <%=col * 100%> <%=row * 100%>">
                   
                   
                   <%
                                                         	for (int i = 0; i < row; i++) {
                                                         			for (int j = 0; j < col; j++) {
                                                         %>
                	   		 <circle cx="<%=cx + j * 100%>"
										cy="<%=cy + i * 100%>" r="40" stroke="black" stroke-width="5"
										fill="#87CEFA" fill-opacity="0.5" />
                             <text x="<%=cx + j * 100%>"
										y="<%=cy + i * 100%>" text-anchor="middle" stroke="black"
										font-size="2.5em" dy=".3em"><%=randomPatternGrid[i][j]%></text>
                   <%
                   	}
                   		}
                   %>
                   
                  </svg>
							</div>
						</div>
					</div>
					<%
						} ;
					%>
					<!-- Random Grid Logic End -->
					<!--Pattern Password Logic -->
					<%
						if (beforeRandomPattern) {
					%>
					<div class="form-group">
					
						<label for="userPatternPassword">Pattern Password<span style="color:red;">*</span></label> 
												<div class="row d-flex justify-content-start ml-1" >
						
						<input
							type="password" class="form-control" name="userPatternPassword"
							title="The pattern password should be entered using the random grid characters in a sequence in which the pattern was drawn."
							id="userPatternPassword" placeholder="Pattern Password"
							minlength="8" required>
							 <i class=" far fa-eye" id="togglePassword" style="margin-left:-50px;
							 margin-top:10px;
							 cursor:pointer;"></i>
					</div>
					</div>
					
				
					<%
						} ;
					%>
					<!-- Hidden Form Fields -->
					<input type="hidden" id="randomGrid" name="randomGrid"
						value='<%=randomPatternGridJSON%>' required>
					<!--Submit Btn Logic -->
					<div class="form-group">
						<%
							if (!beforeRandomPattern) {
						%>
						<button type="submit" class="btn btn-primary">Next</button>
						<%
							} else {
						%>
						<button type="submit" class="btn btn-primary ">Submit</button>
						<%
							} ;
						%>
					</div>
				</form>
				<div>
			
					<a class="text-primary " href="register">Register User</a> |
					<a class="text-primary " href="forgotPattern">Forgot Pattern?</a>
					<br>
					<br>
				</div>
			</div>
		</div>
	</div>
	
	<br>
	<br>
	
	<!-- Footer -->
	<jsp:include page="footer.jsp" />
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.slim.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.1/umd/popper.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.2/js/bootstrap.min.js"></script>
	<script>
	
	var url_string = window.location.href;
	var url = new URL(url_string);
	var msg = url.searchParams.get("msg");
	
	if(msg == null){
		document.getElementById("alertMsg").hidden = true;
	}else if(msg == "invalidCredentials" || msg == "invalidUser"){
		document.getElementById("alertMsg").innerHTML += "You have entered invalid credentials. Please try again.";
		document.getElementById("alertMsg").hidden = false;
		document.getElementById("alertMsg").classList.add("alert-danger");
	}else if(msg == "successful"){
		document.getElementById("alertMsg").innerHTML += "New Pattern is set successfully. Login to continue.";
		document.getElementById("alertMsg").hidden = false;
		document.getElementById("alertMsg").classList.add("alert-success");
		console.log(document.getElementById("alertMsg"));
	}
	
	
	
	
	/* document.getElementById("userId").addEventListener("focus", addValidatedClass);
	document.getElementById("userPatternPassword").addEventListener("focus", addValidatedClass);

	function addValidatedClass(){
		document.getElementById("loginForm").classList.add("was-validated");
	} */
	
		const togglePassword = document.querySelector('#togglePassword');
		const password = document.querySelector('#userPatternPassword');

		togglePassword.addEventListener('click', function(e) {
			// toggle the type attribute
			const type = password.getAttribute('type') === 'password' ? 'text'
					: 'password';
			password.setAttribute('type', type);
			// toggle the eye slash icon
			this.classList.toggle('fa-eye-slash');
		});
	</script>
</body>
</html>