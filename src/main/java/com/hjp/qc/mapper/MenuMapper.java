package com.hjp.qc.mapper;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hjp.qc.vo.Menu;

@Component("menuMapper")
public interface MenuMapper {
	
	List<Menu> queryMenuInfo(HashMap<String, Object> cond);
}
