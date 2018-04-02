<div class = "row top100">
    <div class = "offset-md-4 col-md-4">
    	<input type="text" class="form-control" id="email" placeholder="Email">
        <input type="text" class="form-control" id="fname" placeholder="First Name">
        <input type="text" class="form-control" id="lname" placeholder="Last Name">
        <input type="text" class="form-control" id="usernameInput" placeholder="Username">
        <input type="password" class="form-control" id="passwordInput" placeholder="Password">
        <button type="button" class="btn btn-primary" id="btnSubmit">Submit</button>
    </div>
</div>
<script>
	$("#passwordInput").keypress(function(event){
	    var ew = event.which;
	    if(48 <= ew && ew <= 57)
	        return true;
	    if(65 <= ew && ew <= 90)
	        return true;
	    if(97 <= ew && ew <= 122)
	        return true;
	    return false;
	});
	$("#email").keypress(function(event){
	    var ew = event.which;
	    if (ew == 46 || ew == 64 || ew == 95)
	    	return true;
	    if(48 <= ew && ew <= 57)
	        return true;
	    if(65 <= ew && ew <= 90)
	        return true;
	    if(97 <= ew && ew <= 122)
	        return true;
	    return false;
	});
	$("#usernameInput").keypress(function(event){
	    var ew = event.which;
	    if(48 <= ew && ew <= 57)
	        return true;
	    if(65 <= ew && ew <= 90)
	        return true;
	    if(97 <= ew && ew <= 122)
	        return true;
	    return false;
	});
	$("#lname").keypress(function(event){
	    var ew = event.which;
	    if(ew == 32)
	        return true;
	    if(48 <= ew && ew <= 57)
	        return true;
	    if(65 <= ew && ew <= 90)
	        return true;
	    if(97 <= ew && ew <= 122)
	        return true;
	    return false;
	});
	$("#fname").keypress(function(event){
	    var ew = event.which;
	    if(ew == 32)
	        return true;
	    if(48 <= ew && ew <= 57)
	        return true;
	    if(65 <= ew && ew <= 90)
	        return true;
	    if(97 <= ew && ew <= 122)
	        return true;
	    return false;
	});
	$("#btnSubmit").click(function(){
		var email = document.getElementById("email").value;
		var fname = document.getElementById("fname").value;
		var lname = document.getElementById("lname").value;
	    var user = document.getElementById("usernameInput").value;
	    var pass = document.getElementById("passwordInput").value;              
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
	});
</script>