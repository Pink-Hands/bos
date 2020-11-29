package pers.test.bos.web.action;

import java.util.Date;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import pers.test.bos.domain.BcStaff;
import pers.test.bos.domain.QpNoticebill;
import pers.test.bos.domain.QpWorkbill;
import pers.test.bos.service.IWorkbillService;
import pers.test.bos.web.action.base.BaseAction;

/**
 * 工单管理
 */
@Controller
@Scope("prototype")
public class WorkbillAction extends BaseAction<QpWorkbill> {

	@Autowired
	private IWorkbillService workbillService;

	private Date buildtimestart;

	public void setBuildtimestart(Date buildtimestart) {
		this.buildtimestart = buildtimestart;
	}

	private Date buildtimeend;

	public void setBuildtimeend(Date buildtimeend) {
		this.buildtimeend = buildtimeend;
	}

	/**
	 * 分页查询
	 */
	@RequiresPermissions("workbill")
	public String pageQuery() {
		DetachedCriteria dc = pageBean.getDetachedCriteria();// 获取分页查询中的查询条件
		
		BcStaff staff = model.getBcStaff();
		QpNoticebill noticebill = model.getQpNoticebill();
		String clienttelephone = null;
		String staffname = null;
		if (staff != null) {
			staffname = staff.getName();
			dc.createAlias("bcStaff", "s");
			if (StringUtils.isNotBlank(staffname)) {
				dc.add(Restrictions.like("s.name", "%" + staffname + "%"));
			}
		}
		if (noticebill != null) {
			clienttelephone = noticebill.getTelephone();
			dc.createAlias("qpNoticebill", "n");
			if (StringUtils.isNotBlank(clienttelephone)) {
				dc.add(Restrictions.like("n.telephone", "%" + clienttelephone + "%"));
			}
		}
		if (buildtimestart != null) {
			dc.add(Restrictions.ge("buildtime", buildtimestart));
		}
		if (buildtimeend != null) {
			dc.add(Restrictions.le("buildtime", buildtimeend));
		}

		workbillService.pageQuery(pageBean);
		String[] excludes = new String[] { "TUser", "qpWorkbills", "bcDecidedzones" ,"pickdate"};
		this.java2Json(pageBean, excludes);
		return NONE;
	}

	private String ids;

	public void setIds(String ids) {
		this.ids = ids;
	}

	/**
	 * 追单
	 */
	@RequiresPermissions("workbill-attachbill")
	public String attachbill() {
		workbillService.attachbill(ids);
		return LIST;
	}

	/**
	 * 销单
	 */
	@RequiresPermissions("workbill-delete")
	public String deleteBatch() {
		workbillService.deleteBatch(ids);
		return LIST;
	}

}
