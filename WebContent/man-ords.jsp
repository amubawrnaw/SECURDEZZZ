<div class = "row top100">
    <div class = "col-md-12" id = "prodList">
        <div class = "row">
            <div class = "offset-md-4 col-md-4">
                <div>
                    <select id = 'options'>
                        <option >Delivered</option>
                        <option >To Pick Up</option>
                        <option >In Transit</option>
                        <option color = red>Cancelled</option>
                    </select>
                    <button class = "btn btn-primary" id = "confirm">Confirm</button>
                </div>
            </div>
        </div>
        <div class = "row">
            <div class = "col-md-2">
                <h3>Order id</h3>
            </div>
            <div class = "col-md-3">
                <h3>Name</h3>
            </div>
            <div class = "col-md-2">
                <h3>Price</h3>
            </div>
            <div class = "col-md-2">
                <h3>Qty</h3>
            </div>
            <div class = "col-md-3">
                <h3>Status</h3>
            </div>
        </div>
    </div>
</div>
<script src = "javascript/htmlEscape.js"></script>
<script>
	checkIfValidRedirect();
    var selectedProduct;
    var jsonarr;
    var user = document.cookie.split("=")[1];
    if(user!=null){
        $.get("getOrdersByProductManager?user="+user,function(obj){
            jsonarr = JSON.parse(obj);
            console.log(jsonarr);
            
            for(var i = 0 ; i < jsonarr.length ; i++){
                var jason = jsonarr[i];
                
                var row = $("<div>");   
                row.addClass("row selectable");
                
                row.append("<p class = 'hidden'>" + i + "</p>");
                row.append("<div class = 'col-md-2'><h4>"+jason.order_id+"</h4></div>");
                row.append("<div class = 'col-md-3'><h4>"+jason.product.name+"</h4></div>");
                row.append("<div class = 'col-md-2'><h4>"+jason.product.price+"</h4></div>");
                row.append("<div class = 'col-md-2'><h4>"+jason.qty+"</h4></div>");
                row.append("<div class = 'col-md-3'><h4>"+jason.status[jason.status.length-1].status+"</h4></div>");
                
                $("#prodList").append(row);
            }
        });
    }
    $("#confirm").click(function(){
        if(select == false){
            alert("Select an order first");
        }else{
            var index = $(".selected").children(1).html();
            var detail_id = jsonarr[index].detail_id;
            var status = $("#options option:selected").text();
            $.post("addOrderStatus?detail_id="+detail_id+"&status="+status,function(){
                alert("updated order!");
                $("#center").load("man-ords.jsp");
            });
        }
    });
    
    var select = false;
    $("#prodList").on('click', '.selectable', function(){
        select = true;
        
        $(this).siblings().removeClass('selected');
        $(this).addClass('selected');
    });
</script>