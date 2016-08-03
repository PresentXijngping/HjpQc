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
	<table id="qcManage"></table>
	
	<div id="qcManageTool" style="padding:5px;">
		<div style="margin-bottom:5px;">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add-new" plain="true" onclick="qcManageTool.add();">添加</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit-new" plain="true" onclick="qcManageTool.edit();">修改</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-delete-new" plain="true" onclick="qcManageTool.remove();">删除</a>
		</div>
		<div style="padding:0 0 0 7px;color:#333;">
			查询物料编号：<input type="text" class="textbox" name="queryQcCode" style="width:110px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="qcManageTool.search();">查询</a>
		</div>
	</div>
	
	<div id="qcManageAdd">
		<form id="qcManageAddForm" style="margin:0;padding:5px 0 0 25px;color:#333;">
			<table>
				<tr>
					<td>品项编码：</td><td><input type="text" name="qcCode" class="textbox" style="width:200px;"></td>
					<td>所属分类：</td><td><input type="text" id="qcTypeCode" name="qcTypeCode" class="textbox" style="width:200px;"></td>
				</tr>
				<tr>
					<td>品项名称：</td><td><input type="text" name="qcName" class="textbox" style="width:200px;"></td>
					<td>品项状态：</td><td><input id="state" class="textbox" name="state" style="width:200px;"></td>
				</tr>
				<tr>
					<td>验收标准：</td><td colspan="3"><input id="qcStandard" type="text" class="textbox" name="qcStandard" style="width:475px;height:100px"></td>
				</tr>
			</table>
			<input type="hidden" value="" name="qcId">
		</form>
		<form id="fileForm" action="fileUpload.do" method="post" enctype="multipart/form-data" style="margin:0;padding:5px 0 0 25px;color:#333;" target="busiFrame">
			<table>
				<tr>
					<td style="width:62px;">选择图片：</td><td><input id="fileInfo" class="easyui-filebox" name="fileInfo" style="width:200px"></td>
				</tr>
				<tr>
					<td>图片：</td><td><img id="imgUrl" name="imgUrl" src="web-res/images/image.jpg" width="220" height="220" alt="预览"></td>
				</tr>
			</table>
			<input type="hidden" value="" name="imageId">
		</form>
	<div>
	<iframe id="busiFrame" name="busiFrame" style="display:none;"></iframe>
	
	<script type="text/javascript" src="web-res/js-libs/jquery.min.js"></script>
    <script type="text/javascript" src="web-res/js-libs/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="web-res/js-libs/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="web-res/js-libs/util.js"></script>
    <script type="text/javascript" src="web-res/js-default/qc-manage.js"></script>
</body>
</html>