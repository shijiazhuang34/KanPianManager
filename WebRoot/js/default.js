$("body").on("click",function(){
    $("#search").next().removeClass("mdl-textfield__label");
    $("#searchmin").next().removeClass("mdl-textfield__label");
})

$("#search").on("focus",function(){
    $(this).next().addClass("mdl-textfield__label");
})

$("#searchmin").on("focus",function(){
    $(this).next().addClass("mdl-textfield__label");
})

$(".search").on("click",function(e){
    e.stopPropagation();
})

$("body").on("click",function(){
    $(".inputdiv").removeClass("is-focused");
})

$(".inputdiv input").on("click",function(e){
    e.stopPropagation();
    $(".inputdiv").removeClass("is-focused");
    $(this).parent().addClass("is-focused");
})

$(".searchtag").on("click",function(){
    window.location.href="search?sp="+$(this).attr("title");
})
$(".searchtime").on("click",function(){
    window.location.href="search?time="+$(this).attr("time");
})
$("#issearch").on("click",function(){
    window.location.href="search?sp="+$("#search").val();
})

$("#search").keypress(function (e) {
    var thisid=$(this).attr("id");
    var key = e.which;
    if (key == 13&&thisid=="search") {
        window.location.href="search?sp="+$("#search").val();
    }
});

$("#isserchmin").on("click",function(){
    window.location.href="search?sp="+$("#searchmin").val();
})



function initDialog(innerhtml){
    if($(".gbdialog").length==0) {
        var dightml = '<div class="gbdialog"> <div id="masklayer"></div><div class="mdl-shadow--3dp" id="thedialog"><span id="delalog">x</span><div id="thedialogcontext"></div></div></div>';
        $("body").append(dightml).css("overflow","hidden");
        if (innerhtml != "") {
            $("#thedialogcontext").append(innerhtml);
        }
        $("#masklayer").on("click", delDialog);
        $("#delalog").on("click", delDialog);
    }else{
        if (innerhtml != "") {
            $("#thedialogcontext").html(innerhtml);
        }
    }
}
function delDialog(){
    $("#masklayer").off("click");
    $("#delalog").off("click");
    $(".gbdialog").remove();
    $("body").css("overflow","auto");
}

function initpage(){
    if($(".singlediv").length>0){
        var width=+$(".singlediv").css("width").replace("px","");
        var height=width*0.68;
        $(".singlediv img").css("width",width+"px");
        $(".singlediv img").css("min-height",height+"px");
    }
    if(!ispc()){
        jQuery(function($) {
            $(document).ready( function() {
                $('.searchmin').stickUp();
            });
        });
    }
}
initpage();