<!-- Navigation -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
  <div class="container">
    <a class="navbar-brand" href="#">XD Shop</a>
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
        <li class="nav-item">
          <a class="nav-link" id="navSignUp">Sign up</a>
        </li>
      </ul>
    </div>
  </div>
</nav>
<script>
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
    $("#center").load("register.jsp");
});

</script>