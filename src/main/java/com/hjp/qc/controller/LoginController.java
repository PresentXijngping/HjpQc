package com.hjp.qc.controller;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hjp.qc.service.IMenuService;
import com.hjp.qc.service.IStaffService;
import com.hjp.qc.util.PwdEncryptor;
import com.hjp.qc.vo.Menu;
import com.hjp.qc.vo.Staff;
import com.hjp.qc.vo.StaffRole;

@Controller(value="LoginController")
public class LoginController {
	
	@Resource(name = "staffService")
	private IStaffService staffService;
	
	@Resource(name = "menuService")
	private IMenuService menuService;
	
	@RequestMapping(value = "/login.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String login(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		return "login";
	}
	
	@RequestMapping(value = "/welcome.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String welocme(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		Staff staff = staffService.queryStaffByStaffId(userName);
		model.addAttribute("staffName", staff.getStaffName());
		return "welcome";
	}
	
	@RequestMapping(value = "/checkUserExist.do", method = {RequestMethod.POST, RequestMethod.GET})
	public void checkUserExist(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		JSONObject returnJson = new JSONObject();
		String staffId = req.getParameter("username");
		String password = req.getParameter("password");
		
		String miPassword = PwdEncryptor.encryptByMD5(password, staffId);
		Staff staff = staffService.queryStaffByStaffId(staffId);
		
		HashMap<String, Object> cond = new HashMap<String, Object>();
		cond.put("staffId", staffId);
		cond.put("roleType", "MENU");
		List<StaffRole> staffRoleList = staffService.queryStaffRoleByCond(cond);
		if (staff == null) {
			returnJson.put("status", "0");
			returnJson.put("message", "该工号不存在");
		} else if(!miPassword.equals(staff.getPassword())) {
			returnJson.put("status", "2");
			returnJson.put("message", "密码错误");
		} else {
			if (staffRoleList.size() > 0) {
				if (staffRoleList.get(0).getRoleCode().equals("ROLE_MANAGER")) {
					returnJson.put("status", "1");
					returnJson.put("message", "验证通过");
				} else {
					returnJson.put("status", "3");
					returnJson.put("message", "权限不足");
				}
			} else {
				returnJson.put("status", "3");
				returnJson.put("message", "权限不足");
			}
			
		}
		
		res.setContentType("text/html;charset=UTF-8");
		res.getWriter().print(returnJson.toString());
	}
	
	/**
	 * 获取左边导航栏菜单
	 * @param req
	 * @param res
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryNavMenu.do", method = {RequestMethod.POST, RequestMethod.GET})
	public void queryNavMenu(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		String preMenuId = req.getParameter("id");
		HashMap<String, Object> cond = new HashMap<String, Object>();
		if (preMenuId != null && !"".equals(preMenuId)) {
			cond.put("preMenuId", preMenuId);
		} else {
			cond.put("preMenuId", "0");
		}
		List<Menu> menuList = menuService.queryMenuInfo(cond);
		JSONArray menuArray = new JSONArray();
		for (int i = 0; i < menuList.size(); i++) {
			JSONObject menuObject = new JSONObject();
			menuObject.put("id", menuList.get(i).getMenuId());
			menuObject.put("text", menuList.get(i).getMenuName());
			menuObject.put("state", menuList.get(i).getState());
			menuObject.put("iconCls", menuList.get(i).getIconCls());
			menuObject.put("url", menuList.get(i).getMenuUrl());
			menuArray.put(menuObject);
		}
		
		res.setContentType("text/html;charset=UTF-8");
		res.getWriter().print(menuArray.toString());
	}
	
}
