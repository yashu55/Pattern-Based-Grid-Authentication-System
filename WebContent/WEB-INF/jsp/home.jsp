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
<title>Home</title>
</head>
<body class="bg-light">
	<!-- NavBar -->
	<jsp:include page="navbar.jsp" />
	<br>
	<br>
	<br>
	
	<div class="container-fluid my-4 ">
		<div class="row justify-content-around">
			<!--Login Page -->
			<div class="col-lg-6 bg-light shadow px-4 pt-4">
				<h4 class="display-4 ">
					<i class="fa fa-house-user "></i> Welcome,
					<%=(String) session.getAttribute("sessionUserId")%>
					!!
				</h4>
				
				<hr>
				<p class="text-justify">Pattern-Based Grid Authentication system is a web framework used to authenticate users for a particular service. This is a unique way to keep user authenticity by using Pattern-based password on the web services. This system allows user to register by using username and pattern. At the time of login, user has to give his username and a recall-based password by visualizing the randomly generated characters from a custom grid displayed to him. Hence, the user authentication is secure user-friendly yet robust compared to traditional textual password, 4-digit pin and classical pattern-lock system.</p>
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
</body>
</html>