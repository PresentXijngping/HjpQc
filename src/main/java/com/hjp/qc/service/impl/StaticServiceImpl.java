package com.hjp.qc.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjp.qc.mapper.StaticVariableMapper;
import com.hjp.qc.service.IStaticService;
import com.hjp.qc.vo.StaticVariable;

@Service(value="staticService")
public class StaticServiceImpl implements IStaticService {

	@Resource(name="staticVariableMapper")  
    private StaticVariableMapper staticVariableMapper;
	
	@Override
	public StaticVariable queryStaticByTypeCode(String typeCode) {
		return staticVariableMapper.queryStaticByTypeCode(typeCode);
	}

}
