package pers.test.bos.dao;

import java.util.List;

import pers.test.bos.dao.base.IBaseDao;
import pers.test.bos.domain.BcSubarea;

public interface ISubareaDao extends IBaseDao<BcSubarea>{

	public List<Object> findSubareaGroupByProvince();

}
