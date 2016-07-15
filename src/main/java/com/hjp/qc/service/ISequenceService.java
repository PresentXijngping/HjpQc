package com.hjp.qc.service;

import java.sql.Timestamp;

public interface ISequenceService {
	public Long nextval(String name);
	
	public Timestamp getSysDateTimestamp();
}
