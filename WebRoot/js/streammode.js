function ismenuClick0(){
    var that=$(this).next();
    if(that.hasClass("is-visible0")){
        that.removeClass("is-visible0");
    }else {
        that.addClass("is-visible0");
    }
}

function changelist(){
    var list1="";
    var list2="";
    var list3="";
    var list4="";
    $(".contextlist .singlediv").each(function(index,item){
        if(index%4==0){

            list1=list1+item.outerHTML;
        }else if(index%4==1){

            list2=list2+item.outerHTML;
        }else if(index%4==2){

            list3=list3+item.outerHTML;
        }else{

            list4=list4+item.outerHTML;
        }
    });
    //重载数据
    $(".contextlist").empty();
    $(".contextlist").append("<div class='streamdiv'>"+list1+"</div>");
    $(".contextlist").append("<div class='streamdiv'>"+list2+"</div>");
    $(".contextlist").append("<div class='streamdiv'>"+list3+"</div>");
    $(".contextlist").append("<div class='streamdiv'>"+list4+"</div>");
    $(".tmpdiv").each(function(index,item){
        $(item).removeClass("tmpdiv");
    });

    //添加监听
    $(".ismenu").on("click",ismenuClick0);
    $(".onekydown").on("click",onekydownClick);
    $(".regetbtbtn").on("click",regetbt);
    $(".editbtn").on("click",editbtnClick);
    $(".delbtn").on("click",delbtnClick);

    $(".isdownload").on("click",isdownloadclick);

}

changelist();

$("body").on("click",function(){
    $(".mdl-menu-list").removeClass("is-visible0");
});
$(".mdl-card__menu").on("click",function(e){
    e.stopPropagation();
});