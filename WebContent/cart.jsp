<div class = "row top100">
    <div class = "col-md-8">
        <div class = "row">
            <div class = "col-md-3">
                <h3>Name</h3>
            </div>
            <div class = "col-md-3">
                <h3>Price</h3>
            </div>
            <div class = "col-md-3">
                <h3>Quantity</h3>
            </div>
            <div class = "col-md-3">
                <h3>Total</h3>
            </div>
        </div>
        <div id = 'items'>
        
        </div>
        <button class = "btn btn-primary top25" id = "delete">Delete</button>
    </div>
    <div class = "col-md-4">
        <h2 id = "balance"></h2>
        <h2 class = "top100" id = "total"></h2>
        <button class = "btn btn-primary" id = "checkOut">Check Out</button>
        <p>	Address (for shipping): <textarea id = "address"style="height:200px"/></textarea></p>
    </div>
    
</div>
<script>
	jQuery.ajaxSetup({async:false});
    var dselect = false;
    var jsonarr;
    var total = 0;
    var user = document.cookie.split("=")[1];
    $("#items").on('click','.selectable',function(){
        $(this).siblings().removeClass('dselect');
        $(this).addClass('dselect');
    });
    
    $("#checkOut").click(function(){
    	var address = document.getElementById("address").value;
    	if (address != '')
	        $.post("CartServlet?param=checkOutCart&username="+user+"&address="+address, function(obj){
                console.log(obj);
	        	if(obj == 'true'){
	        		console.log("Cart checked out");
	        		$("#center").load("index.jsp");
	        	}       		
	        	else{
	        		alert("Insufficient Funds, please reload");
	        		$("#center").load("reloadCredits.jsp");
	        	}	
	        });
    	else
    		alert("No address given");
    });
    $("#delete").click(function(){
        var index = $("#items").find(".dselect").children(1).html();
        console.log(index);
        $.post("CartServlet?param=removeFromCart&username="+user+"&prodId="+jsonarr[index].pid,function(obj){
        	console.log("Deleting object");
            $("#center").load("cart.jsp");
        });
    });
    if(user!=null){
        $.get("CartServlet?username="+user,function(obj){
            jsonarr = JSON.parse(obj);

            for(var i = 0 ; i < jsonarr.length ; i++){
                var jason = jsonarr[i];
				//alert(i + " " + jsonarr.length);
                $.get("ProductServlet?param=getById&id="+jason.pid, function(obj){
                    addProductDetails(jason, i, obj);
                });
            }
        });
        $.get("UserServlet?param=user&user="+user,function(obj){
            var jason = JSON.parse(obj);
            $("#balance").html("Balance: P"+jason.credits);
        });
        function addProductDetails(jason, i, obj){
        	var prod = JSON.parse(obj)[0];
            var row = $("<div>");
            row.addClass("row selectable");
            row.append("<p class = 'hidden'>" + i + "</p>");
            row.append("<div class = 'col-md-3'>"+prod.name+"</div>");
            row.append("<div class = 'col-md-3'>"+prod.price+"</div>");
            row.append("<div class = 'col-md-3'>"+jason.qty+"</div>");
            row.append("<div class = 'col-md-3'>"+(prod.price * jason.qty)+"</div>");
            total+=prod.price * jason.qty;
            $("#total").html("P"+total);
            
            $("#items").append(row);
        }
    }
    
</script>