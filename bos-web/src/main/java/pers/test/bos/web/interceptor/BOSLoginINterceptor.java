package pers.test.bos.web.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import pers.test.bos.domain.TUser;
import pers.test.bos.utils.BOSUtils;

/*
 * 拦截器,确保只有已登录的用户才能访问数据等页面
 */
public class BOSLoginINterceptor extends MethodFilterInterceptor {

	// 拦截方法
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		// 从session中获取用户对象
		TUser user = BOSUtils.getLoginUser();
		if (user == null) {
			return "login";
		}
		// 放行
		return invocation.invoke();
	}

}
