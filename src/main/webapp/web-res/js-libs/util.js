/**
 * 通过封装调用的ajax类，来统一管理ajax回调发生的异常等信息
 * 该方法主要用在无跳转的ajax调用
 * @param beanName		方法所在spring Bean名称
 * @param functionName		方法名
 * @param data		json数据
 * @param successfn		成功返回调用方法
 * @param beforeFn	调用后台前，主要用于页面展示
 * @return
 */
function jqueryAjaxData(beanName, functionName, data, beforeFn, successFn, completeFn) {
	data = (data==null || data=="" || typeof(data)=="undefined")? {"date": new Date().getTime()} : data;
	$.ajax({
        type: "post",
        data: {
			data:data,
			beanName:beanName,
			functionName:functionName
		},
        url: "ajaxNotUrl.do",
        dataType: "json",
        beforeSend : function () {
        	beforeFn();
        },
        success: function(d){
        	successFn(d);
        },
		error:function(XMLHttpRequest, textStatus, errorThrown) {
        	switch (XMLHttpRequest.status){  
	            case(500):  
	            	$.messager.alert("错误",XMLHttpRequest.responseText,"warning");
	                break;  
	            case(408):  
	            	$.messager.alert("错误","请求超时","show");
	                break;  
	            default:  
	            	$.messager.alert("错误","亲！系统出错了！工程师马上到！","warning");
	        }  
		},
		complete: function(){
			completeFn();
		}
    });
}