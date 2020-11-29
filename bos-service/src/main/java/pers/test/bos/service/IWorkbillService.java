package pers.test.bos.service;

import pers.test.bos.utils.PageBean;

public interface IWorkbillService {

	public void pageQuery(PageBean pageBean);

	public void attachbill(String ids);

	public void deleteBatch(String ids);

}
