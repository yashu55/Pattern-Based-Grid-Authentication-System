<%@page import="com.patternGrid.dto.User"%>
<%@page import="com.patternGrid.dto.PatternType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	PatternType defaultPatternType = (PatternType) request.getAttribute("defaultPatternType");
	User userDetails = (User) request.getAttribute("userDetails");
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
<title>Verify OTP</title>
</head>
<body class="bg-light">
	<!-- NavBar -->
	<jsp:include page="navbar.jsp" />
	<br>
	<!-- Body -->
	<!--Reset Form -->
	<div class="container-fluid my-4 ">
		<div class="row justify-content-around">
			<!--Login Page -->
			<div class="col-md-7 bg-light shadow px-4 pt-4">
				<h4 class="display-4 ">
					<i class="fa fa-cogs"></i> Verify OTP
				</h4>
				<p class="text-secondary"></p>
				<hr>
				<div id= "alertMsg" class="alert  alert-dismissible fade show"
					role="alert">
					
					<button type="button" class="close" data-dismiss="alert"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<!--Reset form -->
				<form class="" method="post" action="verifyOTP" id="OTPForm">
					<div class="form-group">
						<label for="userId">User Id<span style="color:red;">*</span></label> <input type="text"
							class="form-control" name="userId" id="userId"
							aria-describedby="userIdHelpId" placeholder="Enter User Id"
							value="<%=userDetails.getUserId()%>" disabled required>
					</div>
					<div class="form-group">
						<label for="userEmail">Email address<span style="color:red;">*</span></label> <input type="email"
							class="form-control" id="userEmail" name="userEmail"
							aria-describedby="userEmailHelp" placeholder="Enter email"
							value="<%=userDetails.getUserEmail()%>" disabled required>
					</div>
					 <div class="form-group">
						<label for="otp">Enter OTP<span style="color:red;">*</span></label> <input type="text"
							class="form-control" name="otp" id="otp"
							aria-describedby="otpHelpId" placeholder="Enter OTP"
							  required>
					</div> 
					
						<div class="form-group">
							<button type="submit" id="registerBtn" class="btn btn-success"
								>Verify OTP</button>
						</div>
				</form>
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
	document.getElementById("otp").addEventListener("focus", addValidatedClass);

	function addValidatedClass(){
		document.getElementById("OTPForm").classList.add("was-validated");
	}


	var url_string = window.location.href;
	var url = new URL(url_string);
	var msg = url.searchParams.get("msg");

	if(msg == null){
		document.getElementById("alertMsg").hidden = true;
	}else if(msg == "invalidOTP"){
		document.getElementById("alertMsg").innerHTML += "OTP is incorrect. Please enter valid OTP.";
		document.getElementById("alertMsg").hidden = false;
		document.getElementById("alertMsg").classList.add("alert-danger");
		console.log(document.getElementById("alertMsg"));
	}
	
	
	</script>
	
</body>
</html>