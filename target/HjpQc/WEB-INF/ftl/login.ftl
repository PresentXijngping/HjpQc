<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>花津浦</title>
    <link rel="stylesheet" type="text/css" href="web-res/css-libs/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="web-res/css-libs/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="web-res/css-default/login.css">
</head>
<body>
	<form id="busiForm" action="j_spring_security_check" method="post" style="display:none">
		<div>
			<p>帐号：<input type="text" id="j_username" name="j_username"></p>
			<p>密码：<input type="password" id="j_password" name="j_password"></p>
		</div>
		<input type="submit" hidden style="display:none"/>
	</form>	
	
	<div id="login">
		<p>帐号：<input type="text" id="username" name="username" calss="textbox"></p>
		<p>密码：<input type="password" id="password" name="password" calss="textbox"></p>
	</div>
	<div id="btn">
		<a href="#" class="easyui-linkbutton">登录</a>
		<input type="submit" hidden style="display:none"/>
	</div>

	<script type="text/javascript" src="web-res/js-libs/jquery.min.js"></script>
    <script type="text/javascript" src="web-res/js-libs/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="web-res/js-libs/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="web-res/js-default/login.js"></script>
</body>
</html>