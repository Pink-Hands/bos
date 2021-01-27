package pers.test.bos.dao.base.impl;
import java.util.List;

import javax.mail.Session;

import org.springframework.stereotype.Repository;
import pers.test.bos.dao.IUserDao;
import pers.test.bos.domain.TUser;
/*
 * 实现UserDao中的方法
 */
@Repository//声明dao为bean,实现BaseDaoImpl<User>构造方法
public class UserDaoImpl extends BaseDaoImpl<TUser> implements IUserDao {
	
	/**
	 * 根据用户名和密码查询用户是否存在
	 */
	public TUser findUserByUsernameAndPassword(String username, String password) {
		String hql = "FROM TUser u WHERE u.username = ? AND u.password = ?";
		List<TUser> list = (List<TUser>) this.getHibernateTemplate().find(hql,username,password);
		if(list != null && list.size() > 0 ) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据用户名获取用户
	 */
	public TUser findUserByUsername(String username) {
		String hql = "FROM TUser u WHERE u.username = ?";
		List<TUser> list = (List<TUser>) this.getHibernateTemplate().find(hql,username);
		if(list != null && list.size() > 0 ) {
			return list.get(0);
		}
		return null;
	}

	

}
