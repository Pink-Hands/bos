package pers.test.bos.web.action;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import pers.test.bos.domain.QpNoticebill;
import pers.test.bos.service.INoticebillService;
import pers.test.bos.web.action.base.BaseAction;
import pers.test.crm.Customer;
import pers.test.crm.ICustomerService;

/**
 * 业务通知单管理
 */
@Controller
@Scope("prototype")
public class NoticebillAction extends BaseAction<QpNoticebill> {

	// 注入crm服务
	@Autowired
	private ICustomerService customerService;
	@Autowired
	private INoticebillService noticebillService;

	/**
	 * 根据电话查找用户
	 */
	public String findCustomerByTelephone() {
		String telephone = model.getTelephone();
		Customer customer = customerService.findCustomerByTelephone(telephone);
		this.java2Json(customer, new String[] {});
		return NONE;
	}

	/**
	 * 保存一个业务通知单,并尝试自动分单
	 */
	@RequiresPermissions("noticebill")
	public String add() {
		noticebillService.save(model);
		return "noticebill_add";
	}

	/**
	 * 查找人工分单的 通知单
	 */
	public String findnoassociations() {
		DetachedCriteria dc = pageBean.getDetachedCriteria();//获取分页查询中的查询条件
		dc.add(Restrictions.like("ordertype", "%" + QpNoticebill.ORDERTYPE_MAN + "%"));//人工分单
		dc.add(Restrictions.isNull("bcStaff"));//且没有分配取派员
		noticebillService.pageQuery(pageBean);
		String[] excludes = new String[] { "bcStaff", "TUser", "qpWorkbills","pickdate"};
		this.java2Json(pageBean, excludes);
		return NONE;
	}
	
	/**
	 * 人工分单
	 */
	@RequiresPermissions("diaodu-man")
	public String manadd() {
		noticebillService.mansave(model);
		return "diaodu";
	}

}
