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

import com.hjp.qc.service.IStaffService;
import com.hjp.qc.util.DateStringUtils;
import com.hjp.qc.util.Page;
import com.hjp.qc.util.PwdEncryptor;
import com.hjp.qc.vo.Role;
import com.hjp.qc.vo.Staff;
import com.hjp.qc.vo.StaffRole;


@Controller(value="StaffManageController")
public class StaffManageController {

	@Resource(name = "staffService")
	private IStaffService staffService;
	
	@RequestMapping(value = "/staffManage.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String login(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		return "staff-manage";
	}
	
	@RequestMapping(value = "/queryStaffInfo.do", method = {RequestMethod.POST, RequestMethod.GET})
	public void queryStaffInfo(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		
		String pageSize = req.getParameter("rows");
		String currPage = req.getParameter("page");
		
		String queryStaffId = req.getParameter("queryStaffId");
		String beginTime = req.getParameter("beginTime");
		String endTime = req.getParameter("endTime");
		
		HashMap<String, Object> con = new HashMap<String, Object>();
		if (queryStaffId != null && !"".equals(queryStaffId)) {
			con.put("queryStaffId", queryStaffId);
		}
		if (beginTime != null && !"".equals(beginTime)) {
			con.put("beginTime", beginTime + " 00:00:00");
		}
		if (endTime != null && !"".equals(endTime)) {
			con.put("endTime", endTime + " 23:59:59");
		}
		
		Page page = new Page(Integer.parseInt(currPage), Integer.parseInt(pageSize));
		page.setT(con);
		
		List<Staff> staffList = staffService.queryStaffByPage(page);
		
		JSONObject returnJson = new JSONObject();
		JSONArray staffArray = new JSONArray();
		for (int i = 0; i < staffList.size(); i++) {
			JSONObject oneObject = new JSONObject();
			oneObject.put("id", staffList.get(i).getId());
			oneObject.put("staffId", staffList.get(i).getStaffId());
			oneObject.put("staffName", staffList.get(i).getStaffName());
			oneObject.put("clearPassword", staffList.get(i).getClearPassword());
			oneObject.put("createTime", DateStringUtils.getStringFromDate(staffList.get(i).getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
			staffArray.put(oneObject);
		}
		
		returnJson.put("total", page.getCountRecord());
		returnJson.put("rows", staffArray);
		
		res.setContentType("text/html;charset=UTF-8");
		res.getWriter().print(returnJson.toString());
		
	}
	
	/**
	 * 获取权限属性下拉框
	 * @param req
	 * @param res
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryRole.do", method = {RequestMethod.POST, RequestMethod.GET})
	public void queryRole(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		String roleType = req.getParameter("roleType");
		HashMap<String, Object> cond = new HashMap<String, Object>();
		if (roleType != null && !"".equals(roleType)) {
			cond.put("roleType", roleType);
		}
		List<Role> roleList = staffService.queryRole(cond);
		JSONArray roleArray = new JSONArray();
		for (int i = 0; i < roleList.size(); i++) {
			JSONObject roleObject = new JSONObject();
			roleObject.put("id", roleList.get(i).getRoleCode());
			roleObject.put("text", roleList.get(i).getRoleName());
			roleArray.put(roleObject);
		}
		res.setContentType("text/html;charset=UTF-8");
		res.getWriter().print(roleArray.toString());
	}
	
	/**
	 * 新增用户
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public JSONObject saveNewStaff(JSONObject json) throws Exception {
		JSONObject returnJson = new JSONObject();
		
		Staff staff = new Staff();
		staff.setMerchantId(json.getString("merchantId"));
		staff.setChildMerchantId(json.getString("childMerchantId"));
		staff.setStaffId(json.getString("newStaffId"));
		staff.setStaffName(json.getString("staffName"));
		staff.setClearPassword(json.getString("password"));
		String password = PwdEncryptor.encryptByMD5(json.getString("password"),json.getString("newStaffId"));
		staff.setPassword(password);
		
		StaffRole staffRole = new StaffRole();
		staffRole.setRoleCode(json.getString("roleCode"));
		
		staffService.insertStaff(staff, staffRole);
		
		return returnJson;
	}
	
	/**
	 * 修改用户密码
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public JSONObject editStaff(JSONObject json) throws Exception {
		JSONObject returnJson = new JSONObject();
		
		Staff staff = new Staff();
		staff.setStaffId(json.getString("editStaffId"));
		String password = PwdEncryptor.encryptByMD5(json.getString("password"),json.getString("editStaffId"));
		staff.setClearPassword(json.getString("password"));
		staff.setPassword(password);
		staffService.updateStaffPassword(staff);
		
		return returnJson;
	}
	
	/**
	 * 删除用户
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public JSONObject deleteStaff(JSONObject json) throws Exception {
		JSONObject returnJson = new JSONObject();
		JSONArray staffArray = json.getJSONArray("staffIds");
		HashMap<String, Object> cond = new HashMap<String, Object>();
		for (int i = 0; i < staffArray.length(); i++) {
			cond.put("staffId", staffArray.get(i));
			staffService.deleteStaff(cond);
		}
		return returnJson;
	}
}
