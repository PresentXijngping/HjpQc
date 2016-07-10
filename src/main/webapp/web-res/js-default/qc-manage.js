$(function () {
	//定义操作类型，因为用的是一个模块，需要区分
	var dataType = "add";
	
	$('#qcManage').datagrid({
		url : 'queryQcByPage.do',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 20,
		pageList : [10, 20, 30, 40, 50],
		pageNumber : 1,
		toolbar : '#qcManageTool',
		columns : [[
		    {
		    	field : 'id',
		    	title : '唯一标识',
		    	width : 100,
		    	checkbox : true,
		    },
		    {
		    	field : 'qcCode',
		    	title : '品项编码',
		    	width : 100,
		    },
		    {
		    	field : 'qcName',
		    	title : '品项名称',
		    	width : 100,
		    },
		    {
		    	field : 'firstQcType',
		    	title : '所属一级分类编码',
		    	width : 100,
		    	hidden : true,
		    },
		    {
		    	field : 'firstQcTypeName',
		    	title : '所属一级分类名称',
		    	width : 100,
		    },
		    {
		    	field : 'secondQcType',
		    	title : '所属二级分类编码',
		    	width : 100,
		    	hidden : true,
		    },
		    {
		    	field : 'secondQcTypeName',
		    	title : '所属二级分类名称',
		    	width : 100,
		    },
		    {
		    	field : 'qcStandard',
		    	title : '验收标准',
		    	width : 100,
		    },
		    {
		    	field : 'stateName',
		    	title : '品项状态',
		    	width : 100,
		    },
		    {
		    	field : 'createTime',
		    	title : '创建时间',
		    	width : 100,
		    },
		    {
		    	field : 'picUrl',
		    	title : '图片地址',
		    	width : 100,
		    	hidden : true,
		    },
		    {
		    	field : 'state',
		    	title : '状态',
		    	width : 100,
		    	hidden : true,
		    },
		    {
		    	field : 'imageId',
		    	title : '图片编码',
		    	width : 100,
		    	hidden : true,
		    }
		]],
	});
	
	$('#qcManageAdd').dialog({
		width : 650,
		title : '处理品项',
		modal : true,
		closed : true,
		iconCls : 'icon-user-add',
		buttons : [{
			text : '提交',
			iconCls : 'icon-add-new',
			handler : function () {
				if ($('#qcManageAddForm').form('validate')) {
					var data ={};
					data.id = $('input[name="id"]').val();
					data.qcCode = $('input[name="qcCode"]').val();
					data.qcName = $('input[name="qcName"]').val();
					data.qcTypeCode = $('#qcTypeCode').combotree('getValue');
					data.state = $('#state').combobox('getValue');
					data.qcStandard = $('input[name="qcStandard"]').val();
					data.url = $('input[name="imageId"]').val();
					data = JSON.stringify(data);
					var functionName = "";
					if (dataType === "add") {
						functionName = "saveNewQc";
					} else {
						functionName = "editQc";
					}
					
					jqueryAjaxData('QcManageController',functionName,data,
							function () {
								$.messager.progress({
									text : '正在处理品项中...',
								});
							},
							function (data) {
								var ret = eval(data);
								if(ret.state === 1) {
									$.messager.show({
										title : '提示',
										msg : '处理品项成功'
									});
									$('#qcManageAdd').dialog('close');
									$('#qcManageAdd').form('clear');
									$('#qcManage').datagrid('reload');
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
				$('#qcManageAdd').dialog('close').form('reset');
				$('input[name="imageId"]').val("");
				$("#imgUrl").attr('src', "web-res/images/image.jpg");
			},
		}],
	});
	
	
	$('input[name="qcCode"]').validatebox({
		required : true,
		missingMessage : '请输入品项编码',
		invalidMessage : '品项编码不能为空'
	});
	
	$('input[name="qcName"]').validatebox({
		required : true,
		missingMessage : '请输入品项名称',
		invalidMessage : '品项名称不能为空'
	});
	
	//启用状态
	$('#state').combobox({
		valueField : 'id',
		required : true,
		textField : 'text',
		data : [{
			id : '1',
			text : '启用',
		},{
			id : '0',
			text : '禁用',
		}]
	});
	
	//分类树形菜单
	$('#qcTypeCode').combotree({
		url : 'queryAllQcType.do',
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
		onSelect : function(node) {  
	        //返回树对象  
	        var tree = $(this).tree;  
	        //选中的节点是否为叶子节点,如果不是叶子节点,清除选中  
	        var isLeaf = $(this).tree('isLeaf', node.target);  
	        if (!isLeaf) {  
	            //清除选中  
	            $('#qcTypeCode').combotree('clear');  
	        }  
	    } 
	});
	
	$('#qcStandard').textbox({
		multiline : true
	});
	
	$('#fileInfo').filebox({
		buttonText : '选择图片',
		accept : 'image/jpeg, image/x-png',
		editable : false,
		onChange : function (newValue,oldValue) {
			if (newValue != oldValue) {
				if ($('#fileInfo').filebox('getValue') != '') {
					$.messager.progress({
						text : '正在上传图片中...',
					});
					$("#fileForm").submit();
				}
			}
		}
	});
	
	qcManageTool = {
		add : function () {
			dataType = "add";
			$('#qcManageAdd').form('clear');
			$('#qcManageAdd').dialog('open');
			$('input[name="qcCode"]').focus();
		},
		edit : function () {
			dataType = "update";
			var rows = $('#qcManage').datagrid('getSelections');
			if (rows.length > 1) {
				$.messager.alert('警告操作！', '编辑记录只能选定一条数据！', 'warning');
			} else if (rows.length == 1) {
				$('#qcManageAdd').form('clear');
				$('input[name="imageId"]').val(rows[0].imageId);
				$('#imgUrl').attr('src', rows[0].picUrl);
				$('#qcManageAdd').form('load', {
					qcCode : rows[0].qcCode,
					qcTypeCode : rows[0].secondQcType,
					qcName : rows[0].qcName,
					state : rows[0].state,
					qcStandard : rows[0].qcStandard,
					id : rows[0].id
				}).dialog('open');
			} else if (rows.length == 0) {
				$.messager.alert('警告操作！', '编辑记录至少选定一条数据！', 'warning');
			}
		},
		remove : function () {
			var rows = $('#qcManage').datagrid('getSelections');
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
						jqueryAjaxData('QcManageController','deleteQc',data,
								function () {
									$.messager.progress({
										text : '正在删除品项中...',
									});
								},
								function (data) {
									var ret = eval(data);
									if(ret.state === 1) {
										$.messager.show({
											title : '提示',
											msg : '删除品项成功'
										});
										$('#qcManage').datagrid('reload');
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
			$('#qcManage').datagrid('load', {
				queryQcCode : $.trim($('input[name="queryQcCode"]').val()),
			});
		}
	}
});

function fileUploadCallBack(imgUrl,imageId) {
	$.messager.progress('close');
	$("#imgUrl").attr('src', imgUrl);
	$('input[name="imageId"]').val(imageId);
}
