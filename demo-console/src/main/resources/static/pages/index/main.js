var AppMain = function($) {

    
    function logout(){
        $("#logout_link").on("click",function(event){
           event.preventDefault();
           $("<form />", {
               action : contextPath + "/logout",
               method : "post"
           }).appendTo(document.body)[0].submit();
        });
    }
    
    
    
    
    return {

        init : function() {
            logout();
        }

    }

}(jQuery);

jQuery(document).ready(function(){
    AppMain.init();
});