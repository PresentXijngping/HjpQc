package com.hjp.qc.service;

import java.util.HashMap;
import java.util.List;

import com.hjp.qc.vo.Menu;

public interface IMenuService {
	List<Menu> queryMenuInfo(HashMap<String, Object> cond);
}
