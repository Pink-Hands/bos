package pers.test.bos.utils;

import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import pers.test.bos.domain.TUser;

/*
 * 工具类
 * 简化对session和登录用户的获取
 */
public class BOSUtils {
	
	// 获取session
	public static HttpSession getSession() {

		return ServletActionContext.getRequest().getSession();
	}

	//获取session中登录的用户
	public static TUser getLoginUser() {
		return (TUser) getSession().getAttribute("loginUser");
	}
}
