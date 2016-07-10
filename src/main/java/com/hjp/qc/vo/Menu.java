package com.hjp.qc.vo;

public class Menu {
	
	private String menuId;
	
	private String menuName;
	
	private String state;
	
	private String preMenuId;
	
	private String iconCls;
	
	private String menuUrl;

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPreMenuId() {
		return preMenuId;
	}

	public void setPreMenuId(String preMenuId) {
		this.preMenuId = preMenuId;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
}
