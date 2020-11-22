package pers.test.bos.dao.base.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import pers.test.bos.dao.IFunctionDao;
import pers.test.bos.domain.AuthFunction;

@Repository
public class FunctionDaoImpl extends BaseDaoImpl<AuthFunction> implements IFunctionDao {

	public List<AuthFunction> findAll(){
		//重写findAll
		String hql = "FROM AuthFunction f WHERE f.parentFunction IS NULL";
		List<AuthFunction> list = (List<AuthFunction>) this.getHibernateTemplate().find(hql);
		return list;
	}

	/**
	 * 根据用户id查询权限
	 */
	public List<AuthFunction> findFunctionListByUserId(String userId) {
		String hql = "SELECT DISTINCT f FROM AuthFunction f LEFT OUTER JOIN f.roles r LEFT OUTER JOIN r.TUsers u WHERE u.id = ?";
		List<AuthFunction> list = (List<AuthFunction>) this.getHibernateTemplate().find(hql, userId);
		return list;
	}

	/**
	 * 查所有菜单
	 */
	public List<AuthFunction> findAllMenu() {
		String hql = "FROM AuthFunction f WHERE f.generatemenu = '1' ORDER BY f.zindex ASC";
		List<AuthFunction> list = (List<AuthFunction>) this.getHibernateTemplate().find(hql);
		return list;
	}

	/**
	 * 根据用户id查菜单
	 */
	public List<AuthFunction> findMenuByUserId(String userId) {
		String hql = "SELECT DISTINCT f FROM AuthFunction f LEFT OUTER JOIN f.roles r LEFT OUTER JOIN r.TUsers u WHERE u.id = ? AND f.generatemenu = '1' ORDER BY f.zindex ASC";
		List<AuthFunction> list = (List<AuthFunction>) this.getHibernateTemplate().find(hql, userId);
		return list;
	}

}
