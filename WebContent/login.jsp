<div class="row justify-content-center mt-5">
	<div class="col-md-4">
		<div class="card">
			<div class="card-body">		
				<div class="row mt-3">
					<div class="col-12" style="text-align:center;">
						<h2>Login</h2>
					</div>
				</div>
				<div class = "row mt-2">
				    <div class = "col-md-12">
				        <input type="text" class="form-control" id="usernameInput" placeholder="Username">
				        <input type="password" class="form-control" id="passwordInput" placeholder="Password">
				        <button type="button" class="btn btn-primary btn-block" id="btnSubmit">Submit</button>
				    </div>
				</div>
			</div>
		</div>
	</div>
</div>

<script src = "javascript/htmlEscape.js"></script>
<script>
	checkIfValidRedirect();
    $("#btnSubmit").click(function(){
        console.log("Hehe");
        var chec = 'false';
        var user = htmlEscape(document.getElementById("usernameInput").value);
        var pass = htmlEscape(document.getElementById("passwordInput").value);
        var path = "UserServlet?user="+user+"&pass="+pass+"&param=login";

        $.get(path,function(obj){
            console.log(obj);
            if(obj == 'true'){
                console.log(document.cookie);
                window.location.reload();
                //close();

            }else{
                alert("Invalid username / pass");
            }
        });
    });
</script>