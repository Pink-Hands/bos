package pers.test.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import pers.test.bos.domain.BcRegion;
import pers.test.bos.domain.BcStaff;
import pers.test.bos.service.IStaffService;
import pers.test.bos.web.action.base.BaseAction;

/**
 * 取派员管理
 */
@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<BcStaff> {

	@Autowired
	private IStaffService staffService;

	/**
	 * 添加取派员
	 */
	@RequiresPermissions("staff-add")
	public String add() {
		staffService.save(model);
		return LIST;
	}

	/**
	 * 分页查询
	 */
	@RequiresPermissions("staff")
	public String pageQuery(){
		
		DetachedCriteria dc = pageBean.getDetachedCriteria();//获取分页查询中的查询条件
		String name = model.getName();//获取传递来的查询参数
		String telephone = model.getTelephone();
		String station = model.getStation();
		Character haspda = model.getHaspda();
		String standard = model.getStandard();
		

		if (StringUtils.isNotBlank(name)) {
			dc.add(Restrictions.like("name", "%" + name + "%"));//如果参数存在则进行模糊查询
		}
		if (StringUtils.isNotBlank(telephone)) {
			dc.add(Restrictions.like("telephone", "%" + telephone + "%"));
		}
		if (StringUtils.isNotBlank(station)) {
			dc.add(Restrictions.like("station", "%" + station + "%"));
		}
		if (haspda.equals('2')){//有pda
			dc.add(Restrictions.eq("haspda", '1'));
		}
		if (haspda.equals('3')){//无pda
			dc.add(Restrictions.eq("haspda", '0'));
		}
		if (StringUtils.isNotBlank(standard)) {
			dc.add(Restrictions.like("standard", "%" + standard + "%"));
		}

		staffService.pageQuery(pageBean);
		String[] excludes = new String[] { "currentPage", "detachedCriteria", "pageSize", "bcDecidedzones" };
		this.java2Json(pageBean, excludes);
		return NONE;
	}

	private String ids;

	/**
	 * 取派员批量删除
	 */
	@RequiresPermissions("staff-delete")
	public String deleteBatch() {
		staffService.deleteBatch(ids);
		return LIST;
	}

	/**
	 * 取派员批量恢复
	 */
	@RequiresPermissions("staff-restore")
	public String restoreBatch() {
		staffService.restoreBatch(ids);
		return LIST;
	}

	/**
	 * 取派员信息修改
	 */
	@RequiresPermissions("staff-edit")
	public String edit() {
		// 查询原数据,传递来的数据会放在baseAction的model中
		BcStaff staff = staffService.findById(model.getId());
		// 数据覆盖
		staff.setName(model.getName());
		staff.setTelephone(model.getTelephone());
		staff.setHaspda(model.getHaspda());
		staff.setStandard(model.getStandard());
		staff.setStation(model.getStation());
		// 更新数据
		staffService.update(staff);
		return LIST;
	}

	/**
	 * 查询所有未删除的取派员
	 */
	public String listajax() {
		List<BcStaff> list = staffService.findListNotDelete();
		this.java2Json(list, new String[] { "bcDecidedzones" });
		return NONE;
	}


	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
}
