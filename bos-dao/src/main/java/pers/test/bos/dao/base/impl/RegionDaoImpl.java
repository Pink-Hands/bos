package pers.test.bos.dao.base.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import pers.test.bos.dao.IRegionDao;
import pers.test.bos.domain.BcRegion;

@Repository
public class RegionDaoImpl extends BaseDaoImpl<BcRegion> implements IRegionDao {

	/**
	 * 根据q进行区域的模糊查询
	 */
	public List<BcRegion> findListByQ(String q) {
		String hql = "FROM BcRegion r WHERE r.shortcode LIKE ? OR r.citycode LIKE ? OR r.province LIKE ? OR r.city LIKE ? OR r.district LIKE ?";
		List<BcRegion> list = (List<BcRegion>) this.getHibernateTemplate().find(hql, "%" + q + "%", "%" + q + "%",
				"%" + q + "%", "%" + q + "%", "%" + q + "%");
		return list;
	}

}
