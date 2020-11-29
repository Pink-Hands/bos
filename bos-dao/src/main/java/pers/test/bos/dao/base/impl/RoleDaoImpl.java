package pers.test.bos.dao.base.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import pers.test.bos.dao.IRoleDao;
import pers.test.bos.domain.AuthRole;

@Repository
public class RoleDaoImpl extends BaseDaoImpl<AuthRole> implements IRoleDao {

	/**
	 * 删除角色时,删除相应用户中的角色
	 */
	public void deleteRole(String[] roleIds) {
		String sql = "delete from user_role where role_id = ?";
		for (String id : roleIds) {
			Query sqlquery = this.getSessionFactory().getCurrentSession().createSQLQuery(sql);
			sqlquery.setParameter(0, id);
			sqlquery.executeUpdate();
		}
	}

	

}
