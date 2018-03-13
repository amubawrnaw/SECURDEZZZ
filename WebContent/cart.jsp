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