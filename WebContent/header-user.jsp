<!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
      <div class="container">
        <a class="navbar-brand" href = "#"  id = "logo">XD Shop</a>
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
              <a class="nav-link" id="navAccount">Account</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" id="navViewCart">View Cart</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" id="navLogout">Logout</a>
            </li>

          </ul>
        </div>
      </div>
     </nav>
      <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
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
            $("#navViewCart").click(function(){
                $(this).parent().siblings().removeClass("active");
                $(this).parent().addClass("active");
                $("#center").load("cart.jsp");
            });
            $("#navAccount").click(function(){
                $(this).parent().siblings().removeClass("active");
                $(this).parent().addClass("active");
                $("#center").load("account.jsp");
            });
    		$("#navLogout").click(function(){
    			$.get("UserServlet?param=logout", function(obj){
    				window.location.reload();
    			});
    		});
    	</script>
    