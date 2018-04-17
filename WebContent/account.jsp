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
 	<div class="col-md-12 px-2 py-2">
 		<div class="row">
 			<div class="col-md-4">
 				<div class="row">
 					<div class="col">
 						<div id="userDetails">
 						</div>
 					</div>
 				</div>
 				<div class="row mt-3">
 					<div class="col">
 						<button id="reloadCredits" class="btn btn-primary btn-block">Reload Credits</button>
 					</div>
 				</div>
 			</div>	
 			<div class="col-md-6">
	 			<div id="orderDetails" class="row">
	 			</div>
	 		</div>	
 		</div>
 	</div>
 </div>
<script src = "javascript/htmlEscape.js"></script>
<script>
	checkIfValidRedirect();
	var username = document.cookie.split("=")[1];
	$.get("UserServlet?param=user&user="+username, function(obj){
		if(obj != null)
		{
			//For Account Details
			var jason = JSON.parse(obj);
			console.log(jason);
			var row = $("<div>");
			
			var listGroup = $("<ul>");
			$(listGroup).addClass("list-group");
			
			var username = $("<li>");
			$(username).addClass("list-group-item");
			$(username).append("Username: " + jason.username);
			
			var credits = $("<li>");
			$(credits).addClass("list-group-item");
			$(credits).append("Credits: " + jason.credits);
			
			var fullname = $("<li>");
			$(fullname).addClass("list-group-item");
			$(fullname).append("Name: " + jason.fname + " " + jason.lname);
			
			$(listGroup).append(username);
			$(listGroup).append(credits);
			$(listGroup).append(fullname);
			
			$(row).append(listGroup);
			
			$("#userDetails").append(row);

		}
	});
	
	$.get("getOrderByUserId?username=" + username, function(obj){
		console.log("I hate life")
		console.log(obj);
		
		
		if(obj != null)
		{
			
			//For Orders
			var jason = JSON.parse(obj);
			console.log("I miss the old kanye")
			console.log(jason);
			
			var orders = [];
			
			//Fill order array
			for(i = 0; i < jason.length; i++){
				orders.push(jason[i]);
			}
			
			var col = $("<div>");
			$(col).addClass("col");
			
			for(x = 0; x < orders.length; x++){
				var prodRow = $("<div>");
				$(prodRow).addClass("row");
				
				
			}
			
			
			
			
		}
		
		
	});
	
	
	$("#reloadCredits").click(function(){
		$("#center").load("reloadCredits.jsp");
	});
</script>