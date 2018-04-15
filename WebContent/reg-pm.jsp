<div class="row mt-3">
	<div class="col" style="text-align:center;">
		<h1>Product Manager Sign-up</h1>
	</div>
</div>
<!-- pucha -->
<div class = "row mt-2">
    <div class = "offset-md-4 col-md-4">
        <input type="text" class="form-control" id="storename" placeholder="Store Name">
        <input type="text" class="form-control" id="usernameInput" placeholder="Username">
        <input type="password" class="form-control" id="passwordInput" placeholder="Password">
        <label>Password must be atleast 8 characters, with atleast 1 of each: number, lowercase, uppercase, and special character</label>
        <button type="button" class="btn btn-primary float-right" id="btnSubmit">Submit</button>
    </div>
</div>
<script>
	$("#btnSubmit").click(function(){
		var storeName = htmlEscape(document.getElementById("storename").value);
	    var user = htmlEscape(document.getElementById("usernameInput").value);
	    var pass = htmlEscape(document.getElementById("passwordInput").value);              
	    var path = "ProductManagerServlet?username="+user+"&pass="+pass+"&storeName="+storeName+"&param=register";
	    if(passwordRegex.test(pass))
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
	    else
	    	alert("Invalid password");
	});
</script>