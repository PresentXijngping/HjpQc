package com.hjp.qc.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hjp.qc.mapper.SequenceMapper;
import com.hjp.qc.mapper.StaffMapper;
import com.hjp.qc.service.IStaffService;
import com.hjp.qc.util.CCHException;
import com.hjp.qc.util.Page;
import com.hjp.qc.vo.Role;
import com.hjp.qc.vo.Staff;
import com.hjp.qc.vo.StaffRole;

@Service(value="staffService")
public class StaffServiceImpl implements IStaffService {
	
	@Resource(name="staffMapper")  
    private StaffMapper staffMapper;
	
	@Resource(name="sequenceMapper")  
    private SequenceMapper sequenceMapper;

	@Override
	public Staff queryStaffByStaffId(String staffId) {
		return staffMapper.queryStaffByStaffId(staffId);
	}

	@Override
	public List<Staff> queryStaffByPage(Page page) {
		return staffMapper.queryStaffByPage(page);
	}

	@Override
	public List<Role> queryRole(HashMap<String, Object> cond) {
		return staffMapper.queryRole(cond);
	}

	@Transactional(rollbackFor=CCHException.class)
	@Override
	public void insertStaff(Staff staff, StaffRole staffRole)
			throws CCHException {
		Staff existStaff = staffMapper.queryStaffByStaffId(staff.getStaffId());
		if (existStaff != null) {
			throw new CCHException("0", "该[" + staff.getStaffId() + "]已存在，请换另外的工号");
		}
		
		try {
			String id = sequenceMapper.nextval("SEQ_ID").toString();
			staff.setId(id);
			staff.setCreateTime(sequenceMapper.getSysDateTimestamp());
			staffMapper.insertStaff(staff);
			//将选择的权限入库
			staffRole.setStaffId(staff.getStaffId());
			staffMapper.insertStaffRole(staffRole);
			//将登录权限入库
			staffRole.setRoleCode("ROLE_LOGIN");
			staffMapper.insertStaffRole(staffRole);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CCHException("0", "新增用户失败");
		}
	}

	@Override
	public void updateStaffPassword(Staff staff) throws CCHException {
		staffMapper.updateStaffPassword(staff);
	}

	@Override
	public void deleteStaff(HashMap<String, Object> cond) throws CCHException {
		staffMapper.deleteStaff(cond);
	}

	@Override
	public List<StaffRole> queryStaffRoleByCond(HashMap<String, Object> cond) {
		return staffMapper.queryStaffRoleByCond(cond);
	}

}
