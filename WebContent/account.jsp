<body>
<div id = "userDetails">
	
</div>
<button id ="reloadCredits">Reload Credits</button>
</body>
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
	
	$("#reloadCredits").click(function(){
		$("#center").load("reloadCredits.jsp");
	});
</script>