
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
<title>About</title>
</head>
<body class="bg-light">
	<!-- NavBar -->
	<jsp:include page="navbar.jsp" />
	
	
	
	<div class="container-fluid my-4 ">
		<div class="row justify-content-around">
			<!--Login Page -->
			<div class="col-lg-10 bg-light shadow px-4 pt-4">
				<h4 class="display-4 ">
					<i class="fa fa-info-circle "></i> About
				</h4>
				<p class="text-secondary">About page</p>
				<hr>
				<p class="text-justify">
					&emsp;Pattern-based grid authentication system is a unique, user-friendly yet robust way of authenticating the users. The project is an advancement over the traditional 4-digit Pin, textual password and pattern-lock system. It is designed to be integrated into the existing ‘e-Pramaan’ Single Sign-On (SSO) facility used by Indian citizens to access various government services on a single platform. Existing classical authentication techniques such as textual passwords and pattern-lock are prone to various attacks. The most popular textual-based password system is prone to shoulder surfing attacks. Another popular authentication method is a pattern-lock method. It is widely used on mobile devices and smartphones. A pattern-lock is simple and easier for the user to remember, but it has limitations. Numerous research studies show that users choose patterns from a small space which makes them vulnerable to a variety of attacks such as guessing attacks, shoulder-surfing attacks and smudge attacks. By this project, we allow users to use a recall-based pattern-grid authentication system, instead of the traditional pattern-lock, 4-digit pin or textual password. Our system will help in preventing user’s personal assets from being stolen by their adversaries and provide an easy safe and secure way to login.
				</p>
				<p class="text-justify">
					&emsp;The project, a pattern-based grid authentication is developed to overcome and eliminate shoulder-surfing attacks and smudge attacks. It will also serves the purpose as having One Time Password with no extra network mediums like SMS and Email. The system allows users to draw a pattern over a (3 ✕ 3, 4 ✕ 3, 3 ✕ 4, 4 ✕ 4 or a custom configurable size) grid on the web page using their mobile phones or computer devices during registration. Whenever a user wishes to authenticate, (s) he is presented with a random alphanumeric challenge grid. The user then enters the characters in the cells that correspond to the registered pattern. This recall-based technique asks the user to reproduce something that the user has created earlier during the registration stage. Recall-based techniques usually requires the user to interact with the system in some cognitively meaningful manner. Hence, a pattern-based grid authentication is user-friendly yet robust enough to provide a safe and secure login facility.  
				</p>
				<br>
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
</body>
</html>