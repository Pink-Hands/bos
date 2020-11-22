package pers.test.bos.dao.base;

import java.io.Serializable;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.hibernate.criterion.DetachedCriteria;

import pers.test.bos.utils.PageBean;

/**
 * 持久层通用接口
 */
public interface IBaseDao<T> {
	public void save(T entity);

	public void delete(T entity);

	public void update(T entity);

	public void saveOrUpdate(T entity);

	public T findById(Serializable id);

	public List<T> findAll();

	// 根据条件查询
	public List<T> findByCriteria(DetachedCriteria detachedCriteria);

	// 更新数据,queryName写在Use.hbm.xml中
	public void executeUpadte(String queryName, Object... objects);

	// 分页
	public void pageQuery(PageBean pageBean);

}
