package pers.test.bos.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pers.test.bos.dao.IUserDao;
import pers.test.bos.domain.AuthRole;
import pers.test.bos.domain.TUser;
import pers.test.bos.service.IUserService;
import pers.test.bos.utils.MD5Utils;
import pers.test.bos.utils.PageBean;

@Service // 服务
@Transactional // 事务
public class UserServiceImpl implements IUserService {
	@Autowired
	private IUserDao userDao;// 引入基本方法

	public TUser login(TUser user) {
		// 用MD5进行加密
		String password = MD5Utils.md5(user.getPassword());
		return userDao.findUserByUsernameAndPassword(user.getUsername(), password);
	}

	// 修改密码
	public void editPassword(String id, String password) {
		password = MD5Utils.md5(password);
		userDao.executeUpadte("user.editpassword", password, id);
	}

	/**
	 * 添加用户同时关联角色
	 */
	public void save(TUser user, String[] roleIds) {
		String password = user.getPassword();
		password = MD5Utils.md5(password);
		user.setPassword(password);
		userDao.save(user);
		if (roleIds != null && roleIds.length > 0) {
			for (String roleId : roleIds) {
				AuthRole role = new AuthRole(roleId);// 构造托管对象
				user.getAuthRoles().add(role);// 关联角色
			}
		}
	}

	public void pageQuery(PageBean pageBean) {
		userDao.pageQuery(pageBean);
	}

	/**
	 * 批量删除
	 */
	public void deleteBatch(String ids) {
		if (StringUtils.isNotBlank(ids)) {
			String[] staffIds = ids.split(",");// 分割回数组
			for (String id : staffIds) {
				TUser user = new TUser(id);
				userDao.delete(user);
			}
		}
	}

	/**
	 * 根据id回显用户数据
	 */
	public TUser findById(String userId) {
		return userDao.findById(userId);
	}

	/**
	 * 修改用户数据
	 */
	public void update(TUser user, String[] roleIds, String birthdayString) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date birthday = null;
		try {
			birthday = sdf.parse(birthdayString);// 将字符串转化为日期
			user.setBirthday(birthday);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (user.getPassword().length() != 32) {
			// 如果密码不是32位,说明改变了密码
			String password = user.getPassword();
			password = MD5Utils.md5(password);
			user.setPassword(password);
		}
		userDao.update(user);// 修改数据
		if (roleIds != null && roleIds.length > 0) {
			for (String roleId : roleIds) {
				AuthRole role = new AuthRole(roleId);// 构造托管对象
				user.getAuthRoles().add(role);// 关联角色
			}
		}
	}

	/**
	 * 查找所有用户
	 */
	public List<TUser> findAll() {
		return userDao.findAll();
	}
}
