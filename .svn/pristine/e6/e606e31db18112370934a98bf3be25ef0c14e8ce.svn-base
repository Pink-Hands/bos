package pers.test.bos.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pers.test.bos.dao.IStaffDao;
import pers.test.bos.domain.BcStaff;
import pers.test.bos.service.IStaffService;
import pers.test.bos.utils.PageBean;

@Service
@Transactional
public class StaffServiceImpl implements IStaffService {

	@Autowired
	private IStaffDao staffDao;

	@Override
	public void save(BcStaff model) {
		staffDao.save(model);
	}

	/**
	 * 分页查询
	 */
	public void pageQuery(PageBean pageBean) {
		staffDao.pageQuery(pageBean);
	}

	/**
	 * 取派员批量删除,将deltag改为1
	 */
	public void deleteBatch(String ids) {
		if (StringUtils.isNotBlank(ids)) {
			String[] staffIds = ids.split(",");//分割回数组
			for(String id:staffIds) {
				staffDao.executeUpadte("staff.delete", '1', id);
			}
		}
	}

	/**
	 * 取派员批量恢复
	 */
	public void restoreBatch(String ids) {
		if (StringUtils.isNotBlank(ids)) {
			String[] staffIds = ids.split(",");//分割回数组
			for(String id:staffIds) {
				staffDao.executeUpadte("staff.delete", '0', id);
			}
		}
	}

	/**
	 * 根据id查询数据
	 */
	public BcStaff findById(String id) {
		return staffDao.findById(id);
	}

	/**
	 * 修改数据
	 */
	public void update(BcStaff staff) {
		staffDao.update(staff);
	}

	/**
	 * 查询所有未删除的取派员
	 */
	public List<BcStaff> findListNotDelete() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BcStaff.class);//对象
		detachedCriteria.add(Restrictions.eq("deltag",'0'));//添加过滤条件,寻找deltag = 0的
		return staffDao.findByCriteria(detachedCriteria);//返回查询出来的结果
	}

}
