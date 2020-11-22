package pers.test.bos.web.action;

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

	//注入crm服务
	@Autowired
	private ICustomerService customerService;
	/**
	 * 根据电话查找用户
	 */
	public String findCustomerByTelephone() {
		String telephone = model.getTelephone();
		Customer customer = customerService.findCustomerByTelephone(telephone);
		this.java2Json(customer, new String[] {});
		return NONE;
	}
	
	@Autowired
	private INoticebillService noticebillService;
	
	/**
	 * 保存一个业务通知单,并尝试自动分单
	 */
	public String add() {
		noticebillService.save(model);
		return "noticebill_add";
	}
}
