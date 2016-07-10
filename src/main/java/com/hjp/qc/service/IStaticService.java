package com.hjp.qc.service;

import com.hjp.qc.vo.StaticVariable;

public interface IStaticService {
	StaticVariable queryStaticByTypeCode(String typeCode);
}
