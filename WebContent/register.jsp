<div class="container">
	<div class="row">
		<div class="col-md">
			<div class="row" style="margin-top:32px; margin-bottom:32px;">
				<div class="col-12">
					<h4 class="text-center">Sign-Up</h4>
				</div>			
			</div>
			<div class="row justify-content-around">
				<!-- User Sign-Up -->
				<div class="col-md-5">
					<div class="card text-white bg-dark">
						<div class="card-title mtb-2">
							<h3 class="text-center">Register as User</h3>			
						</div>
						<div class="card-body">
							<input type="text" class="form-control" id="fname" placeholder="First Name">
					        <input type="text" class="form-control" id="lname" placeholder="Last Name">
					        <input type="text" class="form-control" id="usernameInput" placeholder="Username">
					        <input type="password" class="form-control" id="passwordInput" placeholder="Password">
					        <button type="button" class="btn btn-success btn-block" id="btnSubmit">Register</button>
						</div>
					</div>
				</div>
				<!-- Product Manager Sign-Up -->
				<div class="col-md-5">
					<div class="card text-white bg-dark">
						<div class="card-title mtb-2">
							<h3 class="text-center">Register as Product Manager</h3>	
						</div>
						<div class="card-body">
							<input type="text" class="form-control" id="storename" placeholder="Store Name">
					        <input type="text" class="form-control" id="usernamePMInput" placeholder="Username">
					        <input type="password" class="form-control" id="passwordPMInput" placeholder="Password">
					        <button type="button" class="btn btn-success btn-block" id="btnPMSubmit">Register</button>
						</div>
					</div>
				</div>
			</div>
		</div>	
	</div>
</div>

<script>
$("#btnPMSubmit").click(function(){
	var storeName = document.getElementById("storename").value;
    var user = document.getElementById("usernamePMInput").value;
    var pass = document.getElementById("passwordPMInput").value;              
    var path = "ProductManagerServlet?username="+user+"&pass="+pass+"&storeName="+storeName+"&param=register";
    $.post(path, function(obj){
        console.log(obj);
        if(obj == "true"){
        	alert("Register Success!");
            $("#center").load("login.jsp");
        }else{
            alert("Username already taken!");
        }
    });
});

$("#btnSubmit").click(function(){
	var fname = document.getElementById("fname").value;
	var lname = document.getElementById("lname").value;
    var user = document.getElementById("usernameInput").value;
    var pass = document.getElementById("passwordInput").value;              
    var path = "UserServlet?user="+user+"&pass="+pass+"&fName="+fname+"&lName="+lname+"&param=register";
    $.post(path, function(obj){
        console.log(obj);
        if(obj == "true"){
            alert("Register Success!");
            $("#center").load("login.jsp");
        }else{
            alert("Username already taken!");
        }
    });
});
</script>