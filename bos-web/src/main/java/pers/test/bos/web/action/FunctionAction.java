package pers.test.bos.web.action;

import java.util.List;

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
public class FunctionAction extends BaseAction<AuthFunction>{
	
	@Autowired
	private IFunctionService service;
	
	/**
	 * 查询所有权限并返回json数据
	 */
	public String listajax() {
		List<AuthFunction> list = service.findAll();
		this.java2Json(list, new String[] {"parentFunction","roles"});
		return NONE;
	}
	
	/**
	 * 保存权限添加
	 */
	public String add() {
		service.save(model);
		return LIST;
	}
	
	/**
	 * 分页查询
	 */
	public String pageQuery() {
		String page = model.getPage();
		pageBean.setCurrentPage(Integer.parseInt(page));
		service.pageQuery(pageBean);
		this.java2Json(pageBean, new String[] {"parentFunction","roles","children"});
		return NONE;
	}
	
	/**
	 * 查询菜单数据
	 */
	public String findMenu() {
		List<AuthFunction> list = service.findMenu();
		java2Json(list, new String[] {"parentFunction","roles","children"});
		return NONE;
	}
	
	
}
