package pers.test.bos.dao.base.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import pers.test.bos.dao.ISubareaDao;
import pers.test.bos.domain.BcSubarea;

@Repository
public class SubareaDaoImple extends BaseDaoImpl<BcSubarea> implements ISubareaDao {

	public List<Object> findSubareaGroupByProvince() {
		String hql = "SELECT r.province ,count(*) FROM BcSubarea s LEFT OUTER JOIN s.bcRegion r Group BY r.province";
		return (List<Object>) this.getHibernateTemplate().find(hql);
	}

	public void setByRegionid(String id) {
		String sql = "update bc_subarea set region_id = null where region_id = ?";
		Query query = this.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		query.setParameter(0, id);
		query.executeUpdate();
	}
	/**
	 * 将分区中的定区id置null
	 */
	public void setByDecidedzoneid(String id) {
		String sql = "update bc_subarea set decidedzone_id = null where decidedzone_id = ?";
		Query query = this.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		query.setParameter(0, id);
		query.executeUpdate();
	}

	public void edit(BcSubarea subarea, String oldId) {
		BcSubarea nowsubarea = findById(oldId);//查找原数据
		subarea.setBcDecidedzone(nowsubarea.getBcDecidedzone());//对新数据赋值定区id
		if(!subarea.getId().equals(oldId)) {
			//如果改变了id
			this.getHibernateTemplate().delete(nowsubarea);//删除旧数据
		}
		this.getHibernateTemplate().merge(subarea);//保存新数据
	}

}
