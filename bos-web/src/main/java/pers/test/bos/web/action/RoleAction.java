package pers.test.bos.web.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import pers.test.bos.domain.AuthRole;
import pers.test.bos.service.IRoleService;
import pers.test.bos.web.action.base.BaseAction;

/**
 * 角色管理
 */
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<AuthRole>{
	
	@Autowired
	private IRoleService service;
	
	public String functionIds;
	//属性驱动
	public void setFunctionIds(String functionIds) {
		this.functionIds = functionIds;
	}
	
	/**
	 * 添加角色
	 */
	@RequiresPermissions("role-add")
	public String add() {
		service.save(model,functionIds);
		return LIST;
	}
	
	/**
	 * 分页查询
	 */
	@RequiresPermissions("role")
	public String pageQuery() {
		DetachedCriteria dc = pageBean.getDetachedCriteria();// 获取分页查询中的查询条件
		String code = model.getCode();// 获取传递来的查询参数
		String name = model.getName();
		String description = model.getDescription();

		if (StringUtils.isNotBlank(code)) {
			dc.add(Restrictions.like("code", "%" + code + "%"));// 如果参数存在则进行模糊查询
		}
		if (StringUtils.isNotBlank(name)) {
			dc.add(Restrictions.like("name", "%" + name + "%"));
		}
		if (StringUtils.isNotBlank(description)) {
			dc.add(Restrictions.like("description", "%" + description + "%"));
		}
		
		service.pageQuery(pageBean);
		this.java2Json(pageBean, new String[] {"authFunctions","TUsers"});
		return NONE;
	}
	
	/**
	 * 查询角色数据并返回json
	 */
	public String listajax() {
		List<AuthRole> list = service.findAll();
		this.java2Json(list, new String[] {"authFunctions","TUsers"});
		return NONE;
	}
	
	private String Ids;
	
	public void setIds(String ids) {
		Ids = ids;
	}

	/**
	 * 批量删除角色
	 */
	@RequiresPermissions("role-delete")
	public String deleteBatch() {
		service.deleteBatch(Ids);
		return LIST;
	}
	
	private String roleId;
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * 根据id查找角色
	 */
	public String findById() {
		AuthRole role =  service.findById(roleId);
		java2Json(role, new String[]{"TUsers","children","roles","parentFunction"});
		return NONE;
	}
	
	/**
	 * 修改角色
	 */
	@RequiresPermissions("role-edit")
	public String edit() {
		service.update(model,functionIds);
		return LIST;
	}
}
