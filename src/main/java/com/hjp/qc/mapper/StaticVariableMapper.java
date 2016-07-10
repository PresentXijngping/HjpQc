package com.hjp.qc.mapper;

import org.springframework.stereotype.Component;

import com.hjp.qc.vo.StaticVariable;

@Component("staticVariableMapper")
public interface StaticVariableMapper {
	
	StaticVariable queryStaticByTypeCode(String typeCode);
	
}
