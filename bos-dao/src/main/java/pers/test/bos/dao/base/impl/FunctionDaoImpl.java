package pers.test.bos.dao.base.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import pers.test.bos.dao.IFunctionDao;
import pers.test.bos.domain.AuthFunction;

@Repository
public class FunctionDaoImpl extends BaseDaoImpl<AuthFunction> implements IFunctionDao {

	public List<AuthFunction> findAll() {
		// 重写findAll
		String hql = "FROM AuthFunction f WHERE f.parentFunction IS NULL";
		List<AuthFunction> list = (List<AuthFunction>) this.getHibernateTemplate().find(hql);
		return list;
	}

	/**
	 * 根据用户id查询权限
	 */
	public List<AuthFunction> findFunctionListByUserId(String userId) {
		String hql = "SELECT DISTINCT f FROM AuthFunction f LEFT OUTER JOIN f.roles r LEFT OUTER JOIN r.TUsers u WHERE u.id = ? ORDER BY f.zindex ASC";
		List<AuthFunction> list = (List<AuthFunction>) this.getHibernateTemplate().find(hql, userId);
		return list;
	}

	/**
	 * 查基本功能所有菜单
	 */
	public List<AuthFunction> findAllMenu() {
		String hql = "FROM AuthFunction f WHERE f.generatemenu = '1' AND f.id LIKE '1%' ORDER BY f.zindex ASC";
		List<AuthFunction> list = (List<AuthFunction>) this.getHibernateTemplate().find(hql);
		return list;
	}
	/**
	 * 查系统管理所有菜单
	 */
	public List<AuthFunction> findAllMenusystem() {
		String hql = "FROM AuthFunction f WHERE f.generatemenu = '1' AND f.id LIKE '2%' ORDER BY f.zindex ASC";
		List<AuthFunction> list = (List<AuthFunction>) this.getHibernateTemplate().find(hql);
		return list;
	}

	/**
	 * 根据用户id查基本功能菜单
	 */
	public List<AuthFunction> findMenuByUserId(String userId) {
		String hql = "SELECT DISTINCT f FROM AuthFunction f LEFT OUTER JOIN f.roles r LEFT OUTER JOIN r.TUsers u WHERE u.id = ? AND f.generatemenu = '1' AND f.id LIKE '1%' ORDER BY f.zindex ASC";
		List<AuthFunction> list = (List<AuthFunction>) this.getHibernateTemplate().find(hql, userId);
		return list;
	}
	/**
	 * 根据用户id查系统管理菜单
	 */
	public List<AuthFunction> findMenuByUserIdsystem(String userId) {
		String hql = "SELECT DISTINCT f FROM AuthFunction f LEFT OUTER JOIN f.roles r LEFT OUTER JOIN r.TUsers u WHERE u.id = ? AND f.generatemenu = '1' AND f.id LIKE '2%' ORDER BY f.zindex ASC";
		List<AuthFunction> list = (List<AuthFunction>) this.getHibernateTemplate().find(hql, userId);
		return list;
	}

	/**
	 * 修改数据
	 */
	public void edit(AuthFunction function) {
		String sql = "update auth_function set name = ? , code = ? , description = ? , page = ? , generatemenu = ? , zindex = ? , pid = ? where id = ?";
		String id = function.getId();
		String name = function.getName();
		String code = function.getCode();
		String description = function.getDescription();
		String page = function.getPageaction();
		String generatemenu = function.getGeneratemenu();
		Integer zindex = function.getZindex();
		String pid = function.getParentFunction().getId();
		if (pid.equals("0")) {
			pid = null;
		}
		Query sqlquery = this.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		sqlquery.setParameter(0, name);
		sqlquery.setParameter(1, code);
		sqlquery.setParameter(2, description);
		sqlquery.setParameter(3, page);
		sqlquery.setParameter(4, generatemenu);
		sqlquery.setParameter(5, zindex);
		sqlquery.setParameter(6, pid);
		sqlquery.setParameter(7, id);
		sqlquery.executeUpdate();
	}

}
