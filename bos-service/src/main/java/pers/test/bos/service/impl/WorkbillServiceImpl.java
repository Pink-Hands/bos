package pers.test.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pers.test.bos.dao.IWorkbillDao;
import pers.test.bos.domain.BcRegion;
import pers.test.bos.domain.QpWorkbill;
import pers.test.bos.service.IWorkbillService;
import pers.test.bos.utils.PageBean;

@Service
@Transactional
public class WorkbillServiceImpl implements IWorkbillService {

	@Autowired
	private IWorkbillDao workbillDao;

	public void pageQuery(PageBean pageBean) {
		workbillDao.pageQuery(pageBean);
	}

	/**
	 * 追单
	 */
	public void attachbill(String ids) {
		String[] workbillIds = ids.split(",");// 分割回数组
		for (String id : workbillIds) {
			QpWorkbill workbill = workbillDao.findById(id);
			workbill.setType(QpWorkbill.TYPE_2);// 设置为追单
			Integer attachbilltimes = workbill.getAttachbilltimes();
			attachbilltimes += 1;
			workbill.setAttachbilltimes(attachbilltimes);// 追单次数+1
			workbillDao.save(workbill);
		}
	}

	/**
	 * 销单
	 */
	public void deleteBatch(String ids) {
		String[] workbillIds = ids.split(",");// 分割回数组
		for (String id : workbillIds) {
			QpWorkbill workbill = workbillDao.findById(id);
			workbill.setType(QpWorkbill.TYPE_4);// 设置为销单
			workbillDao.save(workbill);
		}
	}
}
