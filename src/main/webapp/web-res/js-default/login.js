$(function() {
	
	//登录界面
	$('#login').dialog({
		title : '登录系统',
		width : 300,
		height : 180,
		modal : true,
		iconCls : 'icon-login',
		buttons : '#btn'
	});
	
	//登录帐号校验
	$('#username').validatebox({
		required : true,
		missingMessage : '请输入帐号',
		invalidMessage : '帐号不能为空'
	});
	
	//登录密码校验
	$('#password').validatebox({
		required : true,
		validType : 'length[6,30]',
		missingMessage : '请输入密码',
		invalidMessage : '密码的长度需在6到10位之间'
	});
	
	//加载时判断验证
	if (!$('#username').validatebox('isValid')) {
		$('#username').focus();
	} else if(!$('#j_password').validatebox('isValid')) {
		$('#password').focus();
	}
	
	//单击登录
	$('#btn a').click(function() {
		if (!$('#username').validatebox('isValid')) {
			$('#username').focus();
		} else if(!$('#password').validatebox('isValid')) {
			$('#password').focus();
		} else {
			$.ajax({
				type : "POST",
				dataType : "JSON",// 返回json格式的数据
				url : "checkUserExist.do",// 要访问的后台地址
				data : {
					username : $("#username").val(),
					password : $('#password').val()
				},
				cache : false,
				beforeSend : function() {
					$.messager.progress({
						text : '正在登录中...',
					});
				},
				success : function(response) {
					$.messager.progress('close');
					var ret = eval(response);
					if (ret.status == '1') { 
						$('#j_username').val($('#username').val());
						$('#j_password').val($('#password').val());
						document.getElementById("busiForm").submit();
					} else if(ret.status == '2') {
						$.messager.alert('登录失败！', '密码错误！', 'warning', function () {
							$('#password').select();
						});
					} else if(ret.status == '0') {
						$.messager.alert('登录失败！', '用户名不存在！', 'warning', function () {
							$('#username').select();
						});
					} else if(ret.status == '3') {
						$.messager.alert('登录失败！', '权限不足！', 'warning', function () {
							$('#username').select();
						}); 
					}
				}
			});
		}
	});
});

