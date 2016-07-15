package com.hjp.qc.util;


import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: ResultMap <br/>
 * date:      2015年03月9日 下午8:01:28 <br/>
 *
 * @author bianxf
 * @version 
 */
public class ResultMap {
	private String errcode;
	private String msg;
	private Map<String, Object> data;
	
	/**
	 * setErrorcode:设置结果编码. <br/>
	 * 
	 * @param errorcode
	 */
	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}
	
	public String getErrcode() {
		return this.errcode;
	}
	
	/**
	 * setMsg:设置结果描述. <br/>
	 *
	 * @param msg
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String getMsg() {
		return this.msg;
	}
	
	public ResultMap() {
		this.errcode =  Constants.ERRORCODE_SUCCESS;
		this.msg = "ok";
	}
	
	/**
	 * Creates a new instance of ResultMap.
	 *
	 * @param errorcode 结果编码
	 * @param msg 结果描述
	 */
	public ResultMap(String errorcode, String msg) {
		this.errcode =  errorcode;
		this.msg = msg;
	}
	
	public ResultMap(Errors error) {
		this.errcode =  error.getCode();
		this.msg = error.getMsg();
	}
	
	public ResultMap(Errors error, String msg) {
		this.errcode =  error.getCode();
		this.msg = msg;
	}
	
	public Map<String, Object> getData() {
		if (data == null) {
			data = new HashMap<String, Object>();
		}
		return this.data;
	}
	
	public void setData(Map<String, Object> data) {
		if (this.data == null) {
			this.data = data;
		} else {
			this.data.putAll(data);
		}
	}
	
	public void putData(String key, Object value) {
		if (data == null) {
			data = new HashMap<String, Object>();
		}
		data.put(key, value);
	}
	
	public void setError(Errors error) {
		this.errcode = error.getCode();
		this.msg = error.getMsg();
	}
}


