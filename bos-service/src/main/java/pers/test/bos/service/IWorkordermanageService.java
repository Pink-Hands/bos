package pers.test.bos.service;

import java.util.List;

import pers.test.bos.domain.QpWorkordermanage;
import pers.test.bos.utils.PageBean;

public interface IWorkordermanageService {

	public void save(QpWorkordermanage model);

	public void pageQuery(PageBean pageBean);

	public void saveBatch(List<QpWorkordermanage> workordermanagerList);

	public void saveorupdate(QpWorkordermanage model);

	public void editIdChange(QpWorkordermanage model, String oldId);

	public List<QpWorkordermanage> findAll();

}
