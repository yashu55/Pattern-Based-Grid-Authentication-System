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
<title>Forgot Pattern</title>
</head>
<body class="bg-light">
	<!-- NavBar -->
	<jsp:include page="navbar.jsp" />
	<br>
	<br>
	<br>
	<br>
	<div class="container-fluid my-4 ">
		<div class="row justify-content-around">
			<!--Login Page -->
			<div class="col-lg-6 bg-light shadow px-4 pt-4">
				<h2>
					<i class="fa fa-key"></i> Forgot Pattern
				</h2>
				<hr>
				<div id= "alertMsg" class="alert  alert-dismissible fade show"
					role="alert">
					
					<button type="button" class="close" data-dismiss="alert"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<!--Reset form -->
				<form class="" method="post" action="" id ="forgotForm">
					<div class="form-group">
						<label for="userId">User Id<span style="color:red;">*</span></label> 
						<input type="userId"
							class="form-control" name="userId" id="userId"
							aria-describedby="userIdHelpId" placeholder="Enter User Id"
							pattern="^[a-zA-Z][a-zA-Z0-9_]{3,25}$"
							title="User Id should start with alphabet. It can be alphanumeric with 
							 4-25 characters." 
							required>
					</div>
					<div class="form-group">
						<button type="submit" class="btn btn-primary">Generate
							OTP</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<br>
	<br>
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
<script >
document.getElementById("userId").addEventListener("focus", addValidatedClass);

function addValidatedClass(){
	document.getElementById("forgotForm").classList.add("was-validated");
}


var url_string = window.location.href;
var url = new URL(url_string);
var msg = url.searchParams.get("msg");

if(msg == null){
	document.getElementById("alertMsg").hidden = true;
}else if(msg == "invalidUser"){
	document.getElementById("alertMsg").innerHTML += "Please Enter valid User Id.";
	document.getElementById("alertMsg").hidden = false;
	document.getElementById("alertMsg").classList.add("alert-danger");
	console.log(document.getElementById("alertMsg"));
}else if(msg == "OTPError"){
	document.getElementById("alertMsg").innerHTML += "OTP was not verified. Please try again.";
	document.getElementById("alertMsg").hidden = false;
	document.getElementById("alertMsg").classList.add("alert-danger");
	console.log(document.getElementById("alertMsg"));
}


</script>

</body>
</html>