<!-- Navigation -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
  <div class="container">
    <a class="navbar-brand" href = "#" id = "logo">XD Shop Admin</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarResponsive">
      <ul class="navbar-nav ml-auto">
	   <li class="nav-item dropdown active">
        	<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	          Manage
	        </a>
	        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
	          <a class="dropdown-item nav-link" id="navManageUser" style="color: black;">User</a>
	          <a class="dropdown-item nav-link" id="navManagePM" style="color: black;">Product Manager</a>
	        </div>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#" id="navLogout">Logout</a>
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
$("#navManageUser").click(function(){
    $(this).parent().siblings().removeClass("active");
    $(this).parent().addClass("active");
    $("#center").load("banAndUnban.html");
});
$("#navManagePM").click(function(){
    $(this).parent().siblings().removeClass("active");
    $(this).parent().addClass("active");
    $("#center").load("banAndUnbanPM.html");
});
$("#navLogout").click(function(){
	$.get("UserServlet?param=logout", function(obj){
		window.location.reload();
	});
});
</script>