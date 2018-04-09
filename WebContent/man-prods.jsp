<div class = "row mt-4">
    <div class = "col-md-12" id = "prodList">
        <div class = "row justify-content-center">
            <div class = "col-md-8">
                <div class = "row">
                    <div class = "col">
                        <button id = "Add" class = "btn btn-primary">Add</button>
                        <button id = "Edit" class = "btn btn-primary">Edit</button>
                        <button id = "Restock" class = "btn btn-primary">Restock</button>
                        <button id = "Delete" class = "btn btn-primary">Delete</button>
                    </div>
                </div>
                <div id = "options" class = "top50">
                </div>
            </div>      
        </div>
    </div>
</div>
<script>
    var select = false;
    $("#prodList").on('click', '.selectable', function(){
        select = true;
        $(this).siblings().removeClass('selected');
        $(this).addClass('selected');
    });
    
    $("#Add").click(function(){
        $("#options").load('add-prod.jsp');
    });
    $("#Edit").click(function(){
        if(select == false){
            alert("Select a product first!");
        }else{
            var index = $("#prodList").find(".selected").children(1).html();
            selectedProduct = jsonarr[index];
            $("#options").load('edit-prod.jsp');
        }
    });
    $("#Restock").click(function(){
        if(select == false){
            alert("Select a product first!");
        }else{
            var index = $("#prodList").find(".selected").children(1).html();
            selectedProduct = jsonarr[index];
            $("#options").load('restock-prod.jsp');
        }
    });
    $("#Delete").click(function(){
        if(select == false){
            alert("Select a product first!");
        }else{
            var index = $("#prodList").find(".selected").children(1).html();
            var id = jsonarr[index].p_id;
            $.post("ProductManagerServlet?param=delete&prodId="+id+"&username="+user,function(obj){
                $("#center").load("man-prods.jsp");
            });
        }
    });
    var selectedProduct;
    var jsonarr;
    var user = document.cookie.split("=")[1];
    if(user!=null){
        $.get("ProductManagerServlet?param=getByPM&username="+user,function(obj){
            jsonarr = JSON.parse(obj);
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
            }
        });
    }
</script>