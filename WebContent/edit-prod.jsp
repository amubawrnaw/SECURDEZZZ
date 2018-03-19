<input type="text" class="form-control" id="Name" placeholder="Product Name">
<input type="text" class="form-control" id="Image" placeholder="Image Link">
<input type="number" class="form-control" id="Price" placeholder="Price">
<button type="button" class="btn btn-primary" id="btnSubmit">Submit</button>
<script>
    $("#Name").val(selectedProduct.name);
    $("#Image").val(selectedProduct.img_link);
    $("#Price").val(selectedProduct.price);
    
    $("#Name").keypress(function(event){
        var ew = event.which;
        if(ew == 32)
            return true;
        if(48 <= ew && ew <= 57)
            return true;
        if(65 <= ew && ew <= 90)
            return true;
        if(97 <= ew && ew <= 122)
            return true;
        return false;
    });
    $("#Image").keypress(function(event){
        var ew = event.which;
        if(ew == 32)
            return true;
        if(48 <= ew && ew <= 57)
            return true;
        if(65 <= ew && ew <= 90)
            return true;
        if(97 <= ew && ew <= 122)
            return true;
        return false;
    });
    $("#btnSubmit").click(function(){
    	var Name = $("#Name").val();

        var Image = $("#Image").val()

        var Price = $("#Price").val()
        
        var Quantity = $("#Quantity").val();
        
        if(Quantity < 0 || Price < 0)
        	alert("Invalid input");
        else
			$.post("ProductManagerServlet?param=edit&name="+Name+"&price="+Price+"&imgLink="+Image+"&username="+user+"&prodId="+selectedProduct.p_id,function(obj){
           		$("#center").load("man-prods.jsp");
        });
    });
</script>