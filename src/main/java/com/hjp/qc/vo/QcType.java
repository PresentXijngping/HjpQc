package com.hjp.qc.vo;

import java.util.Date;

public class QcType {
	
	private String id;
	
	private String qcTypeCode;
	
	private String qcTypeName;
	
	private String preQcTypeCode;
	
	private String qcTypeLevel;
	
	private Date createTime;
	
	private QcType preQcType;

	private Date editTime;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQcTypeCode() {
		return qcTypeCode;
	}

	public void setQcTypeCode(String qcTypeCode) {
		this.qcTypeCode = qcTypeCode;
	}

	public String getQcTypeName() {
		return qcTypeName;
	}

	public void setQcTypeName(String qcTypeName) {
		this.qcTypeName = qcTypeName;
	}

	public String getPreQcTypeCode() {
		return preQcTypeCode;
	}

	public void setPreQcTypeCode(String preQcTypeCode) {
		this.preQcTypeCode = preQcTypeCode;
	}

	public String getQcTypeLevel() {
		return qcTypeLevel;
	}

	public void setQcTypeLevel(String qcTypeLevel) {
		this.qcTypeLevel = qcTypeLevel;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public QcType getPreQcType() {
		return preQcType;
	}

	public void setPreQcType(QcType preQcType) {
		this.preQcType = preQcType;
	}

	public Date getEditTime() {
		return editTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}

}
