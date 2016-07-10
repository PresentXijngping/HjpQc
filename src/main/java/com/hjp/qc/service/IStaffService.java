package com.hjp.qc.service;

import java.util.HashMap;
import java.util.List;

import com.hjp.qc.util.CCHException;
import com.hjp.qc.util.Page;
import com.hjp.qc.vo.Role;
import com.hjp.qc.vo.Staff;
import com.hjp.qc.vo.StaffRole;

public interface IStaffService {
	Staff queryStaffByStaffId(String staffId);
	
	List<Staff> queryStaffByPage(Page page);
	
	List<Role> queryRole(HashMap<String, Object> cond);
	
	void insertStaff(Staff staff, StaffRole staffRole) throws CCHException;
	
	void updateStaffPassword(Staff staff) throws CCHException;
	
	void deleteStaff(HashMap<String, Object> cond) throws CCHException;
	
	List<StaffRole> queryStaffRoleByCond(HashMap<String, Object> cond);
}
