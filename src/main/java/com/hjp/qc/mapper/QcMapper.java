package com.hjp.qc.mapper;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hjp.qc.util.Page;
import com.hjp.qc.vo.Qc;

@Component("qcMapper")
public interface QcMapper {
	
	List<Qc> queryQc(HashMap<String, Object> cond);
	
	List<Qc> queryQcByPage(Page page);
	
	void insertQc(Qc qc);
	
	void updateQc(Qc qc);
	
	void deleteQc(HashMap<String, Object> cond);
}
