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
    <title>Register</title>
</head>

<body class="bg-light">

<!-- NavBar -->
<jsp:include page="navbar.jsp" />  



<!-- Body -->
<!--Register Form -->
   <div class="container-fluid my-4 ">
    <div class="row justify-content-around">
        <!--Login Page -->
        <div class="col-md-7 bg-light shadow px-4 pt-4">
            <h4 class="display-4 ">
                <i class="fa fa-user-plus"></i> Register</h4>
            <p class="text-secondary">Enter login details.</p>
            <hr>
            <!--Login form -->
            <form class="" method="post" action="">
                <div class="form-group">
                    <label for="userId">User Id</label>
                    <input type="userId" class="form-control" name="userId" id="userId" aria-describedby="userIdHelpId"
                        placeholder="Enter User Id" required>
                    <small id="userIdHelpId" class="form-text text-muted">*Required</small>
                </div>

                <div class="form-group">
                    <label for="userEmail">Email address</label>
                    <input type="email" class="form-control" id="userEmail" name="userEmail" aria-describedby="userEmailHelp" placeholder="Enter email">
                    <small id="userEmailHelp" class="form-text text-muted">*Required</small>
                  </div>

                <div class="form-group">
                    <label for="pwd">Pattern Password</label>
                    <input type="password" class="form-control" name="userPatternPassword" id="userPatternPassword" placeholder="Pattern Password"
                         required>
                    <small id="userPatternPasswordHelpId" class="form-text text-muted">*Required</small>
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-success">Register</button>
                    <button type="reset" class="btn btn-danger">Reset</button>
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