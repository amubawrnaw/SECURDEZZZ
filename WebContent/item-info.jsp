<div class = "row">
    <div class = "col-md-7">
        <div id = "imgDiv">
        </div>
    </div>
    <div class = "col-md-5">
        <h1 id = 'prodName'></h1>
        </br>
        <h4 id = 'price'></h3>
        <h4 id = 'qty'></h3>
        
        <div class = "hidden top100">
            <h3>Add To Cart</h3>
            <input type = 'number' placeholder="Quantity" id = "purchaseQty">
            <button id = 'toCart' class = "btn btn-primary">Add to Cart</button>
        </div>
    </div>
</div>
<script>
    $("#toCart").click(function(){
        $.post("CartServlet?param=addItemToCart&username="+user+"&prodId="+itemId+"&qty="+$("#purchaseQty").val(),function(obj){
            if(obj == "true"){
                alert("Item added to cart!");
                $("#center").load('item-info.jsp');
            }else{
                alert("there was an error in adding the item");
            }
            
        });
    });
    
    $.get("ProductServlet?param=getById&id="+itemId, function(obj){
        var jason = JSON.parse(obj)[0];
        $("#imgDiv").append("<img height = 100% width = 100% class = 'img img-responsive' src = '" + jason.img_link + "'/>");
        
        $("#prodName").html(jason.name);
        $("#price").html("P" + jason.price);
        $("#qty").html("Available: " + jason.quantity);
    });
    
    var user = document.cookie.split("=")[1];
    if(user!=null){
        $.get("UserServlet?user=" + name_or_null + "&param=user", function(obj){
            var person = JSON.parse(obj);
            if (person.fname != null){
                $('.hidden').css("display","block");
            }
        });
    }
    
</script>