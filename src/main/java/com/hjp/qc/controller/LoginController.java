package com.hjp.qc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller(value="LoginController")
public class LoginController {
	
	@RequestMapping(value = "/login.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String login(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		String error = req.getParameter("error");
		String username = req.getParameter("j_username");
		if ("true".equalsIgnoreCase(error)) {
			model.addAttribute("error", "密码不正确");
			model.addAttribute("username", username);
		}
		return "login";
	}
	
	@RequestMapping(value = "/welcome.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String welocme(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		return "welcome";
	}
	
}
