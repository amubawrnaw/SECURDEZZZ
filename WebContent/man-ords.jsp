<div class = "row top100">
    <div class = "col-md-12" id = "prodList">
        <div class = "row">
            <div class = "offset-md-4 col-md-4">
                <div class = "hidden">
                    <select id = 'options'>
                        <option >Delivered</option>
                        <option >To Pick Up</option>
                        <option >In Transit</option>
                        <option color = red>Cancelled</option>
                    </select>
                    <button class = "btn btn-primary">Confirm</button>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    var selectedProduct;
    var jsonarr;
    var user = document.cookie.split("=")[1];
    if(user!=null){
        $.get("getOrdersByProductManager?user="+user,function(obj){
            jsonarr = JSON.parse(obj);
            console.log(jsonarr);
            /*)
            for(var i = 0 ; i < jsonarr.length ; i++){
                var jason = jsonarr[i];
                
                var row = $("<div>");   
                row.addClass("row selectable");
                
                row.append("<p class = 'hidden'>" + i + "</p>");
                row.append("<div class = 'col-md-3'><h4>" + jason.name + "</h4></div>");
                row.append("<div class = 'col-md-3'><img height = 50 width = 50 class = 'img img-responsive' src = '" + jason.img_link + "'/></div>");
                row.append("<div class = 'col-md-3'><h4>P" + jason.price + "</h4></div>");
                row.append("<div class = 'col-md-3'><h4>Quantity:" + jason.quantity + "</h4></div>");
                
                $("#prodList").append(row);
            }*/
        });
    }
</script>