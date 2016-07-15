package com.hjp.qc.util;


public enum Errors {
	/*----------公共错误----------*/
	data_not_found             ("4","没有符合条件的数据"),
	param_missing              ("31","相关参数为空");
	
    private String code; 
    private String msg; 
	
	// 枚举对象构造函数
    private Errors(String code, String msg) { 
        this.code = code; 
        this.msg = msg;
    }

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
	
	public String toJson(){
		return "{\"errcode\":\"" + code + "\",\"msg\":\"" + msg + "\"}";
	}
}

