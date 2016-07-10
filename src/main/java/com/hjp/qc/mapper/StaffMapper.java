package com.hjp.qc.mapper;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hjp.qc.util.Page;
import com.hjp.qc.vo.Role;
import com.hjp.qc.vo.Staff;
import com.hjp.qc.vo.StaffRole;

@Component("staffMapper")
public interface StaffMapper {
	
	Staff queryStaffByStaffId(String staffId);
	
	List<StaffRole> queryStaffRole(String staffId);
	
	int insertStaff(Staff staff);
	
	int insertStaffRole(StaffRole staffRole);
	
	void updateStaffPassword(Staff staff);
	
	void deleteStaff(HashMap<String, Object> cond);
	
	List<Staff> queryStaffByPage(Page page);
	
	List<Role> queryRole(HashMap<String, Object> cond);
	
	List<StaffRole> queryStaffRoleByCond(HashMap<String, Object> cond);
}
