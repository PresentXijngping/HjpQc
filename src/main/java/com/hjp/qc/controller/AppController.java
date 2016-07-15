package com.hjp.qc.controller;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hjp.qc.service.IQcService;
import com.hjp.qc.service.IStaticService;
import com.hjp.qc.util.Errors;
import com.hjp.qc.util.ResultMap;
import com.hjp.qc.vo.Qc;
import com.hjp.qc.vo.QcType;
import com.hjp.qc.vo.StaticVariable;

@Controller(value="AppController")
public class AppController {
	
	@Resource(name = "qcService")
	private IQcService qcService;
	
	@Resource(name = "staticService")
	private IStaticService staticService;
	
	@RequestMapping(value = "/app/queryQcType.do", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public ResultMap queryQcType(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		ResultMap rm = new ResultMap();
		String synTime= ""; 
		if (req.getParameter("synTime") != null && !"".equals(req.getParameter("synTime"))) {
			synTime = req.getParameter("synTime");
		} else {
			rm.setError(Errors.param_missing);
			return rm;
		}
		HashMap<String, Object> cond = new HashMap<String, Object>();
		cond.put("synTime", synTime);
		List<QcType> qcTypeList = qcService.queryQcType(cond);
		if (qcTypeList.size() != 0) {
			rm.putData("info", qcTypeList);
		} else {
			rm.setError(Errors.data_not_found);
		}
		
		return rm;
	}
	
	@RequestMapping(value = "/app/queryQc.do", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public ResultMap queryQc(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		ResultMap rm = new ResultMap();
		String synTime= ""; 
		if (req.getParameter("synTime") != null && !"".equals(req.getParameter("synTime"))) {
			synTime = req.getParameter("synTime");
		} else {
			rm.setError(Errors.param_missing);
			return rm;
		}
		HashMap<String, Object> cond = new HashMap<String, Object>();
		cond.put("synTime", synTime);
		List<Qc> qcList = qcService.queryQc(cond);
		StaticVariable staticVariable = staticService.queryStaticByTypeCode("FILE_PATH");
		String filePath = staticVariable.getKeyName() + req.getContextPath() + "/";
		
		for (int i = 0; i < qcList.size(); i++) {
			qcList.get(i).setRealPicUrl(filePath + qcList.get(i).getPicUrl());
		}
		if (qcList.size() != 0) {
			rm.putData("info", qcList);
		} else {
			rm.setError(Errors.data_not_found);
		}
		return rm;
	}
}
