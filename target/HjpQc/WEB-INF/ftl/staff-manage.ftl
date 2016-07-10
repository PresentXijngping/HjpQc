<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>花津浦</title>
    <link rel="stylesheet" type="text/css" href="web-res/css-libs/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="web-res/css-libs/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="web-res/css-default/welcome.css">
</head>
<body>
	<table id="staffManage"></table>
	
	<div id="manageTool" style="padding:5px;">
		<div style="margin-bottom:5px;">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add-new" plain="true" onclick="manageTools.add();">添加</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit-new" plain="true" onclick="manageTools.edit();">修改</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-delete-new" plain="true" onclick="manageTools.remove();">删除</a>
		</div>
		<div style="padding:0 0 0 7px;color:#333;">
			查询帐号：<input type="text" class="textbox" name="queryStaffId" style="width:110px">
			创建时间从：<input type="text" name="beginTime" class="easyui-datebox" editable="false" style="width:110px">
			到：<input type="text" name="endTime" class="easyui-datebox" editable="false" style="width:110px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="manageTools.search();">查询</a>
		</div>
	</div>
	
	<form id="manageAdd" style="margin:0;padding:5px 0 0 25px;color:#333;">
		<p>用户帐号：<input type="text" name="staffId" class="textbox" style="width:200px;"></p>
		<p>用户名称：<input type="text" name="staffName" class="textbox" style="width:200px;"></p>
		<p>用户密码：<input type="password" name="password" class="textbox" style="width:200px;"></p>
		<p>分配权限：<input id="auth" class="textbox" name="auth" style="width:205px;"></p>
	</form>
	
	<form id="manageEdit" style="margin:0;padding:5px 0 0 25px;color:#333;">
		<p>用户帐号：<input type="text" name="staffIdEdit" disabled="true" class="textbox" style="width:200px;"></p>
		<p>用户名称：<input type="text" name="staffNameEdit" disabled="true" class="textbox" style="width:200px;"></p>
		<p>用户密码：<input type="password" name="passwordEdit" class="textbox" style="width:200px;"></p>
	</form>

	<script type="text/javascript" src="web-res/js-libs/jquery.min.js"></script>
    <script type="text/javascript" src="web-res/js-libs/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="web-res/js-libs/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="web-res/js-libs/util.js"></script>
    <script type="text/javascript" src="web-res/js-default/staffManage.js"></script>
</body>
</html>