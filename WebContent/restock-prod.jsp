<input type="numer" class="form-control" id="qty" placeholder="Quantity to add">
<button type="button" class="btn btn-primary" id="btnSubmit">Submit</button>
<script src = "javascript/htmlEscape.js"></script>
<script>
	checkIfValidRedirect();
    $("#btnSubmit").click(function(){
    	var qty = document.getElementById("qty").value;
    	if(qty < 0)
    		alert("Invalid input");
    	else
	        $.post("ProductManagerServlet?param=restock&qty="+qty+"&username="+user+"&prodId="+selectedProduct.p_id,function(obj){
	            $("#center").load("man-prods.jsp");
	        });
    });
</script>