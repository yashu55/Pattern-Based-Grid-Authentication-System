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
<title>Error</title>
</head>
<body class="bg-light">
	<div class="container-fluid  ">
		<div class="row justify-content-center align-items-center"
			style="height: 100vh; background-image: linear-gradient(120deg, #DDDDDA, #4A707A);">
			<div class="col-6 justify-content-center align-items-center">
				<div
					class="row d-flex justify-content-center align-items-center text-light">
					<h1 class="text-center">Oops! There is an Error :-(</h1>
				</div>
				<div class="row d-flex justify-content-center align-items-center">
					<h1 class="display-1 text-light font-weight-bold"><%=request.getAttribute("errorCode")%></h1>
				</div>
				<div
					class="row  d-flex justify-content-center align-items-center text-light">
					<h4 class="text-center"><%=request.getAttribute("errorMsg")%></h4>
				</div>
				<div class="row d-flex justify-content-center">
					<a class="btn  btn-lg btn-success m-1 " href="./" role="button">Back</a>
				</div>
			</div>
		</div>
	</div>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.slim.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.1/umd/popper.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>