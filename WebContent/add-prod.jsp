<input type="text" class="form-control" id="Name" placeholder="Product Name">
<input type="text" class="form-control" id="Image" placeholder="Image Link">
<input type="number" class="form-control" id="Price" placeholder="Price">
<input type="number" class="form-control" id="Quantity" placeholder="Quantity">
<button type="button" class="btn btn-primary" id="btnSubmit">Submit</button>
<script>
    $("#btnSubmit").click(function(){
        var Name = htmlEscape($("#Name").val());

        var Image = htmlEscape($("#Image").val());
        
        var Price = htmlEscape($("#Price").val());
        
        var Quantity = $("#Quantity").val();
        if(Quantity < 0 || Price < 0)
        	alert("Invalid input");
        else
	        $.post("ProductManagerServlet?param=add&name="+Name+"&price="+Price+"&qty="+Quantity+"&imgLink="+Image+"&username="+user,function(obj){
	            $("#center").load("man-prods.jsp");
	        });
    });
</script>