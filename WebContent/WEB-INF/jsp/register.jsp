<%@page import="com.patternGrid.dto.PatternType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	PatternType defaultPatternType = (PatternType) request.getAttribute("defaultPatternType");
%>
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
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/patternlock.css" />
<title>Register</title>
</head>
<body class="bg-light">
	<!-- NavBar -->
	<jsp:include page="navbar.jsp" />
	<!-- Body -->
	<!--Register Form -->
	<div class="container-fluid  ">
		<div class="row justify-content-around">
			<!--Login Page -->
			<div class="col-md-7 bg-light shadow px-4 pt-4">
				<h4 class="display-4 ">
					<i class="fa fa-user-plus"></i> Register
				</h4>
				<p class="text-secondary">Enter registration details.</p>
				<hr>
				<div id= "alertMsg" class="alert  alert-dismissible fade show"
					role="alert">
					
					<button type="button" class="close" data-dismiss="alert"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<!--Register form -->
				<form class="" method="post" action="register" id ="loginForm">
					<div class="form-group">
						<label for="userId">User Id<span style="color:red;">*</span></label> <input type="text"
							class="form-control" name="userId" id="userId"
							pattern="^[a-zA-Z][a-zA-Z0-9_]{3,25}$"
							title="User Id should start with alphabet. It can be alphanumeric with 
							 4-25 characters."
							aria-describedby="userIdHelpId" placeholder="Enter User Id"
							required>
					</div>
					<div class="form-group">
						<label for="userEmail">Email<span style="color:red;">*</span></label> <input type="email"
							class="form-control" id="userEmail" name="userEmail"
							aria-describedby="userEmailHelp" placeholder="Enter email"
							required>
					</div>
					<div class="container-fluid m-0 p-0">
						<div class="row d-flex justify-content-center">
							<h4 id="notification">Draw New Pattern</h4>
						</div>
						<div class="row d-flex justify-content-center">
							<div class="col d-flex justify-content-center align-items-center">
								<!-- Pattern Div   -->
								<div id="patternHolder"></div>
							</div>
						</div>
						<div class="row d-flex justify-content-center">
							<button type="button" name="mySaveBtn" id="mySaveBtn"
								onclick="mySaveFunction()"
								class="btn btn-sm btn-primary my-2 m-1">Save Pattern</button>
						</div>
						<div class="row d-flex justify-content-center">
							<button type="button" name="myResetBtn" id="myResetBtn"
								onclick="myResetFunction()" class="btn btn-danger btn-sm m-1">Reset</button>
						</div>
						<div class="form-group">
							<input type="text" class="form-control"
								name="userPatternPassword" id="userPatternPassword"
								hidden="true" required />
						</div>
						<div class="form-group">
							<button type="submit" id="registerBtn" class="btn btn-success"
								disabled>Register</button>
						</div>
					</div>
				</form>
				<div>
					<a class="text-primary " href="login">Login</a> | <a
						class="text-primary " href="forgotPattern">Forgot Pattern?</a> <br>
					<br>
				</div>
			</div>
		</div>
	</div>
	<!-- Footer -->
	<jsp:include page="footer.jsp" />
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.slim.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.1/umd/popper.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.2/js/bootstrap.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/javascript/patternlock.js"></script>
	<script>
		let lock = new PatternLock("#patternHolder", {
			matrix : [
	<%=defaultPatternType.getPatternRowSize()%>
		,
	<%=defaultPatternType.getPatternColSize()%>
		]
		});
	</script>
	<script
		src="${pageContext.request.contextPath}/resources/javascript/patternValidation.js"></script>
		
		<script>
		document.getElementById("userId").addEventListener("focus", addValidatedClass);
		
		function addValidatedClass(){
			document.getElementById("loginForm").classList.add("was-validated");
		}

		
		
		
		var url_string = window.location.href;
		var url = new URL(url_string);
		var msg = url.searchParams.get("msg");
		
		if(msg == null){
			document.getElementById("alertMsg").hidden = true;
		}else if(msg == "userIdExists"){
			document.getElementById("alertMsg").innerHTML += "The User Id is already present!! Please try with a different User Id.";
			document.getElementById("alertMsg").hidden = false;
			document.getElementById("alertMsg").classList.add("alert-danger");
			console.log(document.getElementById("alertMsg"));
		}else if(msg == "unSuccessful" || msg == "invalidPattern"){
			document.getElementById("alertMsg").innerHTML += "Invalid User Id/ Pattern. Please try again.";
			document.getElementById("alertMsg").hidden = false;
			document.getElementById("alertMsg").classList.add("alert-danger");
		}else if(msg == "successful"){
			document.getElementById("alertMsg").innerHTML += "You have registered successfully!!!";
			document.getElementById("alertMsg").hidden = false;
			document.getElementById("alertMsg").classList.add("alert-success");
		}
		</script>
</body>
</html>