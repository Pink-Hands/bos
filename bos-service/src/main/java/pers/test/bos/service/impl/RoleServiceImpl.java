package pers.test.bos.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.ehcache.hibernate.HibernateUtil;
import pers.test.bos.dao.IRoleDao;
import pers.test.bos.domain.AuthFunction;
import pers.test.bos.domain.AuthRole;
import pers.test.bos.service.IRoleService;
import pers.test.bos.utils.PageBean;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService {

	@Autowired
	private IRoleDao dao;

	/**
	 * 保存角色同时关联权限
	 */
	public void save(AuthRole role, String functionIds) {
		dao.save(role);// 保存角色
		if (StringUtils.isNoneBlank(functionIds)) {
			String[] fIds = functionIds.split(",");// 用,分割
			for (String functionId : fIds) {
				// 手动构造权限对象
				AuthFunction Function = new AuthFunction(functionId);
				// 角色关联权限
				role.getAuthFunctions().add(Function);
			}
		}

	}

	public void pageQuery(PageBean pageBean) {
		dao.pageQuery(pageBean);
	}

	public List<AuthRole> findAll() {
		return dao.findAll();
	}

	/**
	 * 批量删除角色
	 */
	public void deleteBatch(String ids) {
		if (StringUtils.isNotBlank(ids)) {
			String[] roleIds = ids.split(",");// 分割回数组
			dao.deleteRole(roleIds);
			for (String id : roleIds) {
				AuthRole role = new AuthRole(id);
				dao.delete(role);
			}
		}
	}

	public AuthRole findById(String roleId) {
		return dao.findById(roleId);
	}

	public void update(AuthRole role, String functionIds) {
		dao.saveOrUpdate(role);
		if (StringUtils.isNoneBlank(functionIds)) {
			String[] fIds = functionIds.split(",");// 用,分割
			for (String functionId : fIds) {
				// 手动构造权限对象
				AuthFunction Function = new AuthFunction(functionId);
				// 角色关联权限
				role.getAuthFunctions().add(Function);
			}
		}
	}

}
