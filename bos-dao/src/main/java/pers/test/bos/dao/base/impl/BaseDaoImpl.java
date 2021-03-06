package pers.test.bos.dao.base.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import pers.test.bos.dao.base.IBaseDao;
import pers.test.bos.utils.PageBean;

/**
 * 持久层接口的实现,继承模板
 */
public class BaseDaoImpl<T> extends HibernateDaoSupport implements IBaseDao<T> {
	// 代表某个实体类,User
	private Class<T> entityClass;

	@Resource // 根据类型注入spring工厂中的会话工厂对象sessionFactory
	public void setMySessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	// 在构造方法里动态获得entityClass
	public BaseDaoImpl() {
		// 获取UserDaoImpl的父类即BaseDaoImpl<User>
		ParameterizedType superclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		// 获得父类上声明的泛型数组<User>,User是domain中的类
		Type[] actualTypeArguments = superclass.getActualTypeArguments();
		entityClass = (Class<T>) actualTypeArguments[0];
	}

	@Override
	public void save(T entity) {
		this.getHibernateTemplate().save(entity);
	}

	@Override
	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);

	}

	@Override
	public void update(T entity) {
		this.getHibernateTemplate().update(entity);

	}

	@Override
	public T findById(Serializable id) {
		return this.getHibernateTemplate().get(entityClass, id);
	}

	@Override
	public List<T> findAll() {
		String hql = "FROM " + entityClass.getSimpleName();
		return (List<T>) this.getHibernateTemplate().find(hql);
	}

	/**
	 * 更新数据,用query内封装的update方法
	 */
	public void executeUpadte(String queryName, Object... objects) {
		Session session = this.getSessionFactory().getCurrentSession();
		Query query = session.getNamedQuery(queryName);
		int i = 0;
		// 为query循环赋值
		for (Object object : objects) {
			query.setParameter(i++, object);
		}
		query.executeUpdate();// 更新数据
	}

	/**
	 * 分页查询方法
	 */
	public void pageQuery(PageBean pageBean) {

		int currentPage = pageBean.getCurrentPage();
		int pageSize = pageBean.getPageSize();
		DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();
		// 查询total
		detachedCriteria.setProjection(Projections.rowCount());// 设定查询方式为count(*)
		List<Long> countList = (List<Long>) this.getHibernateTemplate().findByCriteria(detachedCriteria);// 查询
		Long count = countList.get(0);// 获取查询结果
		pageBean.setTotal(count.intValue());// 赋值

		// 查询List rows要展示的内容
		detachedCriteria.setProjection(null);// 设定查询方式为*
		// 指定hibernate封装对象的方式
		detachedCriteria.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
		int firstResult = (currentPage - 1) * pageSize;// 初始位置
		int maxResults = pageSize;// 展示条目
		List rows = this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
		pageBean.setRows(rows);
	}

	@Override
	public void saveOrUpdate(T entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);
	}

	/**
	 * 根据条件查询
	 */
	public List<T> findByCriteria(DetachedCriteria detachedCriteria) {
		return (List<T>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
	}

}
