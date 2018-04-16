<html>
    <!-- stuff i need -->
    <head>
    	<link href="frameworks/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	    <link href="css/custom.css" rel="stylesheet">
	    <script src="frameworks/jquery/jquery.min.js"></script>
	    <script src="frameworks/bootstrap/js/bootstrap.bundle.min.js"></script>
    </head>
    <body>
	    <div class="container">
	    	<div id = "headerContainer">
		        <jsp:include page="header-guest.jsp"></jsp:include>
		    </div>
		    <div id = "center">
		        <jsp:include page="browse-shop.jsp"></jsp:include>
		    </div>
	    </div>    
    </body>
    <script src = "javascript/htmlEscape.js"></script>
	<script>
	checkIfValidRedirect();
        var prodid = window.location.href.split("=")[1];
        var name_or_null = document.cookie.split("=")[1];
        if(prodid != null){
            $("#center").load("item-info.jsp");
        }
        if (name_or_null != null){
            $.get("UserServlet?user=" + name_or_null + "&param=user", function(obj){
                var person = JSON.parse(obj);
                console.log(person.adminUsername);
                if (person.fname != null){
                    $('#headerContainer').load("header-user.jsp");
                }else if(person.storeName != null){
                    $('#headerContainer').load("header-pm.jsp");
                }else if (person.adminUsername != null){
                	console.log("I'm an admin god!");
                	$('#headerContainer').load("header-admin.jsp");
                }
                });
            
        }
        $("#center").load("default.jsp");
        
    </script>
</html>