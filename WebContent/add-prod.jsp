<input type="text" class="form-control" id="Name" placeholder="Product Name">
<input type="text" class="form-control" id="Image" placeholder="Image Link">
<input type="number" class="form-control" id="Price" placeholder="Price">
<input type="number" class="form-control" id="Quantity" placeholder="Quantity">
<button type="button" class="btn btn-primary" id="btnSubmit">Submit</button>
<script>
    $("#btnSubmit").click(function(){
        var Name = $("#Name").val();
        var Image = $("#Image").val();
        var Price = $("#Price").val();
        var Quantity = $("#Quantity").val();
        
        $.post("ProductManagerServlet?param=add&name="+Name+"&price="+Price+"&qty="+Quantity+"&imgLink="+Image+"&username="+user,function(obj){
            $("#center").load("man-prods.jsp");
        });
    });
</script>