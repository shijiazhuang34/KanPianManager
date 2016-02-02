<%@ page language="java"  pageEncoding="UTF-8"%>
<%@include file="taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<base href="<%=basePath%>">
	<meta charset="UTF-8">
	<meta name="viewport"content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	<meta content="telephone=no" name="format-detection" />
<!----------------------------->
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="description" content="A front-end template that helps you build fast, modern mobile web apps.">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- Add to homescreen for Chrome on Android -->
	<meta name="mobile-web-app-capable" content="yes">
	<link rel="icon" sizes="192x192" href="images/touch/chrome-touch-icon-192x192.png">

	<!-- Add to homescreen for Safari on iOS -->
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-title" content="Material Design Lite">
	<link rel="apple-touch-icon-precomposed" href="apple-touch-icon-precomposed.png">

	<!-- Tile icon for Win8 (144x144 + tile color) -->
	<meta name="msapplication-TileImage" content="images/touch/ms-touch-icon-144x144-precomposed.png">
	<meta name="msapplication-TileColor" content="#3372DF">

	<link href="mdl/css/family_Roboto.css" rel="stylesheet">
	<link href="mdl/css/family_Material_Icons.css" rel="stylesheet">
	<link rel="stylesheet" href="mdl/css/material.min.css">
	<link rel="stylesheet" href="mdl/css/styles.css">
<!----------------------------->
	<title>${tabtitle}</title>
	<link rel="stylesheet"  type="text/css" href="css/mdl_ext.css">
	<link rel="stylesheet"  type="text/css" href="css/color_ext.css">
	<script type="text/javascript" src="js/jq1.11.1_min.js"></script>
	<script type="text/javascript" src="js/jquery.devrama.lazyload-0.9.3.js"></script>

	<c:if test="${ ispc eq 1 }">
		<link rel="stylesheet" type="text/css" href="css/default.css"/>
	</c:if>
	<c:if test="${ ispc eq 0 }">
		<link rel="stylesheet" type="text/css" href="css/mobile.css"/>
		<script type="text/javascript" src="js/stickUp.min.js"></script>
	</c:if>
</head>
<body class="mdl-demo mdl-color--grey-100 mdl-color-text--grey-700 mdl-base">
<script type="text/javascript">
	function ispc(){
		var userAgentInfo = navigator.userAgent;
		var Agents = ["Android", "iPhone",
			"SymbianOS", "Windows Phone",
			"iPad", "iPod"];
		var flag = true;
		for (var v = 0; v < Agents.length; v++) {
			if (userAgentInfo.indexOf(Agents[v]) > 0) {
				flag = false;
				break;
			}
		}
		return flag;
	}
</script>
<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header is-upgraded">
<jsp:include page="head.jsp?tab=${tab}"></jsp:include>
<div class="mdl-layout__content ">
	<div class="mdl-layout__tab-panel is-active">
		<div class="mainpage">
			<div class="search searchmin">
				<div  class="mdl-textfield mdl-textfield--expandable is-focused" >
					<label id="isserchmin" class="mdl-button mdl-button--icon" for="search">
						<i class="material-icons">search</i>
					</label>
					<div class="mdl-textfield__expandable-holder">
						<input class="mdl-textfield__input" type="text" id="searchmin">
						<label class="" for="search">Enter your query...</label>
					</div>
				</div>
			</div>
			<c:if test="${ streammode eq 1 }">
				<script type="text/javascript">
					//动态改变搜索的位置
					var wid=$(document).width();
					var mainwidth=+$(".mainpage").css("width").replace("px","");
					var marg_rit=(wid-mainwidth)/2+"px";
					$(".searchmin").css("margin-right",marg_rit);
					$("#searchmin").keypress(function (e) {
						var thisid=$(this).attr("id");
						var key = e.which;
						if (key == 13&&thisid=="searchmin") {
							window.location.href="search?sp="+$("#searchmin").val();
						}
					});
				</script>
			</c:if>

			<div class="leftcontext fl">
				<jsp:include page="${pagetype}.jsp"></jsp:include>
			</div>
			<div class="rightcontext fr mdl-shadow--3dp">
				<div class="search searchmax">
					<div  class="mdl-textfield mdl-textfield--expandable is-focused" >
						<label id="issearch" class="mdl-button mdl-button--icon" for="search">
							<i class="material-icons">search</i>
						</label>
						<div class="mdl-textfield__expandable-holder">
							<input class="mdl-textfield__input" type="text" id="search">
							<label class="" for="search">Enter your query...</label>
						</div>
					</div>
				</div>
				<c:if test="${not empty hotlist }">
				<div class="hotlist rightlist">
					<h5>热门</h5>
					<c:forEach items="${hotlist}" var="dd">
							<a href="javascript:void(0)"  title="${dd.key}" class="searchtag"><span class="mdl-badge" data-badge="${dd.value}">${dd.key}</span></a>
					</c:forEach>
				</div>
				</c:if>
				<c:if test="${not empty datelist }">
				<div class="timelist rightlist">
					<h5>历史档案</h5>
					<ul>
						<c:forEach items="${datelist}" var="d">
							<li title="${d.dateen}" ><a href="javascript:void(0)" class="searchtime" time="${d.dateen}">${d.datecn}<span class="num">(${d.count})</span></a></li>
						</c:forEach>
					</ul>
				</div>
				</c:if>
			</div>
		</div>
	</div>
</div>
</div>
<script type="text/javascript" src="js/default.js"></script>
<jsp:include page="foot.jsp"></jsp:include>
</body>
</html>