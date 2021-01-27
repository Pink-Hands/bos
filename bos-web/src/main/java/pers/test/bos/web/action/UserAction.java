package pers.test.bos.web.action;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;

import pers.test.bos.dao.base.impl.UserDaoImpl;
import pers.test.bos.domain.AuthRole;
import pers.test.bos.domain.TUser;
import pers.test.bos.service.IUserService;
import pers.test.bos.utils.BOSUtils;
import pers.test.bos.utils.MD5Utils;
import pers.test.bos.utils.PageBean;
import pers.test.bos.web.action.base.BaseAction;

@Controller // 控制器
@Scope("prototype") // 多例
public class UserAction extends BaseAction<TUser> {

	private String checkcode;// 接收验证码

	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}

	@Autowired
	private IUserService userService;

	/**
	 * 处理登录界面,使用shiro框架提供的方式
	 */
	public String login() {
		// 取出验证码
		String validdatecode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		if (StringUtils.isNotBlank(checkcode) && checkcode.equalsIgnoreCase(validdatecode)) {
			Subject subject = SecurityUtils.getSubject();// 获得当前登录用户对象
			AuthenticationToken token = new UsernamePasswordToken(model.getUsername(),
					MD5Utils.md5(model.getPassword()));
			try {
				subject.login(token);// 跳转到BOSRealm进行认证,如果认证失败会抛出异常
				TUser user = (TUser) subject.getPrincipal();// 取出user
				ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);// 放入session
			} catch (AuthenticationException e) {
				e.printStackTrace();
				this.addActionError("用户名或者密码输入错误！");
				return LOGIN;
			}
			return HOME;
		} else {
			// 验证码错误,提示后跳转回登录页面
			this.addActionError("输入的验证码错误!");
			return LOGIN;
		}
	}

	/**
	 * 注销
	 */
	public String logout() {
		ServletActionContext.getRequest().getSession().invalidate();
		return LOGIN;
	}

	/**
	 * 修改密码
	 */
	public String editPassword() throws IOException {
		String f = "1";
		// 获取当前用户
		TUser user = BOSUtils.getLoginUser();
		try {
			userService.editPassword(user.getId(), model.getPassword());
		} catch (Exception e) {
			f = "0";
			e.printStackTrace();
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");// 告知返回的f是字符串而不是对象
		ServletActionContext.getResponse().getWriter().printf(f);
		return NONE;
	}

	private String[] roleIds;

	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}

	/**
	 * 保存用户
	 */
	@RequiresPermissions("user-add")
	public String add() {
		userService.save(model, roleIds);
		return LIST;
	}

	private String roleNames;

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	/**
	 * 分页查询
	 */
	@RequiresPermissions("user")
	public String pageQuery() {
		DetachedCriteria dc = pageBean.getDetachedCriteria();// 获取分页查询中的查询条件
		String username = model.getUsername();// 获取传递来的查询参数
		String telephone = model.getTelephone();
		String station = model.getStation();
		String gender = model.getGender();
		Double salary = model.getSalary();
		String remark = model.getRemark();

		if (StringUtils.isNotBlank(username)) {
			dc.add(Restrictions.like("username", "%" + username + "%"));// 如果参数存在则进行模糊查询
		}
		if (StringUtils.isNotBlank(telephone)) {
			dc.add(Restrictions.like("telephone", "%" + telephone + "%"));
		}
		if (StringUtils.isNotBlank(remark)) {
			dc.add(Restrictions.like("remark", "%" + remark + "%"));
		}
		if (StringUtils.isNotBlank(station)) {
			dc.add(Restrictions.like("station", "%" + station + "%"));
		}
		if (StringUtils.isNotBlank(gender)) {
			dc.add(Restrictions.like("gender", "%" + gender + "%"));
		}
		if (salary != null) {
			dc.add(Restrictions.like("salary", "%" + salary + "%"));
		}
		if (StringUtils.isNotBlank(roleNames)) {
			dc.createAlias("authRoles", "r");

			dc.add(Restrictions.like("r.name", "%" + roleNames + "%"));
			userService.pageQuery(pageBean);

		}

		userService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[] { "qpNoticebills", "authRoles", "birthday" });
		return NONE;
	}

	private String Ids;

	public void setIds(String ids) {
		Ids = ids;
	}

	/**
	 * 删除用户
	 */
	@RequiresPermissions("user-delete")
	public String deleteBatch() {
		userService.deleteBatch(Ids);
		return LIST;
	}

	private String userId;

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 根据id回显数据
	 */
	public String findById() {
		TUser user = userService.findById(userId);
		this.java2Json(user, new String[] { "qpNoticebills", "TUsers", "authFunctions", "birthday" });
		return NONE;
	}

	private String birthdayString;

	public void setBirthdayString(String birthdayString) {
		this.birthdayString = birthdayString;
	}

	/**
	 * 修改用户
	 */
	@RequiresPermissions("user-edit")
	public String edit() {
		userService.update(model, roleIds, birthdayString);
		return LIST;
	}

	/**
	 * 查找已经注册的所有用户名
	 */
	public String findAllName() {
		List<TUser> list = userService.findAll();
		String allNames = "";
		for (TUser user : list) {
			String name = user.getUsername();
			allNames += name + ",";
		}
		int length = allNames.length();
		allNames = allNames.substring(0, length - 1);// 去除最后的,
		ServletActionContext.getResponse().setContentType("text/plain;charset=utf-8");// 设置输出为文本
		try {
			ServletActionContext.getResponse().getWriter().print(allNames);// 将从数据库查询到的数据输回
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;
	}

}
