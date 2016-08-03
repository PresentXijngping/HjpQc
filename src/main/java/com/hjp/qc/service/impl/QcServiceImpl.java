package com.hjp.qc.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hjp.qc.mapper.QcMapper;
import com.hjp.qc.mapper.QcTypeMapper;
import com.hjp.qc.mapper.SequenceMapper;
import com.hjp.qc.service.IQcService;
import com.hjp.qc.util.CCHException;
import com.hjp.qc.util.Page;
import com.hjp.qc.vo.Qc;
import com.hjp.qc.vo.QcType;

@Service(value="qcService")
public class QcServiceImpl implements IQcService {
	
	@Resource(name="qcTypeMapper")  
    private QcTypeMapper qcTypeMapper;
	
	@Resource(name="sequenceMapper")  
    private SequenceMapper sequenceMapper;
	
	@Resource(name="qcMapper")  
    private QcMapper qcMapper;

	@Override
	public List<QcType> queryQcTypeByPage(Page page) {
		return qcTypeMapper.queryQcTypeByPage(page);
	}

	@Transactional(rollbackFor=CCHException.class)
	@Override
	public void insertQcType(QcType qcType) throws CCHException {
		HashMap<String, Object> cond = new HashMap<String, Object>();
		cond.put("qcTypeCode", qcType.getQcTypeCode());
		List<QcType> qcTypeList = qcTypeMapper.queryQcType(cond);
		if (qcTypeList.size() > 0) {
			throw new CCHException("0", "该[" + qcTypeList.get(0).getQcTypeCode() + "]已存在，请换另外的分类编码");
		}
		try {
			String id = sequenceMapper.nextval("SEQ_ID").toString();
			qcType.setId(id);
			qcTypeMapper.insertQcType(qcType);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CCHException("0", "新增分类信息失败");
		}
	}

	@Override
	public void updateQcType(QcType qcType) {
		qcTypeMapper.updateQcType(qcType);
	}

	@Transactional(rollbackFor=CCHException.class)
	@Override
	public void deleteQcTypeList(List<QcType> qcTypeList) throws CCHException {
		HashMap<String, Object> cond = new HashMap<String, Object>();
		for (int i = 0; i < qcTypeList.size(); i++) {
			cond.put("preQcTypeCode", qcTypeList.get(i).getId());
			List<QcType> exsitQcTypeList = qcTypeMapper.queryQcType(cond);
			if (exsitQcTypeList.size() > 0) {
				throw new CCHException("0", "该[" + exsitQcTypeList.get(0).getQcTypeCode() + "]存在下级分类，无法删除");
			}
			
			try {
				cond.clear();
				cond.put("id", qcTypeList.get(i).getId());
				qcTypeMapper.deleteQcType(cond);
			} catch (Exception e) {
				e.printStackTrace();
				throw new CCHException("0", "删除分类信息失败");
			}
		}
	}

	@Override
	public List<QcType> queryQcType(HashMap<String, Object> cond) {
		return qcTypeMapper.queryQcType(cond);
	}

	@Transactional(rollbackFor=CCHException.class)
	@Override
	public void deleteSecondQcTypeList(List<QcType> qcTypeList)
			throws CCHException {
		HashMap<String, Object> cond = new HashMap<String, Object>();
		for (int i = 0; i < qcTypeList.size(); i++) {
			cond.put("qcTypeCode", qcTypeList.get(i).getId());
			List<Qc> qcList = qcMapper.queryQc(cond);
			if (qcList.size() > 0) {
				throw new CCHException("0", "分类下存在验收标准，无法删除");
			}
			try {
				cond.clear();
				cond.put("id", qcTypeList.get(i).getId());
				qcTypeMapper.deleteQcType(cond);
			} catch (Exception e) {
				e.printStackTrace();
				throw new CCHException("0", "删除分类信息失败");
			}
		}
	}

	@Override
	public List<Qc> queryQcByPage(Page page) {
		return qcMapper.queryQcByPage(page);
	}

	@Transactional(rollbackFor=CCHException.class)
	@Override
	public void insertQc(Qc qc) throws CCHException {
		HashMap<String, Object> cond = new HashMap<String, Object>();
		cond.put("qcCode", qc.getQcCode());
		cond.put("notQueryDelete", "1");
		List<Qc> qcList = qcMapper.queryQc(cond);
		if (qcList.size() > 0) {
			throw new CCHException("0", "该[" + qcList.get(0).getQcCode() + "]品项编码已存在，请重新输入");
		}
		try {
			String id = sequenceMapper.nextval("SEQ_ID").toString();
			qc.setId(id);
			Timestamp now = sequenceMapper.getSysDateTimestamp();
			qc.setCreateTime(now);
			qc.setEditTime(now);
			qcMapper.insertQc(qc);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CCHException("0", "新增品项信息失败");
		}
	}

	@Transactional(rollbackFor=CCHException.class)
	@Override
	public void updateQc(Qc qc) throws CCHException {
		HashMap<String, Object> cond = new HashMap<String, Object>();
		cond.put("id", qc.getId());
		cond.put("notQueryDelete", "1");
		List<Qc> qcList = qcMapper.queryQc(cond);
		if (qcList.size() > 0) {
			//当编码发生了改变，需要校验是否该编码已经被使用
			if (!qcList.get(0).getQcCode().equals(qc.getQcCode())) {
				cond.clear();
				cond.put("qcCode", qc.getQcCode());
				cond.put("notQueryDelete", "1");
				List<Qc> existQcList = qcMapper.queryQc(cond);
				if (existQcList.size() > 0) {
					throw new CCHException("0", "该[" + qcList.get(0).getQcCode() + "]品项编码已存在，请重新输入");
				}
			}
		}
		try {
			Timestamp now = sequenceMapper.getSysDateTimestamp();
			qc.setEditTime(now);
			qcMapper.updateQc(qc);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CCHException("0", "修改品项信息失败");
		}
	}

	@Transactional(rollbackFor=CCHException.class)
	@Override
	public void deleteQcLis(List<Qc> qcList) throws CCHException {
		HashMap<String, Object> cond = new HashMap<String, Object>();
		for (int i = 0; i < qcList.size(); i++) {
			cond.put("id", qcList.get(i).getId());
			qcMapper.deleteQc(cond);
		}
	}

	@Override
	public List<Qc> queryQc(HashMap<String, Object> cond) {
		return qcMapper.queryQc(cond);
	}

	@Transactional(rollbackFor=CCHException.class)
	@Override
	public void updateQcList(List<Qc> qcList) throws CCHException {
		HashMap<String, Object> cond = new HashMap<String, Object>();
		for (int i = 0; i < qcList.size(); i++) {
			Timestamp now = sequenceMapper.getSysDateTimestamp();
			qcList.get(i).setEditTime(now);
			qcMapper.updateQc(qcList.get(i));
		}
	}
}
