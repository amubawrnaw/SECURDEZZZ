<input type="numer" class="form-control" id="qty" placeholder="Quantity to add">
<button type="button" class="btn btn-primary" id="btnSubmit">Submit</button>
<script>
    $("#btnSubmit").click(function(){
        $.post("ProductManagerServlet?param=restock&qty="+qty+"&username="+user+"&prodId="+selectedProduct.p_id,function(obj){
            $("#center").load("man-prods.jsp");
        });
    });
</script>