package com.hjp.qc.mapper;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hjp.qc.util.Page;
import com.hjp.qc.vo.QcType;

@Component("qcTypeMapper")
public interface QcTypeMapper {
	
	List<QcType> queryQcTypeByPage(Page page);
	
	void insertQcType(QcType qcType);
	
	List<QcType> queryQcType(HashMap<String, Object> cond);
	
	void updateQcType(QcType qcType);
	
	void deleteQcType(HashMap<String, Object> cond);
	
}
