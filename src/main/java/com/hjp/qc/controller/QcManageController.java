package com.hjp.qc.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
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

import com.hjp.qc.service.IQcService;
import com.hjp.qc.service.ISequenceService;
import com.hjp.qc.service.IStaffService;
import com.hjp.qc.service.IStaticService;
import com.hjp.qc.util.DateStringUtils;
import com.hjp.qc.util.Page;
import com.hjp.qc.vo.Qc;
import com.hjp.qc.vo.QcType;
import com.hjp.qc.vo.StaticVariable;

@Controller(value="QcManageController")
public class QcManageController {
	
	@Resource(name = "staffService")
	private IStaffService staffService;
	
	@Resource(name = "qcService")
	private IQcService qcService;
	
	@Resource(name = "staticService")
	private IStaticService staticService;
	
	@Resource(name = "sequenceService")
	private ISequenceService sequenceService;
	
	@RequestMapping(value = "/first-qc.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String firstQc(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		return "first-qc";
	}
	
	@RequestMapping(value = "/second-qc.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String secondQc(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		return "second-qc";
	}
	
	@RequestMapping(value = "/qcManage.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String qcManage(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		return "qc-manage";
	}
	
	@RequestMapping(value = "/queryQcTypePage.do", method = {RequestMethod.POST, RequestMethod.GET})
	public void queryQcTypePage(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		String pageSize = req.getParameter("rows");
		String currPage = req.getParameter("page");
		
		String qcTypeLevel = req.getParameter("qcTypeLevel");
		String queryQcTypeCode = req.getParameter("queryQcTypeCode");
		String beginTime = req.getParameter("beginTime");
		String endTime = req.getParameter("endTime");
		
		HashMap<String, Object> con = new HashMap<String, Object>();
		if (qcTypeLevel != null && !"".equals(qcTypeLevel)) {
			con.put("qcTypeLevel", qcTypeLevel);
		}
		if (queryQcTypeCode != null && !"".equals(queryQcTypeCode)) {
			con.put("queryQcTypeCode", queryQcTypeCode);
		}
		if (beginTime != null && !"".equals(beginTime)) {
			con.put("beginTime", beginTime + " 00:00:00");
		}
		if (endTime != null && !"".equals(endTime)) {
			con.put("endTime", endTime + " 23:59:59");
		}
		Page page = new Page(Integer.parseInt(currPage), Integer.parseInt(pageSize));
		page.setT(con);
		
		List<QcType> qcTypeList = qcService.queryQcTypeByPage(page);
		JSONObject returnJson = new JSONObject();
		JSONArray qcTypeArray = new JSONArray();
		for (int i = 0; i < qcTypeList.size(); i++) {
			JSONObject oneObject = new JSONObject();
			oneObject.put("id", qcTypeList.get(i).getId());
			oneObject.put("qcTypeCode", qcTypeList.get(i).getQcTypeCode());
			oneObject.put("qcTypeName", qcTypeList.get(i).getQcTypeName());
			oneObject.put("createTime", DateStringUtils.getStringFromDate(qcTypeList.get(i).getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
			qcTypeArray.put(oneObject);
		}
		returnJson.put("total", page.getCountRecord());
		returnJson.put("rows", qcTypeArray);
		
		res.setContentType("text/html;charset=UTF-8");
		res.getWriter().print(returnJson.toString());
	}
	
	@RequestMapping(value = "/querySecondQcTypePage.do", method = {RequestMethod.POST, RequestMethod.GET})
	public void querySecondQcTypePage(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		String pageSize = req.getParameter("rows");
		String currPage = req.getParameter("page");
		
		String qcTypeLevel = req.getParameter("qcTypeLevel");
		String queryQcTypeCode = req.getParameter("queryQcTypeCode");
		String beginTime = req.getParameter("beginTime");
		String endTime = req.getParameter("endTime");
		
		HashMap<String, Object> con = new HashMap<String, Object>();
		if (qcTypeLevel != null && !"".equals(qcTypeLevel)) {
			con.put("qcTypeLevel", qcTypeLevel);
		}
		if (queryQcTypeCode != null && !"".equals(queryQcTypeCode)) {
			con.put("queryQcTypeCode", queryQcTypeCode);
		}
		if (beginTime != null && !"".equals(beginTime)) {
			con.put("beginTime", beginTime + " 00:00:00");
		}
		if (endTime != null && !"".equals(endTime)) {
			con.put("endTime", endTime + " 23:59:59");
		}
		Page page = new Page(Integer.parseInt(currPage), Integer.parseInt(pageSize));
		page.setT(con);
		
		List<QcType> qcTypeList = qcService.queryQcTypeByPage(page);
		
		//查询一级分类
		con.clear();
		con.put("qcTypeLevel", "1");
		List<QcType> firstQcTypeList = qcService.queryQcType(con);
		
		JSONObject returnJson = new JSONObject();
		JSONArray qcTypeArray = new JSONArray();
		for (int i = 0; i < qcTypeList.size(); i++) {
			JSONObject oneObject = new JSONObject();
			oneObject.put("id", qcTypeList.get(i).getId());
			oneObject.put("qcTypeCode", qcTypeList.get(i).getQcTypeCode());
			oneObject.put("qcTypeName", qcTypeList.get(i).getQcTypeName());
			oneObject.put("createTime", DateStringUtils.getStringFromDate(qcTypeList.get(i).getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
			for (int j = 0; j < firstQcTypeList.size(); j++) {
				if (qcTypeList.get(i).getPreQcTypeCode().equals(firstQcTypeList.get(j).getId())) {
					oneObject.put("preQcTypeName", firstQcTypeList.get(j).getQcTypeName());
				}
			}
			oneObject.put("preQcTypeCode", qcTypeList.get(i).getPreQcTypeCode());
			qcTypeArray.put(oneObject);
		}
		returnJson.put("total", page.getCountRecord());
		returnJson.put("rows", qcTypeArray);
		
		res.setContentType("text/html;charset=UTF-8");
		res.getWriter().print(returnJson.toString());
	}
	
	@RequestMapping(value = "/queryQcByPage.do", method = {RequestMethod.POST, RequestMethod.GET})
	public void queryQcByPage(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		String pageSize = req.getParameter("rows");
		String currPage = req.getParameter("page");
		
		StaticVariable staticVariable = staticService.queryStaticByTypeCode("FILE_PATH");
		String filePath = staticVariable.getKeyName() + req.getContextPath() + "/";
		
		String queryQcCode = req.getParameter("queryQcCode");
		
		HashMap<String, Object> con = new HashMap<String, Object>();
		if (queryQcCode != null && !"".equals(queryQcCode)) {
			con.put("queryQcCode", queryQcCode);
		}
		con.put("notQueryDelete", "1");
		Page page = new Page(Integer.parseInt(currPage), Integer.parseInt(pageSize));
		page.setT(con);
		
		List<Qc> qcList = qcService.queryQcByPage(page);
		
		con.clear();
		con.put("qcTypeLevel", "1");
		List<QcType> firstQcTypeList = qcService.queryQcType(con);
		con.put("qcTypeLevel", "2");
		List<QcType> secondQcTypeList = qcService.queryQcType(con);
		//将二级分类的所属一级分类进行组装
		for (int i = 0; i < secondQcTypeList.size(); i++) {
			for (int j = 0; j < firstQcTypeList.size(); j++) {
				if (secondQcTypeList.get(i).getPreQcTypeCode().equals(firstQcTypeList.get(j).getId())) {
					secondQcTypeList.get(i).setPreQcType(firstQcTypeList.get(j));
					continue;
				}
			}
		}
		
		JSONObject returnJson = new JSONObject();
		JSONArray qcArray = new JSONArray();
		for (int i = 0; i < qcList.size(); i++) {
			JSONObject oneObject = new JSONObject();
			oneObject.put("id", qcList.get(i).getId());
			oneObject.put("qcCode", qcList.get(i).getQcCode());
			oneObject.put("qcName", qcList.get(i).getQcName());
			oneObject.put("secondQcType", qcList.get(i).getQcTypeCode());
			oneObject.put("qcStandard", qcList.get(i).getQcStandard());
			oneObject.put("state", qcList.get(i).getState());
			oneObject.put("picUrl", filePath + qcList.get(i).getPicUrl());
			oneObject.put("imageId", qcList.get(i).getPicUrl());
			if (qcList.get(i).getState().equals("1")) {
				oneObject.put("stateName", "启用");
			} else {
				oneObject.put("stateName", "禁用");
			}
			oneObject.put("createTime", DateStringUtils.getStringFromDate(qcList.get(i).getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
			for (int j = 0; j < secondQcTypeList.size(); j++) {
				if (qcList.get(i).getQcTypeCode().equals(secondQcTypeList.get(j).getId())) {
					oneObject.put("firstQcTypeName", secondQcTypeList.get(j).getPreQcType().getQcTypeName());
					oneObject.put("firstQcType", secondQcTypeList.get(j).getPreQcType().getId());
					oneObject.put("secondQcTypeName", secondQcTypeList.get(j).getQcTypeName());
				}
			}
			qcArray.put(oneObject);
		}
		
		
		returnJson.put("total", page.getCountRecord());
		returnJson.put("rows", qcArray);
		res.setContentType("text/html;charset=UTF-8");
		res.getWriter().print(returnJson.toString());
	}
	
	/**
	 * 查询分类列表
	 * @param req
	 * @param res
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryQcType.do", method = {RequestMethod.POST, RequestMethod.GET})
	public void queryQcType(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		String qcTypeLevel= req.getParameter("qcTypeLevel");
		HashMap<String, Object> cond = new HashMap<String, Object>();
		cond.put("qcTypeLevel", qcTypeLevel);
		List<QcType> qcTypeList = qcService.queryQcType(cond);
		JSONArray qcTypeArray = new JSONArray();
		for (int i = 0; i < qcTypeList.size(); i++) {
			JSONObject qcTypeObject = new JSONObject();
			qcTypeObject.put("id", qcTypeList.get(i).getId());
			qcTypeObject.put("text", qcTypeList.get(i).getQcTypeName());
			qcTypeArray.put(qcTypeObject);
		}
		res.setContentType("text/html;charset=UTF-8");
		res.getWriter().print(qcTypeArray.toString());
	}
	
	/**
	 * 查询所有分类树形结构
	 * @param req
	 * @param res
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAllQcType.do", method = {RequestMethod.POST, RequestMethod.GET})
	public void queryAllQcType(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		HashMap<String, Object> cond = new HashMap<String, Object>();
		cond.put("qcTypeLevel", "1");
		List<QcType> firstQcTypeList = qcService.queryQcType(cond);
		cond.put("qcTypeLevel", "2");
		List<QcType> secondQcTypeList = qcService.queryQcType(cond);
		
		JSONArray qcTypeArray = new JSONArray();
		for (int i = 0; i < firstQcTypeList.size(); i++) {
			JSONObject firstObject = new JSONObject();
			firstObject.put("id", firstQcTypeList.get(i).getId());
			firstObject.put("text", firstQcTypeList.get(i).getQcTypeName());
			JSONArray childArray = new JSONArray();
			for (int j = 0; j < secondQcTypeList.size(); j++) {
				if (firstQcTypeList.get(i).getId().equals(secondQcTypeList.get(j).getPreQcTypeCode())) {
					JSONObject childObject = new JSONObject();
					childObject.put("id", secondQcTypeList.get(j).getId());
					childObject.put("text", secondQcTypeList.get(j).getQcTypeName());
					childArray.put(childObject);
				}
			}
			//没有子节点不给进行选择为分类
			if (childArray.length() != 0) {
				firstObject.put("children", childArray);
				qcTypeArray.put(firstObject);
			}
			
		}
		res.setContentType("text/html;charset=UTF-8");
		res.getWriter().print(qcTypeArray.toString());
	}
	
	/**
	 * 新增品控分类
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public JSONObject saveNewQcType(JSONObject json) throws Exception {
		JSONObject returnJson = new JSONObject();
		
		QcType qcType = new QcType();
		qcType.setPreQcTypeCode(json.getString("preQcTypeCode"));
		qcType.setQcTypeCode(json.getString("qcTypeCode"));
		qcType.setQcTypeLevel(json.getString("qcTypeLevel"));
		qcType.setQcTypeName(json.getString("qcTypeName"));
		Timestamp now = sequenceService.getSysDateTimestamp();
		qcType.setCreateTime(now);
		qcType.setEditTime(now);
		qcService.insertQcType(qcType);
		
		return returnJson;
	}
	
	/**
	 * 修改品控分类
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public JSONObject editQcType(JSONObject json) throws Exception {
		JSONObject returnJson = new JSONObject();
		
		QcType qcType = new QcType();
		qcType.setId(json.getString("idEdit"));
		qcType.setQcTypeCode(json.getString("qcTypeCodeEdit"));
		qcType.setQcTypeName(json.getString("qcTypeNameEdit"));
		qcType.setEditTime(sequenceService.getSysDateTimestamp());
		if (json.has("preQcTypeCodeEdit")) {
			qcType.setPreQcTypeCode(json.getString("preQcTypeCodeEdit"));
		}
		qcService.updateQcType(qcType);
		
		return returnJson;
	}
	
	/**
	 * 删除分类
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public JSONObject deleteQcType(JSONObject json) throws Exception {
		JSONObject returnJson = new JSONObject();
		JSONArray qcTypeArray = json.getJSONArray("ids");
		List<QcType> qcTypeList = new ArrayList<QcType>();
		for (int i = 0; i < qcTypeArray.length(); i++) {
			QcType qcType = new QcType();
			qcType.setId(qcTypeArray.get(i).toString());
			qcTypeList.add(qcType);
		}
		qcService.deleteQcTypeList(qcTypeList);
		
		return returnJson;
	}
	
	/**
	 * 删除二级分类
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public JSONObject deleteSecondQcType(JSONObject json) throws Exception {
		JSONObject returnJson = new JSONObject();
		JSONArray qcTypeArray = json.getJSONArray("ids");
		List<QcType> qcTypeList = new ArrayList<QcType>();
		for (int i = 0; i < qcTypeArray.length(); i++) {
			QcType qcType = new QcType();
			qcType.setId(qcTypeArray.get(i).toString());
			qcTypeList.add(qcType);
		}
		qcService.deleteSecondQcTypeList(qcTypeList);
		
		return returnJson;
	}
	
	/**
	 * 新增品项标准
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public JSONObject saveNewQc(JSONObject json) throws Exception {
		JSONObject returnJson = new JSONObject();
		Qc qc = new Qc();
		if (json.has("url")) {
			qc.setPicUrl(json.getString("url"));
		}
		qc.setQcCode(json.getString("qcCode"));
		qc.setQcName(json.getString("qcName"));
		if (json.has("qcStandard")) {
			qc.setQcStandard(json.getString("qcStandard"));
		}
		qc.setQcTypeCode(json.getString("qcTypeCode"));
		qc.setState(json.getString("state"));
		qcService.insertQc(qc);
		
		return returnJson;
	}
	
	/**
	 * 修改品项 标准
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public JSONObject editQc(JSONObject json) throws Exception {
		JSONObject returnJson = new JSONObject();
		
		Qc qc = new Qc();
		if (json.has("url")) {
			qc.setPicUrl(json.getString("url"));
		}
		qc.setQcCode(json.getString("qcCode"));
		qc.setQcName(json.getString("qcName"));
		if (json.has("qcStandard")) {
			qc.setQcStandard(json.getString("qcStandard"));
		}
		qc.setQcTypeCode(json.getString("qcTypeCode"));
		qc.setState(json.getString("state"));
		qc.setId(json.getString("id"));
		qcService.updateQc(qc);
		
		return returnJson;
	}
	
	/**
	 * 删除品项
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public JSONObject deleteQc(JSONObject json) throws Exception {
		JSONObject returnJson = new JSONObject();
		JSONArray qcArray = json.getJSONArray("ids");
		List<Qc> qcList = new ArrayList<Qc>();
		for (int i = 0; i < qcArray.length(); i++) {
			Qc qc = new Qc();
			qc.setId(qcArray.get(i).toString());
			//将状态更新成2，即删除状态
			qc.setState("2");
			qcList.add(qc);
		}
		qcService.updateQcList(qcList);
		return returnJson;
	}
}
