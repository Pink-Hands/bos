package pers.test.bos.service;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;

import pers.test.bos.dao.IFunctionDao;
import pers.test.bos.dao.IUserDao;
import pers.test.bos.domain.AuthFunction;
import pers.test.bos.domain.TUser;

public class BOSRealm extends AuthorizingRealm {

	@Autowired
	private IUserDao userDao;
	@Autowired
	private IFunctionDao functionDao;

	// 认证方法
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 根据用户名查询数据库密码,框架负责比对是否一致
		UsernamePasswordToken passwordToken = (UsernamePasswordToken) token;
		String username = passwordToken.getUsername();// 获取用户名
		TUser user = userDao.findUserByUsername(username);// 根据用户名查询用户
		if (user == null) {
			// 用户名不存在
			return null;
		}
		// 简单认证对象,将数据库查到的密码传递回去,让框架匹配是否一致
		AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
		return info;
	}

	// 授权方法,拦截器中 perms 时会调用此方法来查看谁具有权限
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//获取当前登录用户
		TUser user = (TUser) principals.getPrimaryPrincipal();
		//查询数据库
		List<AuthFunction> list = null;
		if(user.getId().equals("1")) {
			//如果当前用户为超级管理员
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AuthFunction.class);
			list = functionDao.findByCriteria(detachedCriteria);//查询权限列表所有权限
		}else {
			list = functionDao.findFunctionListByUserId(user.getId());
		}
		
		for (AuthFunction function : list) {
			info.addStringPermission(function.getCode());// 为用户授权
		}
		//根据用户表查询
		return info;
	}

}
