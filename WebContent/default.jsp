<div class = "row">
    <div class = "offset-md-3 col-md-6 top50">
        <input type = "text" class = "form-control" placeholder = "Search Products" class = "full" id = "search">
        <button id = "searchBtn" class = "btn btn-primary">Search</button>
    </div>
</div>
<div class = "row top50 ">
    <div class = "col-lg-12" id = "products">
        
    </div>
</div>
<script>
    var itemId;
    $.get("ProductServlet?param=all", function(obj){
        console.log(obj);
         //imageCount = obj.split(",,,").length - 1;
         addPhotos(obj);
     });
    $("#searchBtn").click(function(){
        var search = $("#search").val();
        $.get("ProductServlet?param=search&searchString="+search, function(obj){
            addPhotos(obj);
        });
    });
    $("#products").on('click','#clickable',function(){
        itemId = $(this).siblings(2).children().first().html();
        $("#center").load("item-info.jsp");
    });

     function addPhotos(obj){
         if(obj!=null){
             var jsonarr = JSON.parse(obj);	
             var x = jsonarr.length;
             $("#products").empty();
             if(obj!=""){
                 for (var i = 0; i < x; i++)
                 {
                     var row;
                     if(i%4 == 0){
                         row = $("<div>");
                         $(row).addClass("row");
                     }
                     var jsonobj = jsonarr[i];

                     var photo = $("<div>");
                     $(photo).addClass("photos");
                     $(photo).addClass("col-md-3");
                     $(photo).prepend("<img id = 'clickable' height = 250 width = 250 class = 'img img-responsive' src = '" + jsonobj.img_link + "'/>");
                     var imgid = $("<div class = 'hidden'>");
                     $(imgid).addClass("id");
                     $((imgid).html("<p>" + jsonobj.p_id + "</p>"));  
                     $(photo).append(imgid);
                     var imgName = $("<h3 class = center-text>");
                     imgName.html(jsonobj.name);
                     photo.append("</br>");
                     photo.append(imgName);
                     
                     row.append(photo);
                     if(i%4 == 0 || i == x-1){
                         $("#products").append(row);
                     }

                 }
             }

         }
     }

</script>

