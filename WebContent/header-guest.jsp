<!-- Navigation -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
  <div class="container">
    <a class="navbar-brand" href = "#" id = "logo">XD Shop</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarResponsive">
      <ul class="navbar-nav ml-auto">
        <li class="nav-item active">
          <a class="nav-link" id = "home">Home
            <span class="sr-only">(current)</span>
          </a>
        </li>
        <li class="nav-item">
          <a class="nav-link" id="navLogin">Login</a>
        </li>
        <li class="nav-item dropdown">
        	<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	          Sign up
	        </a>
	        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
	          <a class="dropdown-item nav-link" id="navSignUp" style="color: black;">Sign up as User</a>
	          <a class="dropdown-item nav-link" id="navPMSignUp" style="color: black;">Sign up as PM</a>
	        </div>
        </li>
        <!-- <li class="nav-item">
          <a class="nav-link" id="navSignUp">Sign up</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" id="navPMSignUp">PM Sign up</a>
        </li> -->
      </ul>
    </div>
  </div>
</nav>
<script src = "javascript/htmlEscape.js"></script>
<script>
	checkIfValidRedirect();
$("#logo").click(function(){
    $(this).parent().siblings().removeClass("active");
    $(this).parent().addClass("active");
    $("#center").load("default.jsp");
});
$("#home").click(function(){
    $(this).parent().siblings().removeClass("active");
    $(this).parent().addClass("active");
    $("#center").load("default.jsp");
});
$("#navLogin").click(function(){
    $(this).parent().siblings().removeClass("active");
    $(this).parent().addClass("active");
    $("#center").load("login.jsp");
});
$("#navSignUp").click(function(){
    $(this).parent().siblings().removeClass("active");
    $(this).parent().addClass("active");
    $("#center").load("reg-user.jsp");
});
$("#navPMSignUp").click(function(){
    $(this).parent().siblings().removeClass("active");
    $(this).parent().addClass("active");
    $("#center").load("reg-pm.jsp");
});
</script>