package pers.test.bos.utils;

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;

/**
 * 封装分页属性
 */

public class PageBean {

	private int currentPage;//当前页码
	private int pageSize;//每页显示条目数
	private DetachedCriteria detachedCriteria;//查询条件,封装的查询类
	private int total;//总条目
	private List rows;//当前页展示的内容
	
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public DetachedCriteria getDetachedCriteria() {
		return detachedCriteria;
	}
	public void setDetachedCriteria(DetachedCriteria detachedCriteria) {
		this.detachedCriteria = detachedCriteria;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	
}
