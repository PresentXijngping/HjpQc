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
	<table id="secondQcManage"></table>
	
	<div id="secondQcManageTool" style="padding:5px;">
		<div style="margin-bottom:5px;">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add-new" plain="true" onclick="secondQcTool.add();">添加</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit-new" plain="true" onclick="secondQcTool.edit();">修改</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-delete-new" plain="true" onclick="secondQcTool.remove();">删除</a>
		</div>
		<div style="padding:0 0 0 7px;color:#333;">
			查询分类编号：<input type="text" class="textbox" name="queryQcTypeCode" style="width:110px">
			创建时间从：<input type="text" name="beginTime" class="easyui-datebox" editable="false" style="width:110px">
			到：<input type="text" name="endTime" class="easyui-datebox" editable="false" style="width:110px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="secondQcTool.search();">查询</a>
		</div>
	</div>
	
	<form id="secondQcManageAdd" style="margin:0;padding:5px 0 0 25px;color:#333;">
		<p>二级分类编码：<input type="text" name="qcTypeCode" class="textbox" style="width:200px;"></p>
		<p>二级分类类别：<input type="text" name="qcTypeName" class="textbox" style="width:200px;"></p>
		<p>所属一级分类：<input id="preQcTypeCode" class="textbox" name="preQcTypeCode" style="width:205px;"></p>
	</form>
	
	<form id="secondQcManageEdit" style="margin:0;padding:5px 0 0 25px;color:#333;">
		<input type="hidden" name="idEdit" class="textbox" style="width:200px;">
		<p>二级分类编码：<input type="text" name="qcTypeCodeEdit" class="textbox" style="width:200px;"></p>
		<p>二级分类类别：<input type="text" name="qcTypeNameEdit" class="textbox" style="width:200px;"></p>
		<p>所属一级分类：<input id="preQcTypeCodeEdit" class="textbox" name="preQcTypeCodeEdit" style="width:205px;"></p>
	</form>

	<script type="text/javascript" src="web-res/js-libs/jquery.min.js"></script>
    <script type="text/javascript" src="web-res/js-libs/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="web-res/js-libs/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="web-res/js-libs/util.js"></script>
    <script type="text/javascript" src="web-res/js-default/second-qc.js"></script>
</body>
</html>