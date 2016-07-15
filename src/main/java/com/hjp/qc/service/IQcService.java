package com.hjp.qc.service;

import java.util.HashMap;
import java.util.List;

import com.hjp.qc.util.CCHException;
import com.hjp.qc.util.Page;
import com.hjp.qc.vo.Qc;
import com.hjp.qc.vo.QcType;

public interface IQcService {
	List<QcType> queryQcTypeByPage(Page page);
	
	void insertQcType(QcType qcType) throws CCHException;
	
	void updateQcType(QcType qcType);
	
	void deleteQcTypeList(List<QcType> qcTypeList) throws CCHException;
	
	void deleteSecondQcTypeList(List<QcType> qcTypeList) throws CCHException;
	
	List<QcType> queryQcType(HashMap<String, Object> cond);
	
	List<Qc> queryQcByPage(Page page);
	
	void insertQc(Qc qc) throws CCHException;
	
	void updateQc(Qc qc) throws CCHException;
	
	void deleteQcLis(List<Qc> qcList) throws CCHException;
	
	List<Qc> queryQc(HashMap<String, Object> cond);
}
