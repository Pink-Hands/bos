package pers.test.bos.dao.base.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import pers.test.bos.dao.ISubareaDao;
import pers.test.bos.domain.BcSubarea;


@Repository
public class SubareaDaoImple extends BaseDaoImpl<BcSubarea> implements ISubareaDao {

	public List<Object> findSubareaGroupByProvince() {
		String hql = "SELECT r.province ,count(*) FROM BcSubarea s LEFT OUTER JOIN s.bcRegion r Group BY r.province";
		return (List<Object>) this.getHibernateTemplate().find(hql);
	}

}
