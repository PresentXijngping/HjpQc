package com.hjp.qc.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjp.qc.mapper.MenuMapper;
import com.hjp.qc.service.IMenuService;
import com.hjp.qc.vo.Menu;

@Service(value="menuService")
public class MenuServiceImpl implements IMenuService {
	
	@Resource(name="menuMapper")  
    private MenuMapper menuMapper;

	@Override
	public List<Menu> queryMenuInfo(HashMap<String, Object> cond) {
		return menuMapper.queryMenuInfo(cond);
	}

}
