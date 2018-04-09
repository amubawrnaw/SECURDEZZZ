<input type="text" class="form-control" id="Name" placeholder="Product Name">
<input type="text" class="form-control" id="Image" placeholder="Image Link">
<input type="number" class="form-control" id="Price" placeholder="Price">
<button type="button" class="btn btn-primary" id="btnSubmit">Submit</button>
<script>
    $("#Name").val(selectedProduct.name);
    $("#Image").val(selectedProduct.img_link);
    $("#Price").val(selectedProduct.price);
    $("#btnSubmit").click(function(){
    	var Name = htmlEscape($("#Name").val());

        var Image = htmlEscape($("#Image").val());

        var Price = htmlEscape($("#Price").val());
        
        var Quantity = $("#Quantity").val();
        
        if(Quantity < 0 || Price < 0)
        	alert("Invalid input");
        else
			$.post("ProductManagerServlet?param=edit&name="+Name+"&price="+Price+"&imgLink="+Image+"&username="+user+"&prodId="+selectedProduct.p_id,function(obj){
           		$("#center").load("man-prods.jsp");
        });
    });
</script>