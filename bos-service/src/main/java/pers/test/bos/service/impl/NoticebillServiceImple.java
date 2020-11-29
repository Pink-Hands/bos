package pers.test.bos.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pers.test.bos.dao.IDecidedzoneDao;
import pers.test.bos.dao.INoticebillDao;
import pers.test.bos.dao.IStaffDao;
import pers.test.bos.dao.IWorkbillDao;
import pers.test.bos.domain.BcDecidedzone;
import pers.test.bos.domain.BcStaff;
import pers.test.bos.domain.QpNoticebill;
import pers.test.bos.domain.QpWorkbill;
import pers.test.bos.domain.TUser;
import pers.test.bos.service.INoticebillService;
import pers.test.bos.utils.BOSUtils;
import pers.test.bos.utils.PageBean;
import pers.test.crm.ICustomerService;

@Service
@Transactional
public class NoticebillServiceImple implements INoticebillService {

	@Autowired
	private INoticebillDao noticebillDao;

	@Autowired
	private ICustomerService customerService;

	@Autowired
	private IDecidedzoneDao decidedzoneDao;

	@Autowired
	private IWorkbillDao workbillDao;

	@Autowired
	private IStaffDao staffDao;

	/**
	 * 保存一个业务通知单,并尝试自动分单
	 */
	public void save(QpNoticebill model) {
		TUser user = BOSUtils.getLoginUser();
		model.setTUser(user);// 保存当前登录的用户

		String pickaddress = model.getPickaddress();// 获取取件地址
		String decidedzoneId = customerService.findDecidedzoneIdByAddress(pickaddress);
		if (decidedzoneId != null) {
			// 查询到定区id,可以自动分单
			BcDecidedzone decidedzone = decidedzoneDao.findById(decidedzoneId);// 根据id查询定区
			BcStaff staff = decidedzone.getBcStaff();// 根据定区查询取派员
			model.setBcStaff(staff);// 保存取派员
			model.setOrdertype(QpNoticebill.ORDERTYPE_AUTO);// 设定分单类型为自动分单

			QpWorkbill workbill = new QpWorkbill(staff);// 根据取派员创建工单
			workbill.setAttachbilltimes(0);// 追单次数
			workbill.setBuildtime(new Timestamp(System.currentTimeMillis()));// 创建时间
			workbill.setQpNoticebill(model);// 关联业务通知单
			workbill.setPickstate(QpWorkbill.PICKSTATE_NO);// 取件状态:未取件
			workbill.setRemark(model.getRemark());// 备注信息
			workbill.setType(QpWorkbill.TYPE_1);// 工单类型:新单
			workbillDao.save(workbill);
		} else {
			// 没查询到
			model.setOrdertype(QpNoticebill.ORDERTYPE_MAN);
		}
		noticebillDao.save(model);
	}

	public void pageQuery(PageBean pageBean) {
		noticebillDao.pageQuery(pageBean);
	}

	public void mansave(QpNoticebill model) {
		QpNoticebill noticebill = noticebillDao.findById(model.getId());
		noticebill.setBcStaff(model.getBcStaff());

		QpWorkbill workbill = new QpWorkbill(model.getBcStaff());// 根据取派员创建工单
		workbill.setAttachbilltimes(0);// 追单次数
		workbill.setBuildtime(new Timestamp(System.currentTimeMillis()));// 创建时间
		workbill.setQpNoticebill(noticebill);// 关联业务通知单
		workbill.setPickstate(QpWorkbill.PICKSTATE_NO);// 取件状态:未取件
		workbill.setRemark(model.getRemark());// 备注信息
		workbill.setType(QpWorkbill.TYPE_1);// 工单类型:新单
		workbillDao.save(workbill);
		noticebillDao.save(noticebill);
	}
}
