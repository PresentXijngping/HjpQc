$(function () {
	
	$('#staffManage').datagrid({
		url : 'queryStaffInfo.do',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 20,
		pageList : [10, 20, 30, 40, 50],
		pageNumber : 1,
		toolbar : '#manageTool',
		columns : [[
		    {
		    	field : 'id',
		    	title : '唯一标识',
		    	width : 100,
		    	checkbox : true,
		    },
		    {
		    	field : 'staffId',
		    	title : '帐号',
		    	width : 100,
		    },
		    {
		    	field : 'staffName',
		    	title : '用户名称',
		    	width : 100,
		    },
		    {
		    	field : 'clearPassword',
		    	title : '用户密码',
		    	width : 100,
		    },
		    {
		    	field : 'createTime',
		    	title : '创建时间',
		    	width : 100,
		    }
		]],
	});
	
	$('#manageAdd').dialog({
		width : 350,
		title : '新增用户',
		modal : true,
		closed : true,
		iconCls : 'icon-user-add',
		buttons : [{
			text : '提交',
			iconCls : 'icon-add-new',
			handler : function () {
				if ($('#manageAdd').form('validate')) {
					var data ={};
					data.newStaffId = $('input[name="staffId"]').val();
					data.password = $('input[name="password"]').val();
					data.roleCode = $('#auth').combotree('getValue');
					data.staffName = $('input[name="staffName"]').val();
					data = JSON.stringify(data);
					jqueryAjaxData('StaffManageController','saveNewStaff',data,
							function () {
								$.messager.progress({
									text : '正在新增用户中...',
								});
							},
							function (data) {
								var ret = eval(data);
								if(ret.state === 1) {
									$.messager.show({
										title : '提示',
										msg : '新增用户成功'
									});
									$('#manageAdd').dialog('close');
									$('#manageAdd').form('clear');
									$('#staffManage').datagrid('reload');
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
				$('#manageAdd').dialog('close').form('reset');
			},
		}],
	});
	
	$('#manageEdit').dialog({
		width : 350,
		title : '修改用户密码',
		modal : true,
		closed : true,
		iconCls : 'icon-user-add',
		buttons : [{
				text : '提交',
				iconCls : 'icon-edit-new',
				handler : function () {
					if ($('#manageEdit').form('validate')) {
						var data ={};
						data.editStaffId = $('input[name="staffIdEdit"]').val();
						data.password = $('input[name="passwordEdit"]').val();
						data = JSON.stringify(data);
						jqueryAjaxData('StaffManageController','editStaff',data,
								function () {
									$.messager.progress({
										text : '正在修改用户中...',
									});
								},
								function (data) {
									var ret = eval(data);
									if(ret.state === 1) {
										$.messager.show({
											title : '提示',
											msg : '修改用户成功'
										});
										$('#manageEdit').dialog('close');
										$('#manageEdit').form('clear');
										$('#staffManage').datagrid('reload');
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
					$('#manageEdit').dialog('close');
				},
			}
		],
	});
	
	$('input[name="staffId"]').validatebox({
		required : true,
		missingMessage : '请输入帐号',
		invalidMessage : '帐号不能为空'
	});
	
	$('input[name="staffName"]').validatebox({
		required : true,
		missingMessage : '请输入帐号名称',
		invalidMessage : '帐号名称不能为空'
	});
	
	//登录密码校验
	$('input[name="password"]').validatebox({
		required : true,
		validType : 'length[6,30]',
		missingMessage : '请输入密码',
		invalidMessage : '密码的长度需在6到10位之间'
	});
	
	//修改密码校验
	$('input[name="passwordEdit"]').validatebox({
		required : true,
		validType : 'length[6,30]',
		missingMessage : '请输入密码',
		invalidMessage : '密码的长度需在6到10位之间'
	});
	
	//分配权限
	$('#auth').combotree({
		url : 'queryRole.do?roleType=MENU',
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
	
	
	manageTools = {
		add : function () {
			$('#manageAdd').form('clear');
			$('#manageAdd').dialog('open');
			$('input[name="staffId"]').focus();
		},
		edit : function () {
			var rows = $('#staffManage').datagrid('getSelections');
			if (rows.length > 1) {
				$.messager.alert('警告操作！', '编辑记录只能选定一条数据！', 'warning');
			} else if (rows.length == 1) {
				$('#manageEdit').form('clear');
				$('#manageEdit').form('load', {
					staffIdEdit : rows[0].staffId,
					staffNameEdit : rows[0].staffName
				}).dialog('open');
			} else if (rows.length == 0) {
				$.messager.alert('警告操作！', '编辑记录至少选定一条数据！', 'warning');
			}
		},
		remove : function () {
			var rows = $('#staffManage').datagrid('getSelections');
			if (rows.length > 0) {
				$.messager.confirm('确定操作', '您正在要删除所选的记录吗？', function (flag) {
					if (flag) {
						var staffIds = [];
						for(var i=0; i < rows.length; i++) {
							staffIds.push(rows[i].staffId);
						}
						var data ={};
						data.staffIds = staffIds;
						data = JSON.stringify(data);
						jqueryAjaxData('StaffManageController','deleteStaff',data,
								function () {
									$.messager.progress({
										text : '正在删除用户中...',
									});
								},
								function (data) {
									var ret = eval(data);
									if(ret.state === 1) {
										$.messager.show({
											title : '提示',
											msg : '删除用户成功'
										});
										$('#staffManage').datagrid('reload');
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
			$('#staffManage').datagrid('load', {
				queryStaffId : $.trim($('input[name="queryStaffId"]').val()),
				beginTime : $('input[name="beginTime"]').val(),
				endTime : $('input[name="endTime"]').val(),
			});
		}
	};
});