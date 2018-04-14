<div class="row mt-3">
	<div class="col" style="text-align:center;">
		<h1>User Sign-up</h1>
	</div>
</div>
<div class = "row mt-2">
    <div class = "offset-md-4 col-md-4">
    	<input type="text" class="form-control" id="email" placeholder="Email">
        <input type="text" class="form-control" id="fname" placeholder="First Name">
        <input type="text" class="form-control" id="lname" placeholder="Last Name">
        <input type="text" class="form-control" id="usernameInput" placeholder="Username">
        <input type="password" class="form-control" id="passwordInput" placeholder="Password">
        <label>Password must be atleast 8 characters, with atleast 1 of each: lowercase, uppercase, and special character</label>
        <button type="button" class="btn btn-primary" id="btnSubmit">Submit</button>
    </div>
</div>
<script>
	$("#btnSubmit").click(function(){
		var email = document.getElementById("email").value;
		if(!(email.includes("@")))
			alert("Invalid Email");
		else {	
			var fname = htmlEscape(document.getElementById("fname").value);
			var lname = htmlEscape(document.getElementById("lname").value);
		    var user = htmlEscape(document.getElementById("usernameInput").value);
		    var pass = htmlEscape(document.getElementById("passwordInput").value);    
		    if(passwordRegex.test(pass)) {
			    var path = "UserServlet?email="+email+"&user="+user+"&pass="+pass+"&fName="+fname+"&lName="+lname+"&param=register";
			    $.post(path, function(obj){
			        console.log(obj);
			        if(obj == "true"){
		                alert("Register Success!");
		                $.get("UserServlet?user="+user+"&pass="+pass+"&param=login", function(obj){
		                	 $("#center").load("login.jsp");
		                	 window.location.reload();
		                });
			           
			        }else{
			            alert("Username already taken!");
			        }
			    });
		    } else
		    	alert("Invalid Password");
		}
	});
</script>