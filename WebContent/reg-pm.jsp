<div class = "row top100">
    <div class = "offset-md-4 col-md-4">
        <input type="text" class="form-control" id="storename" placeholder="Store Name">
        <input type="text" class="form-control" id="usernameInput" placeholder="Username">
        <input type="password" class="form-control" id="passwordInput" placeholder="Password">
        <button type="button" class="btn btn-primary" id="btnSubmit">Submit</button>
    </div>
</div>
<script>
	$("#btnSubmit").click(function(){
		var storeName = document.getElementById("storename").value;
	    var user = document.getElementById("usernameInput").value;
	    var pass = document.getElementById("passwordInput").value;              
	    var path = "ProductManagerServlet?username="+user+"&pass="+pass+"&storeName="+storeName+"&param=register";
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