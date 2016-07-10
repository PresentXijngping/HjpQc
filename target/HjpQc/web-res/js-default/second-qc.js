$(function () {
	
	$('#secondQcManage').datagrid({
		url : 'querySecondQcTypePage.do?qcTypeLevel=2',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 20,
		pageList : [10, 20, 30, 40, 50],
		pageNumber : 1,
		toolbar : '#secondQcManageTool',
		columns : [[
		    {
		    	field : 'id',
		    	title : '唯一标识',
		    	width : 100,
		    	checkbox : true,
		    },
		    {
		    	field : 'qcTypeCode',
		    	title : '二级分类编码',
		    	width : 100,
		    },
		    {
		    	field : 'qcTypeName',
		    	title : '二级分类名称',
		    	width : 100,
		    },
		    {
		    	field : 'preQcTypeName',
		    	title : '所属一级分类名称',
		    	width : 100,
		    },
		    {
		    	field : 'createTime',
		    	title : '创建时间',
		    	width : 100,
		    },
		    {
		    	field : 'preQcTypeCode',
		    	title : '所属一级分类编号',
		    	width : 100,
		    	hidden : true,
		    }
		]],
	});
	
	//一级分类
	$('#preQcTypeCode').combotree({
		url : 'queryQcType.do?qcTypeLevel=1',
		required : true,
		lines : true,
		checkbox : true,
		//multiple : true,
		onlyLeafCheck : true,
		onLoadSuccess : function (node, data) {
			var _this = this;
			if (data) {
				$(data).each(function (index, value) {
					$(_this).tree('expandAll');
				});
			}
		},
	});
	
	$('#preQcTypeCodeEdit').combotree({
		url : 'queryQcType.do?qcTypeLevel=1',
		required : true,
		lines : true,
		checkbox : true,
		//multiple : true,
		onlyLeafCheck : true,
		onLoadSuccess : function (node, data) {
			var _this = this;
			if (data) {
				$(data).each(function (index, value) {
					$(_this).tree('expandAll');
				});
			}
		},
	});
	
	$('#secondQcManageAdd').dialog({
		width : 350,
		title : '新增一级分类',
		modal : true,
		closed : true,
		iconCls : 'icon-edit-add',
		buttons : [{
			text : '提交',
			iconCls : 'icon-add-new',
			handler : function () {
				if ($('#secondQcManageAdd').form('validate')) {
					var data ={};
					data.qcTypeCode = $('input[name="qcTypeCode"]').val();
					data.qcTypeName = $('input[name="qcTypeName"]').val();
					data.qcTypeLevel = '2';
					data.preQcTypeCode = $('#preQcTypeCode').combotree('getValue');;
					data = JSON.stringify(data);
					jqueryAjaxData('QcManageController','saveNewQcType',data,
							function () {
								$.messager.progress({
									text : '正在新增二级分类中...',
								});
							},
							function (data) {
								var ret = eval(data);
								if(ret.state === 1) {
									$.messager.show({
										title : '提示',
										msg : '新增二级分类成功'
									});
									$('#secondQcManageAdd').dialog('close');
									$('#secondQcManageAdd').form('clear');
									$('#secondQcManage').datagrid('reload');
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
	
	$('#secondQcManageEdit').dialog({
		width : 350,
		title : '修改一级分类',
		modal : true,
		closed : true,
		iconCls : 'icon-user-add',
		buttons : [{
				text : '提交',
				iconCls : 'icon-edit-new',
				handler : function () {
					if ($('#secondQcManageEdit').form('validate')) {
						var data ={};
						data.qcTypeCodeEdit = $('input[name="qcTypeCodeEdit"]').val();
						data.qcTypeNameEdit = $('input[name="qcTypeNameEdit"]').val();
						data.preQcTypeCodeEdit = $('#preQcTypeCodeEdit').combotree('getValue');
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
										$('#secondQcManageEdit').dialog('close');
										$('#secondQcManageEdit').form('clear');
										$('#secondQcManage').datagrid('reload');
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
					$('#secondQcManageEdit').dialog('close');
				},
			}
		],
	});
	
	$('input[name="qcTypeCode"]').validatebox({
		required : true,
		missingMessage : '请输入二级分类编码',
		invalidMessage : '二级分类编码不能为空'
	});
	
	$('input[name="qcTypeName"]').validatebox({
		required : true,
		missingMessage : '请输入二级分类名称',
		invalidMessage : '二级分类名称不能为空'
	});
	
	$('input[name="qcTypeCodeEdit"]').validatebox({
		required : true,
		missingMessage : '请输入二级分类编码',
		invalidMessage : '二级分类编码不能为空'
	});
	
	$('input[name="qcTypeNameEdit"]').validatebox({
		required : true,
		missingMessage : '请输入二级分类名称',
		invalidMessage : '二级分类名称不能为空'
	});
	
	secondQcTool = {
		add : function () {
			$('#secondQcManageAdd').form('clear');
			$('#secondQcManageAdd').dialog('open');
			$('input[name="qcTypeCode"]').focus();
		},
		edit : function () {
			var rows = $('#secondQcManage').datagrid('getSelections');
			if (rows.length > 1) {
				$.messager.alert('警告操作！', '编辑记录只能选定一条数据！', 'warning');
			} else if (rows.length == 1) {
				$('#secondQcManageEdit').form('clear');
				$('#secondQcManageEdit').form('load', {
					qcTypeCodeEdit : rows[0].qcTypeCode,
					qcTypeNameEdit : rows[0].qcTypeName,
					idEdit : rows[0].id,
					preQcTypeCodeEdit : rows[0].preQcTypeCode,
				}).dialog('open');
			} else if (rows.length == 0) {
				$.messager.alert('警告操作！', '编辑记录至少选定一条数据！', 'warning');
			}
		},
		remove : function () {
			var rows = $('#secondQcManage').datagrid('getSelections');
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
						jqueryAjaxData('QcManageController','deleteSecondQcType',data,
								function () {
									$.messager.progress({
										text : '正在二级分类用户中...',
									});
								},
								function (data) {
									var ret = eval(data);
									if(ret.state === 1) {
										$.messager.show({
											title : '提示',
											msg : '删除二级分类成功'
										});
										$('#secondQcManage').datagrid('reload');
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
			$('#secondQcManage').datagrid('load', {
				queryQcTypeCode : $.trim($('input[name="queryQcTypeCode"]').val()),
				beginTime : $('input[name="beginTime"]').val(),
				endTime : $('input[name="endTime"]').val(),
			});
		}
	}
});