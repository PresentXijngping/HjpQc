package com.hjp.qc.vo;

import java.util.Date;

public class Qc {
	
	private String id;
	
	private String qcName;
	
	private String qcTypeCode;
	
	private String qcCode;
	
	private String qcStandard;
	
	private String picUrl;
	
	private String state;
	
	private Date createTime;
	
	private Date editTime;
	
	private String realPicUrl;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQcName() {
		return qcName;
	}

	public void setQcName(String qcName) {
		this.qcName = qcName;
	}

	public String getQcTypeCode() {
		return qcTypeCode;
	}

	public void setQcTypeCode(String qcTypeCode) {
		this.qcTypeCode = qcTypeCode;
	}

	public String getQcCode() {
		return qcCode;
	}

	public void setQcCode(String qcCode) {
		this.qcCode = qcCode;
	}

	public String getQcStandard() {
		return qcStandard;
	}

	public void setQcStandard(String qcStandard) {
		this.qcStandard = qcStandard;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getEditTime() {
		return editTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}

	public String getRealPicUrl() {
		return realPicUrl;
	}

	public void setRealPicUrl(String realPicUrl) {
		this.realPicUrl = realPicUrl;
	}
	
}
