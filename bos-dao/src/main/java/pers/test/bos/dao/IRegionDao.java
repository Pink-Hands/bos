package pers.test.bos.dao;

import java.util.List;

import pers.test.bos.dao.base.IBaseDao;
import pers.test.bos.domain.BcRegion;

public interface IRegionDao extends IBaseDao<BcRegion> {

	public List<BcRegion> findListByQ(String q);
	
}
