  <%--  <%@ page import="java.net.*" %>
   
   <%
   URL url = new URL(request.getRequestURL().toString());
   
   String _Path = url.getPath(); 
   
   
   %>
    --%>
   
    <!-- navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
      <a class="navbar-brand" href="#">
        <i class="fa fa-user-lock" aria-hidden="true"></i> Pattern Grid
        Authentication</a>
      <button
        class="navbar-toggler"
        type="button"
        data-toggle="collapse"
        data-target="#navbarText"
        aria-controls="navbarText"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarText">
        <ul class="navbar-nav mr-auto">
          <li class="nav-item ">
            <a class="nav-link" href="login"
              >Login<span class="sr-only">(current)</span></a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="register">Register</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="about">About</a>
          </li>  
        </ul>
      </div>
    </nav>


