<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>花津浦</title>
    <link rel="stylesheet" type="text/css" href="web-res/css-libs/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="web-res/css-libs/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="web-res/css-default/welcome.css">
</head>
<body class="easyui-layout">

	<div data-options="region:'north',title:'header',split:true,noheader:true" style="height:60px;background:#666;">
		<div class="logo">后台管理</div>
		<div class="logout">您好，${staffName}<a href="j_spring_security_logout">退出</a></div>
	</div>   
	<div data-options="region:'south',title:'footer',split:true,noheader:true" style="height:35px;line-height:30px;text-align:center;">
		版权归属花津浦所有.
	</div>    
	<div data-options="region:'west',title:'导航',split:true,iconCls:'icon-world'" style="width:180px;padding:10px;">
		<ul id="nav"></ul>
	</div>   
	<div data-options="region:'center'" style="overflow:hidden;">
		<div id="tabs">
			<div title="起始页" iconCls="icon-house" style="padding:0 10px;display:block;">
				欢迎来到花津浦品控管理系统！
			</div>
		</div>
	</div> 

	<script type="text/javascript" src="web-res/js-libs/jquery.min.js"></script>
    <script type="text/javascript" src="web-res/js-libs/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="web-res/js-libs/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="web-res/js-default/welcome.js"></script>
</body>
</html>