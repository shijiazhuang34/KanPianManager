/**
 * Created by L on 2015/9/10.
 */
if(isedit) {
    $("#clearsrc").text("返回");
}

function readFile(result){
    var file = this.files[0];
    if(!/image\/\w+/.test(file.type)){
        alert("文件必须为图片！");
        input.value='';
        return false;
    }
    var reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = function(e){
        var result = $("#thedialog #result")[0];
        result.innerHTML = '<img src="'+this.result+'" alt="" />';
    }
}

function postexl(){
    var exlval=$("#thedialog #exlFile").val();
    if(!!exlval){
        var fd = new FormData($("#thedialog #postexl")[0]);
        $.ajax({
            url: "manager/uploadExl",
            type: "POST",
            data: fd,
            processData: false, // 告诉jQuery不要去处理发送的数据
            contentType: false, // 告诉jQuery不要去设置Content-Type请求头
            success:function(data){
                initDialog(data+"<br>");
            }
        });
    }else{
        alert("上传文件不能为空");
    }
}

function postimg(){
    var imgval=$("#thedialog #imgFile").val();
    var typedir=$("#typevideo").val();
    if(!!typedir){
        if(!!imgval){
            var fd = new FormData($("#thedialog #postimg")[0]);
            fd.append("typedir", typedir);
            var oldpath="";
            if(isedit) {
                oldpath=$("#imgpath").val();
            }
            $.ajax({
                url: "manager/postimgtor?oldpath="+oldpath,
                type: "POST",
                data: fd,
                processData: false, // 告诉jQuery不要去处理发送的数据
                contentType: false, // 告诉jQuery不要去设置Content-Type请求头
                success:function(data){
                    $("#imgpath").val(data);
                    delDialog();
                }
            });
        }else{
            alert("上传图片不能为空");
        }
    }else{
        alert("必须先选择类型");
    }
}

function posttor(){
    var torval=$("#thedialog #torFile").val();
    var typedir=$("#typevideo").val();
    if(!!typedir){
        if(!!torval){
            var fd = new FormData($("#thedialog #posttor")[0]);
            fd.append("typedir", typedir);
            $.ajax({
                url: "manager/postimgtor",
                type: "POST",
                data: fd,
                processData: false,	// 告诉jQuery不要去处理发送的数据
                contentType: false,	// 告诉jQuery不要去设置Content-Type请求头
                success:function(data){
                    $("#torpath").val(data);
                    delDialog();
                }
            });
        }else{
            alert("上传种子不能为空");
        }
    }else{
        alert("必须先选择类型");
    }
}

$("#upimg").on("click",function(){
    var uploadimg=$(".uploadimg").html();
    initDialog(uploadimg);
    $("#thedialog").css("top","50px");

    var result = $("#thedialog #result")[0];
    var input = $("#thedialog #imgFile");
    if(typeof FileReader==='undefined'){
        result.innerHTML = "抱歉，你的浏览器不支持 FileReader";
        input.attr('disabled','disabled');
    }else{
        input.on('change',readFile);
    }
})

$("#uptor").on("click",function(){
    var uploadtor=$(".uploadtor").html();
    initDialog(uploadtor);
})

$("#importExlBtn").on("click",function(){
    var importExlDiv=$(".importExlDiv").html();
    initDialog(importExlDiv);
})

function goback(){
    var refer = document.referrer;
    window.location.href = refer;
}

$("#clearsrc").on("click",function(){
    if(isedit){
        goback();
    }else{
        $(".formparam").val("");
    }
})

function gettags(){
    var tags= new Array();
    $("#tags span").each(function () {
        tags.push($(this).attr("title"))
    })
    return tags.join(",");
}

$("#savesrc").on("click",function(){
    var subflag=1;
    $(".formparam").each(function(){
        if($(this).val()==""){
            subflag=0;
        }
    })
    if(subflag==0){
        alert("表单不能有空值");
    }else{
        var isurl="";
        if(!isedit){
            isurl="manager/uploadform";
        }else{
            isurl="manager/updatefrom";
        }
        var tags=gettags();
        if(tags.length>0){
            isurl=isurl+"?tagslist="+tags;
        }
        $.ajax({
        url: isurl,
        type: "POST",
            data: $("#postsrc").serialize(),
            success:function(data){
                if(!isedit) {
                    if (data == "ok") {
                        alert("保存成功");
                        $(".formparam").val("");
                    }
                    else{
                        alert("保存失败");
                    }
                }
                if(isedit) {
                    if (!!+data) {
                        alert("保存成功");
                        goback();
                    }else{
                        alert("保存失败");
                    }
                }
        }
    });
}
})


$("#addtag").on("click",function(){
    var input='<div class="inputdiv mdl-textfield mdl-js-textfield is-upgraded is-dirty" data-upgraded=",MaterialTextfield">'+
        '<input class="mdl-textfield__input formparam" type="text"   id="addtagval" />'+
        '<span class="spanbtn" onclick="addthistag()">确定</span></div>';
    initDialog(input);
})

function removetag(obj){
    $(obj).parent().remove();
}

function addthistag(){
    var tagval=$("#addtagval").val();
    delDialog();var tagval=$("#addtagval").val();
    delDialog();
    var span='<span title="'+tagval+'">'+tagval+'<i onclick="removetag(this)">x</i></span>';
    $("#tags").append(span);
}


