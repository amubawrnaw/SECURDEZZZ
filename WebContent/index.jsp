<html>
    <!-- stuff i need -->
    <link href="frameworks/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/custom.css" rel="stylesheet">
    <script src="frameworks/jquery/jquery.min.js"></script>
    <script src="frameworks/bootstrap/js/bootstrap.bundle.min.js"></script>
    <div id = "headerContainer">
        <jsp:include page="header-guest.jsp"></jsp:include>
    </div>
    <div id = "center">
        
    </div>
    
    
    <script>
        var prodid = window.location.href.split("=")[1];
        var name_or_null = document.cookie.split("=")[1];
        if(prodid != null){
            $("#center").load("item-info.jsp");
        }
        if (name_or_null != null){
            $.get("UserServlet?user=" + name_or_null + "&param=user", function(obj){
                var person = JSON.parse(obj);
                console.log(person);
                if (person.fname != null){
                    $('#headerContainer').load("header-user.jsp");
                }else if(person.storeName != null){
                    $('#headerContainer').load("header-pm.jsp");
                }
                });
            
        }
        $("#center").load("default.jsp");
        
    </script>
</html>