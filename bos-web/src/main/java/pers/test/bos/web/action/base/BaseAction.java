package pers.test.bos.web.action.base;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import pers.test.bos.domain.BcRegion;
import pers.test.bos.utils.PageBean;

/**
 * 表现层通用实现 获取数据存进model
 */
public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

	public static final String HOME = "home";
	public static final String LIST = "list";

	// 模型对象
	protected T model;

	public T getModel() {
		return model;
	}

	// 构造方法动态获取 实体类型 ,通过反射建立model
	public BaseAction() {
		// this为触发Action的类,已经定义好了泛型,其父类为baseAction,也拥有了相应的泛型
		ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();

		// 获得BaseAction上声明的泛型数组
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
		Class<T> entityClass = (Class<T>) actualTypeArguments[0];// 获取泛型
		detachedCriteria = DetachedCriteria.forClass(entityClass);// 根据类创建分页查询对象
		pageBean.setDetachedCriteria(detachedCriteria);
		// 通过反射创建对象
		try {
			model = entityClass.newInstance();// 根据泛型创建一个与之对应的实例,存放Action带来的数据
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 分页查询
	 */
	protected int page;// 存放传递来的page和rows
	protected int rows;
	protected PageBean pageBean = new PageBean();
	DetachedCriteria detachedCriteria = null;

	// 属性驱动
	public void setPage(int page) {
		pageBean.setCurrentPage(page);
	}

	public void setRows(int rows) {
		pageBean.setPageSize(rows);
	}

	/**
	 * 将Object类转化为json,并在Response中输出数据
	 * 
	 * @param o
	 *            Object类
	 * @param excludes
	 *            需要排除的json元素
	 */
	/*
	 * 使用json-lib将pageBean输出为json JSONObject转化单一对象,即使pageBean内很多对象,但pageBean不是数组
	 * JSONArray转化数组
	 */
	public void java2Json(Object o, String[] excludes) {
		JsonConfig jsonConfig = new JsonConfig();
		// 排除不需要转化的json
		jsonConfig.setExcludes(excludes);
		String json = JSONObject.fromObject(o, jsonConfig).toString();// 转化为json字符串
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");// 设置输出为json
		try {
			ServletActionContext.getResponse().getWriter().print(json);// 将从数据库查询到的数据输回
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将List转化为json,并在Response中输出数据
	 * 
	 * @param o
	 *            数组List
	 * @param excludes
	 *            需要排除的json元素
	 */
	public void java2Json(List o, String[] excludes) {
		JsonConfig jsonConfig = new JsonConfig();
		// 排除不需要转化的json
		jsonConfig.setExcludes(excludes);
		String json = JSONArray.fromObject(o, jsonConfig).toString();// 转化为json字符串
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");// 设置输出为json
		try {
			ServletActionContext.getResponse().getWriter().print(json);// 将从数据库查询到的数据输回
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
