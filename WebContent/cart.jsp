<div class = "row mt-4 justify-content-center">
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
			<div class="col-md-3">
				<h3>Total</h3>
			</div>
        </div>
        <div class="row">
       		<div class="col" id = 'items'>
        
       		</div>     
        </div>
        <div class="row no-gutters mt-3">
        	<div class="col-sm-auto">
	        	<button class = "btn btn-primary" id = "delete">Delete</button>
		        <button class = "btn btn-primary" id = "editQty">Edit Quantity</button>
        	</div>
        	<div class="col-sm-auto ml-1 mt-1">
        		<input type = "number" id = "newQty"/>
        	</div>
	        
	        
        </div>
        
        
    </div>
    <div class = "col-md-4">
        <h2 id = "balance"></h2>
        <div class="row mb-5 mt-1">
        	<div class = "col-md-12">
	            <p>Total: </p>
	            <p id = "total" style="font-size:30px; font-weight:bold;"></p>
	        </div>
        </div>
		<div class="row">
			<div class="col">
				<div class="form-group">
					<label for="address">Address (for shipping):</label>
					<textarea class="form-control" id = "address" rows="4"/></textarea>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col">
				<button class = "btn btn-primary btn-block" id = "checkOut">Check Out</button>
			</div>
		</div>
    </div>
    
</div>
<script src = "javascript/htmlEscape.js"></script>
<script>
	checkIfValidRedirect();
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
    $("#editQty").click(function(){
    	var index = $("#items").find(".dselect").children(1).html();
    	console.log(index);
    	var quantity = document.getElementById("newQty").value;
    	console.log("Editing quantity to be " + quantity);
    	if (quantity == 0)
    		alert("Delete the product instead");
    	else{
    		
    		$.post("CartServlet?param=addItemToCart&prodId="+jsonarr[index].pid+"&username="+user+"&qty="+quantity, function(obj){
    			if(obj == 'true')
    				$("#center").load("cart.jsp");
    			else
    				alert("Error");
    		});
    	}
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