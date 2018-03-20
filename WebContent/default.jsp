<div class="container">

      <div class="row">

        <div class="col-lg-3">

          <h1 class="my-4">XD Shop</h1>
          <form>
          	<div class="form-group">
          		<label for="searchInput">Search</label>
          		<input type="text" id="searchInput" class="form-control full" placeholder="Search Products" id="search">
          	</div>
          	<button type="submit" class="btn btn-primary" id="searchBtn">Search</button>
          </form>

        </div>
        <!-- /.col-lg-3 -->
		<div class="col-lg-9">

          <div id="carouselExampleIndicators" class="carousel slide my-4" data-ride="carousel">
            <ol class="carousel-indicators">
              <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
              <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
              <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
            </ol>
            <div class="carousel-inner" role="listbox">
              <div class="carousel-item active">
                <img class="d-block img-fluid" src="http://placehold.it/900x350" alt="First slide">
              </div>
              <div class="carousel-item">
                <img class="d-block img-fluid" src="http://placehold.it/900x350" alt="Second slide">
              </div>
              <div class="carousel-item">
                <img class="d-block img-fluid" src="http://placehold.it/900x350" alt="Third slide">
              </div>
            </div>
            <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
              <span class="carousel-control-prev-icon" aria-hidden="true"></span>
              <span class="sr-only">Previous</span>
            </a>
            <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
              <span class="carousel-control-next-icon" aria-hidden="true"></span>
              <span class="sr-only">Next</span>
            </a>
          </div>

          <div class="row" id="products">
                      <!-- <a href="#"><img class="card-img-top" src="http://placehold.it/700x400" alt=""></a>
                       <div class="card-body">
                         <h4 class="card-title">
                           <a href="#">Item One</a>
                         </h4>
                         <h5>$24.99</h5>
                         <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Amet numquam aspernatur!</p>
                       </div>
                       <div class="card-footer">
                         <small class="text-muted">&#9733; &#9733; &#9733; &#9733; &#9734;</small>
                       </div> -->
          </div>
          <!-- /.row -->

        </div>
        <!-- /.col-lg-9 -->
        
      </div>
      <!-- /.row -->

	</div>
	<!-- /.container -->
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
                         row = $("<div>");
                         $(row).addClass("row");
                     var col;
                         col = $("<div>");
                         $(col).addClass("col-lg-4");
                         $(col).addClass("col-md-6");
                         $(col).addClass("mb-4");
                     row.append(col)
                     
                     var jsonobj = jsonarr[i];
                     
                     var card;
                         card = $("<div>");
                         $(card).addClass("card"); 
                         $(card).prepend("<img id='clickable' class='card-img-top' src='" + jsonobj.img_link+ "'/>");
                     
                     col.append(card)
                     
                     var cardBody;
                     	cardBody = $("<div>");
                     	$(cardBody).addClass("card-body");
                     	var imgID = $("<div class = 'hidden'>");
                        $(imgID).addClass("id");
                        $((imgID).html("<p>" + jsonobj.p_id + "</p>"));  
                        $(cardBody).append(imgID);
                        var imgName = $("<h4>");
                        imgName.html(jsonobj.name);
                        $(imgName).addClass("card-title");
                        cardBody.append(imgName)
                        
                     card.append(cardBody)
                     	
                     
                     /*
                     var photo = $("<div>");
                     $(photo).addClass("photos");
                     $(photo).addClass("col-md");
                     var imgid = $("<div class = 'hidden'>");
                     $(imgid).addClass("id");
                     $((imgid).html("<p>" + jsonobj.p_id + "</p>"));  
                     $(photo).append(imgid);
                     var imgName = $("<h3>");
                     imgName.html(jsonobj.name);
                     photo.append("</br>");
                     photo.append(imgName);
                     
                     card.append(photo);
                     */

                         $("#products").append(col);
                    

                 }
             }

         }
     }

</script>

