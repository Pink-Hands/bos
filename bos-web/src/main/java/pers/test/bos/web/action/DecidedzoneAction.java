package pers.test.bos.web.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import pers.test.bos.domain.BcDecidedzone;
import pers.test.bos.domain.BcStaff;
import pers.test.bos.service.IDecidedzoneService;
import pers.test.bos.web.action.base.BaseAction;
import pers.test.crm.Customer;
import pers.test.crm.ICustomerService;

/**
 * 定区
 */
@Controller
@Scope("prototype")
public class DecidedzoneAction extends BaseAction<BcDecidedzone> {

	private String[] subareaid;

	// 属性驱动
	public void setSubareaid(String[] subareaid) {
		this.subareaid = subareaid;
	}

	@Autowired
	private IDecidedzoneService decidedzoneService;

	/**
	 * 添加定区
	 */
	@RequiresPermissions("decidedzone-add")
	public String add() {
		decidedzoneService.save(model, subareaid);
		return LIST;
	}

	/**
	 * 分页查询
	 */
	@RequiresPermissions("decidedzone")
	public String pageQuery() {
		DetachedCriteria dc = pageBean.getDetachedCriteria();//获取分页查询中的查询条件
		String id = model.getId();//获取传递来的查询参数
		String name = model.getName();
		BcStaff staff = model.getBcStaff();
		String station = null;
		if(staff!=null) {
			station = model.getBcStaff().getStation();
		}
		
		if (StringUtils.isNotBlank(id)) {
			dc.add(Restrictions.like("id", "%" + id + "%"));//如果参数存在则进行模糊查询
		}
		if (StringUtils.isNotBlank(name)) {
			dc.add(Restrictions.like("name", "%" + name + "%"));
		}
		dc.createAlias("bcStaff", "s");
		if (StringUtils.isNotBlank(station)) {
			dc.add(Restrictions.like("s.station", "%" + station + "%"));
		}
		
		decidedzoneService.pageQuery(pageBean);
		// bcSubareas是定区中不需要展示的,而取派员需要显示改为立即加载,取派员中的bcDecidedzones则仍需要排除
		String[] excludes = new String[] { "currentPage", "detachedCriteria", "pageSize", "bcSubareas",
				"bcDecidedzones" };
		this.java2Json(pageBean, excludes);
		return NONE;
	}

	// 注入crm代理对象
	@Autowired
	private ICustomerService proxy;

	/**
	 * 查询没被关联的客户
	 */
	public String findListNotAssociaton() {
		List<Customer> list = proxy.findListNotAssociaton();
		this.java2Json(list, new String[] {});
		return NONE;
	}

	/**
	 * 查询已被关联的客户
	 */
	public String findListHasAssociaton() {
		String id = model.getId();
		List<Customer> list = proxy.findListHasAssociation(id);
		this.java2Json(list, new String[] {});
		return NONE;
	}

	// 属性驱动获取选中的客户id
	private List<Integer> customerIds;

	public List<Integer> getCustomerIds() {
		return customerIds;
	}

	public void setCustomerIds(List<Integer> customerIds) {
		this.customerIds = customerIds;
	}

	/**
	 * 将客户关联到定区
	 */
	@RequiresPermissions("decidedzone-association")
	public String assigncustomerstodecidedzone() {
		proxy.assigncustomerstodecidedzone(model.getId(), customerIds);
		return LIST;
	}

	private String ids;

	public void setIds(String ids) {
		this.ids = ids;
	}

	/**
	 * 批量删除定区
	 */
	@RequiresPermissions("decidedzone-delete")
	public String deleteBatch() {
		decidedzoneService.deleteBatch(ids);
		return LIST;
	}

	private String oldId;

	public void setOldId(String oldId) {
		this.oldId = oldId;
	}

	/**
	 * 修改定区
	 */
	@RequiresPermissions("decidedzone-edit")
	public String edit() {
		decidedzoneService.edit(model, subareaid, oldId);
		return LIST;
	}
}
