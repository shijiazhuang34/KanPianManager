function changelist(){
    var list1="";
    var list2="";
    var list3="";
    $(".contextlist .singlediv").each(function(index,item){
        //item=$(item);
        if(index%3==1){
            //list1.push(item);
            list1=list1+item.outerHTML;
        }else if(index%3==2){
            //list2.push(item);
            list2=list2+item.outerHTML;
        }else{
            //list3.push(item);
            list3=list3+item.outerHTML;
        }
    });

    $(".contextlist").empty();
    $(".contextlist").append("<div class='streamdiv'>"+list1+"</div>");
    $(".contextlist").append("<div class='streamdiv'>"+list2+"</div>");
    $(".contextlist").append("<div class='streamdiv'>"+list3+"</div>");
}

changelist();