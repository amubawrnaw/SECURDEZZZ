<!-- <body>
<div id = "userDetails">
</div>
<div id = "orderDetails">
</div>
<button id ="reloadCredits">Reload Credits</button>
</body>
 -->
 <div class="row mt-4">
 	<div class="col" style="text-align:center;">
 		<h2>Account Page</h2>
 	</div>
 </div>
 <div class="row justify-content-center">
 	<div class="col-md-4 px-2 py-2">
 		<div class="row">
 			<div class="col">
 				<div id="userDetails">
 				</div>
 			</div>	
 		</div>
 		<div class="row">
	 		<div class="col">
	 			<div id="orderDetails">
	 			</div>
	 		</div>	
 		</div>
 		<div class="row">
 			<div class="col">
 				<button id="reloadCredits" class="btn btn-primary btn-block">Reload Credits</button>
 			</div>	
 		</div>
 	</div>
 </div>

<script>
	var username = document.cookie.split("=")[1];
	$.get("UserServlet?param=user&user="+username, function(obj){
		if(obj != null)
		{
			var jason = JSON.parse(obj);
			console.log(jason);
			var row = $("<div>");
			row.append("<p>Username: " + jason.username + "</p>");
			row.append("<p>Credits: " + jason.credits + "</p>");
			row.append("<p>Name: " + jason.fname + " " + jason.lname + "</p>");
			$("#userDetails").append(row);
		}
	});
	
	$.get("getOrderByUserId?username=" + username, function(obj){
		console.log(obj);
	});
	
	
	$("#reloadCredits").click(function(){
		$("#center").load("reloadCredits.jsp");
	});
</script>