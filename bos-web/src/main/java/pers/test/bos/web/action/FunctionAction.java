package pers.test.bos.web.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import pers.test.bos.domain.AuthFunction;
import pers.test.bos.service.IFunctionService;
import pers.test.bos.web.action.base.BaseAction;

/**
 * 权限管理
 */
@Controller
@Scope("prototype")
public class FunctionAction extends BaseAction<AuthFunction> {

	@Autowired
	private IFunctionService service;

	/**
	 * 查询所有权限并返回json数据
	 */
	public String listajax() {
		List<AuthFunction> list = service.findAll();
		this.java2Json(list, new String[] { "parentFunction", "roles" });
		return NONE;
	}

	/**
	 * 保存权限添加
	 */
	@RequiresPermissions("function-add")
	public String add() {
		service.save(model);
		return LIST;
	}

	/**
	 * 分页查询
	 */
	@RequiresPermissions("function")
	public String pageQuery() {

		DetachedCriteria dc = pageBean.getDetachedCriteria();// 获取分页查询中的查询条件
		String code = model.getCode();// 获取传递来的查询参数
		String name = model.getName();
		String pageaction = model.getPageaction();
		String generatemenu = model.getGeneratemenu();
		String description = model.getDescription();
		
		if (StringUtils.isNotBlank(code)) {
			dc.add(Restrictions.like("code", "%" + code + "%"));// 如果参数存在则进行模糊查询
		}
		if (StringUtils.isNotBlank(name)) {
			dc.add(Restrictions.like("name", "%" + name + "%"));
		}
		if (StringUtils.isNotBlank(pageaction)) {
			dc.add(Restrictions.like("pageaction", "%" + pageaction + "%"));
		}
		if (StringUtils.isNotBlank(generatemenu) && generatemenu.equals("3")) {// 不生成菜单
			dc.add(Restrictions.like("generatemenu", "%" + 0 + "%"));
		}
		if (StringUtils.isNotBlank(generatemenu) && generatemenu.equals("4")) {// 生成菜单
			dc.add(Restrictions.like("generatemenu", "%" + 1 + "%"));
		}
		if (StringUtils.isNotBlank(description)) {
			dc.add(Restrictions.like("description", "%" + description + "%"));
		}

		service.pageQuery(pageBean);
		this.java2Json(pageBean, new String[] { "parentFunction", "roles", "children" });
		return NONE;
	}

	/**
	 * 查询基本功能菜单数据
	 */
	public String findMenu() {
		List<AuthFunction> list = service.findMenu();
		java2Json(list, new String[] { "parentFunction", "roles", "children" });
		return NONE;
	}
	/**
	 * 查询系统管理菜单数据
	 */
	public String findMenusystem() {
		List<AuthFunction> list = service.findMenusystem();
		java2Json(list, new String[] { "parentFunction", "roles", "children" });
		return NONE;
	}
	
	private String Ids;
	
	public void setIds(String ids) {
		Ids = ids;
	}

	/**
	 * 批量删除权限
	 */
	@RequiresPermissions("function-delete")
	public String deleteBatch() {
		service.deleteBatch(Ids);
		return LIST;
	}
	
	/**
	 * 修改数据
	 */
	@RequiresPermissions("function-edit")
	public String edit() {
		service.update(model);
		return LIST;
	}
	
	
}
