$(function () {
	
	$('#firstQcManage').datagrid({
		url : 'queryQcTypePage.do?qcTypeLevel=1',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 20,
		pageList : [10, 20, 30, 40, 50],
		pageNumber : 1,
		toolbar : '#firstQcManageTool',
		columns : [[
		    {
		    	field : 'id',
		    	title : '唯一标识',
		    	width : 100,
		    	checkbox : true,
		    },
		    {
		    	field : 'qcTypeCode',
		    	title : '分类编码',
		    	width : 100,
		    },
		    {
		    	field : 'qcTypeName',
		    	title : '分类名称',
		    	width : 100,
		    },
		    {
		    	field : 'createTime',
		    	title : '创建时间',
		    	width : 100,
		    }
		]],
	});
	
	$('#firstQcManageAdd').dialog({
		width : 350,
		title : '新增一级分类',
		modal : true,
		closed : true,
		iconCls : 'icon-edit-add',
		buttons : [{
			text : '提交',
			iconCls : 'icon-add-new',
			handler : function () {
				if ($('#firstQcManageAdd').form('validate')) {
					var data ={};
					data.qcTypeCode = $('input[name="qcTypeCode"]').val();
					data.qcTypeName = $('input[name="qcTypeName"]').val();
					data.qcTypeLevel = '1';
					data.preQcTypeCode = '0';
					data = JSON.stringify(data);
					jqueryAjaxData('QcManageController','saveNewQcType',data,
							function () {
								$.messager.progress({
									text : '正在新增一级分类中...',
								});
							},
							function (data) {
								var ret = eval(data);
								if(ret.state === 1) {
									$.messager.show({
										title : '提示',
										msg : '新增一级分类成功'
									});
									$('#firstQcManageAdd').dialog('close');
									$('#firstQcManageAdd').form('clear');
									$('#firstQcManage').datagrid('reload');
								}
							},
							function () {
								$.messager.progress('close');
							}
					);
				}
			},
		},{
			text : '取消',
			iconCls : 'icon-redo',
			handler : function() {
				$('#firstQcManageAdd').dialog('close').form('reset');
			},
		}],
	});
	
	$('#firstQcManageEdit').dialog({
		width : 350,
		title : '修改一级分类',
		modal : true,
		closed : true,
		iconCls : 'icon-user-add',
		buttons : [{
				text : '提交',
				iconCls : 'icon-edit-new',
				handler : function () {
					if ($('#firstQcManageEdit').form('validate')) {
						var data ={};
						data.qcTypeCodeEdit = $('input[name="qcTypeCodeEdit"]').val();
						data.qcTypeNameEdit = $('input[name="qcTypeNameEdit"]').val();
						data.idEdit = $('input[name="idEdit"]').val();
						data = JSON.stringify(data);
						jqueryAjaxData('QcManageController','editQcType',data,
								function () {
									$.messager.progress({
										text : '正在修改一级分类中...',
									});
								},
								function (data) {
									var ret = eval(data);
									if(ret.state === 1) {
										$.messager.show({
											title : '提示',
											msg : '修改一级分类成功'
										});
										$('#firstQcManageEdit').dialog('close');
										$('#firstQcManageEdit').form('clear');
										$('#firstQcManage').datagrid('reload');
									}
								},
								function () {
									$.messager.progress('close');
								}
						);
					}
				},
			}, {
				text : '取消',
				iconCls : 'icon-redo',
				handler : function () {
					$('#firstQcManageEdit').dialog('close');
				},
			}
		],
	});
	
	$('input[name="qcTypeCode"]').validatebox({
		required : true,
		missingMessage : '请输入一级分类编码',
		invalidMessage : '一级分类编码不能为空'
	});
	
	$('input[name="qcTypeName"]').validatebox({
		required : true,
		missingMessage : '请输入一级分类名称',
		invalidMessage : '一级分类名称不能为空'
	});
	
	$('input[name="qcTypeCodeEdit"]').validatebox({
		required : true,
		missingMessage : '请输入一级分类编码',
		invalidMessage : '一级分类编码不能为空'
	});
	
	$('input[name="qcTypeNameEdit"]').validatebox({
		required : true,
		missingMessage : '请输入一级分类名称',
		invalidMessage : '一级分类名称不能为空'
	});
	
	fistQcTool = {
		add : function () {
			$('#firstQcManageAdd').form('clear');
			$('#firstQcManageAdd').dialog('open');
			$('input[name="qcTypeCode"]').focus();
		},
		edit : function () {
			var rows = $('#firstQcManage').datagrid('getSelections');
			if (rows.length > 1) {
				$.messager.alert('警告操作！', '编辑记录只能选定一条数据！', 'warning');
			} else if (rows.length == 1) {
				$('#firstQcManageEdit').form('clear');
				$('#firstQcManageEdit').form('load', {
					qcTypeCodeEdit : rows[0].qcTypeCode,
					qcTypeNameEdit : rows[0].qcTypeName,
					idEdit : rows[0].id
				}).dialog('open');
			} else if (rows.length == 0) {
				$.messager.alert('警告操作！', '编辑记录至少选定一条数据！', 'warning');
			}
		},
		remove : function () {
			var rows = $('#firstQcManage').datagrid('getSelections');
			if (rows.length > 0) {
				$.messager.confirm('确定操作', '您正在要删除所选的记录吗？', function (flag) {
					if (flag) {
						var ids = [];
						for(var i=0; i < rows.length; i++) {
							ids.push(rows[i].id);
						}
						var data ={};
						data.ids = ids;
						data = JSON.stringify(data);
						jqueryAjaxData('QcManageController','deleteQcType',data,
								function () {
									$.messager.progress({
										text : '正在一级分类用户中...',
									});
								},
								function (data) {
									var ret = eval(data);
									if(ret.state === 1) {
										$.messager.show({
											title : '提示',
											msg : '删除一级分类成功'
										});
										$('#firstQcManage').datagrid('reload');
									}
								},
								function () {
									$.messager.progress('close');
								}
						);
					}
				});
			}
		},
		search : function () {
			$('#firstQcManage').datagrid('load', {
				queryQcTypeCode : $.trim($('input[name="queryQcTypeCode"]').val()),
				beginTime : $('input[name="beginTime"]').val(),
				endTime : $('input[name="endTime"]').val(),
			});
		}
	}
});