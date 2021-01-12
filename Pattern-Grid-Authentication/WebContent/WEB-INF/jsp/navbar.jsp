
<!-- navbar -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
	<a class="navbar-brand" href="#"> <i class="fa fa-user-lock"
		aria-hidden="true"></i> Pattern Grid Authentication
	</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarText" aria-controls="navbarText"
		aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
	<div class="collapse navbar-collapse" id="navbarText">
		<ul class="navbar-nav mr-auto">
			<%
				if (session.getAttribute("sessionUserId") == null) {
			%>
			<li class="nav-item "><a class="nav-link" href="login">Login</a>
			</li>
			<li class="nav-item"><a class="nav-link" href="register">Register</a>
			</li>
			<%
				} else {
			%>
			<li class="nav-item"><a class="nav-link" href="home">Home</a></li>
			<li class="nav-item"><a class="nav-link" href="settings">Settings</a>
			</li>
			<%
				}
			%>
			<li class="nav-item"><a class="nav-link" href="about">About</a>
			</li>
		</ul>
		<ul class="navbar-nav">
			<%
				if (session.getAttribute("sessionUserId") != null) {
			%>
			<li class="nav-item "><a class="btn btn-outline-light mx-2"
				href="logout" role="button">Logout</a></li>
			<%
				}
				;
			%>
		</ul>
	</div>
</nav>
