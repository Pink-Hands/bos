package pers.test.bos.web.action;

import java.util.List;

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
	public String add() {
		service.save(model,functionIds);
		return LIST;
	}
	
	/**
	 * 分页查询
	 */
	public String pageQuery() {
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
	
}
