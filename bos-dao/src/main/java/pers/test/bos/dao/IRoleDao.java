package pers.test.bos.dao;

import pers.test.bos.dao.base.IBaseDao;
import pers.test.bos.domain.AuthRole;

public interface IRoleDao extends IBaseDao<AuthRole> {

	public void deleteRole(String[] roleIds);

}
