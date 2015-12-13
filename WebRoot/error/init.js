/**
 * Created by L on 2015/9/13.
 */
$("body").on("click",function(){
    $(".inputdiv").removeClass("is-focused");
})

$(".inputdiv input").on("click",function(e){
    e.stopPropagation();
    $(".inputdiv").removeClass("is-focused");
    $(this).parent().addClass("is-focused");
})