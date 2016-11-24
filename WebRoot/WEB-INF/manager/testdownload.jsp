<%@ page language="java"  pageEncoding="UTF-8"%>
<%@include file="taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
	<meta charset="UTF-8">
	<title>下载测试页面</title>
	<script type="text/javascript" src="js/jq1.11.1_min.js"></script>
	<style type="text/css">
	.data #datehead tr td{
		text-align: center;
		font-weight: bold;
		background: #aaa;
		color: #fff;
		padding: 5px 0;
	}
	.data #datatable tr td{
		border: 1px solid #ddd;
		text-align: center;
	}
	.filenames{
		max-width: 300px;
		overflow: hidden;
	}
	</style>
</head>
<body>
	<form id="getform">
	下载地址:<input name="url" type="text">
	开启线程数:
	<select name="threadnum">
		<option>1</option>
		<option>3</option>
		<option>5</option>
		<option>7</option>
		<option>9</option>
		<option>10</option>
	</select>
	重试次数:
	<select name="trynum">
		<option>20</option>
		<option>30</option>
		<option>50</option>
		<option>100</option>
		<option>150</option>
	</select>
	<input type="button" id="getbig" value="提交">
	</form><hr>
	<table class="data" cellspacing="0">
		<thead id="datehead">
			<tr>
			<td width="100">操作</td>
			<td width="60">状态</td>
			<td width="300">文件名</td>
			<td width="60">完成度</td>
			<td width="60">大小</td>
			<td width="240">分数</td>
			<td width="100">下载速度</td>
			<td width="60">重试</td>
			<td width="100">运行时间</td>
			<td width="100">剩余时间</td>
			</tr>
		</thead>
		<tbody id="datatable">

		</tbody>
	</table>
</body>
<script type="text/javascript">
$("#getbig").click(function(){
	$.get("manager/getbig",$("#getform").serialize(),function(data){
		console.log(data);
	});
});

function getone(){
	$.get("manager/getdatalist",function(data){
		$("#datatable").empty();
		for(i in data){
			//console.log(i);
			console.log(data[i]);
			var dop="";
			if(!!data[i].docode){
				dop="<button onclick='dataop(\""+data[i].docode+"\",\""+data[i].md5name+"\")'>"+data[i].dowhat+"</button>";
			}
			var del="<button onclick='dataop(\""+4+"\",\""+data[i].md5name+"\")'>删除</button>";
			var out="<tr><td>"+dop+del+"</td><td>"+data[i].status+"</td><td class='filenames'>"
			+data[i].filename+"</td><td>"+data[i].degree+"</td><td>"+data[i].contentsize+"</td><td>"
			+data[i].countLengths+"/"+data[i].contentLengths+"</td><td>"
			+data[i].averagerate+"</td><td>"+data[i].retrynumcount+"/"+data[i].retrynum
			+"</td><td>"+data[i].timerun+"</td><td>"+data[i].timeoff+"</td></tr>";
			$("#datatable").append(out);
		}
	});
}
getone();
setInterval(function(){
	getone();
},3000)


function dataop(opcode,filecode){
	var msg="";
	if(opcode=="1"){
		msg="暂停";
	}else if(opcode=="2"){
		msg="继续";
	}else if(opcode=="3"){
		msg="继续";
	}else{
		msg="删除";
	}
	if(confirm("是否"+msg)){
		$.get("manager/dataop",{"opcode":opcode,"filecode":filecode},function(data){
			alert(data);
			if(opcode=="4"){
				$("#"+filecode).remove();
			}
		});
	}
}
</script>
</html>
