<%@ page language="java"  pageEncoding="UTF-8"%>
<%@include file="taglib.jsp"%>
<!DOCTYPE HTMl>
<html>
<head>
    <base href="<%=basePath%>">
    <title>403</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link href="mdl/css/family_Roboto.css" rel="stylesheet">
    <link href="mdl/css/family_Material_Icons.css" rel="stylesheet">
    <link rel="stylesheet" href="mdl/css/material.min.css">
    <link rel="stylesheet" href="mdl/css/styles.css">
    <link rel="stylesheet"  type="text/css" href="css/mdl_ext.css">
    <link rel="stylesheet" href="error/style.css" type="text/css" media="all" />
    <script type="text/javascript" src="js/jq1.11.1_min.js"></script>
    <link rel="icon" sizes="69x69" href="img/logo.png">
</head>
<body>

<div class="wrap">
    <a href=""><h1>JAV</h1></a>
    <div class="banner">
        <form action="login">
            <div class="inputdiv mdl-textfield mdl-js-textfield is-upgraded is-dirty" data-upgraded=",MaterialTextfield">
                账号：<input class="mdl-textfield__input formparam" type="text"   name="root" />
                <label class="mdl-textfield__label" for="sample1"></label>
            </div><br>
            <div class="inputdiv mdl-textfield mdl-js-textfield is-upgraded is-dirty" data-upgraded=",MaterialTextfield">
                密码：<input class="mdl-textfield__input formparam" type="text"   name="pass" />
                <label class="mdl-textfield__label" for="sample1"></label>
            </div><br>
            <button id="login" class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored">
                登录
            </button>
        </form>
        <br><span>${errmsg}</span>
    </div>
</div>
<script type="text/javascript" src="error/init.js"></script>
</body>
</html>
