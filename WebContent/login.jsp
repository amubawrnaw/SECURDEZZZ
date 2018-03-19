<div class = "row top100">
    <div class = "offset-md-4 col-md-4">
        <input type="text" class="form-control" id="usernameInput" placeholder="Username">
        <input type="password" class="form-control" id="passwordInput" placeholder="Password">
        <button type="button" class="btn btn-primary" id="btnSubmit">Submit</button>
    </div>
</div>
<script>
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
    $("#btnSubmit").click(function(){
        console.log("Hehe");
        var chec = 'false';
        var user = document.getElementById("usernameInput").value;
        var pass = document.getElementById("passwordInput").value;
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