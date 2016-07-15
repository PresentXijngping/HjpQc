package com.hjp.qc.service.impl;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjp.qc.mapper.SequenceMapper;
import com.hjp.qc.service.ISequenceService;

@Service(value="sequenceService")
public class SequenceServiceImpl implements ISequenceService {
	
	@Resource(name="sequenceMapper")  
    private SequenceMapper sequenceMapper;

	@Override
	public Long nextval(String name) {
		return sequenceMapper.nextval(name);
	}

	@Override
	public Timestamp getSysDateTimestamp() {
		return sequenceMapper.getSysDateTimestamp();
	}

}
