<body>
<div id = "creditDetails">
</div>
</body>
<script>
	var username = document.cookie.split("=")[1];
	$.get("UserServlet?param=getCredits&user="+username, function(obj){
		var credits = obj;
		console.log(credits);
		var row = $("<div>");
		row.append("<p>Current Credits: " + credits + "</p>");
		row.append("<p>Reload By? <input type = 'number' id = 'numCredits'/></p>");
		row.append("<button id = 'reload'>Reload</button>")
		$("#creditDetails").append(row);
	});
	$("body").on('click', '#reload', function(){
		var amount = document.getElementById("numCredits").value;
		$.post("UserServlet?param=reload&user="+username+"&amount="+amount, function(obj){
			$("#center").load("account.jsp");
		})
	});
</script>