package pers.test.bos.dao;

import java.util.List;

import pers.test.bos.dao.base.IBaseDao;
import pers.test.bos.domain.BcSubarea;

public interface ISubareaDao extends IBaseDao<BcSubarea>{


	public List<Object> findSubareaGroupByProvince();

	public void setByRegionid(String id);

	public void edit(BcSubarea subarea, String oldId);
	/**
	 * 将分区中的定区id置null
	 */
	public void setByDecidedzoneid(String id);


}
