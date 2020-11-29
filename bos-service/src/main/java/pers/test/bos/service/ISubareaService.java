package pers.test.bos.service;

import java.util.List;

import pers.test.bos.domain.BcSubarea;
import pers.test.bos.utils.PageBean;

public interface ISubareaService {

	void save(BcSubarea model);

	void pageQuery(PageBean pageBean);

	public List<BcSubarea> findAll();

	public List<BcSubarea> findListNotAssociation();

	public List<BcSubarea> findListByDecidedzoneId(String decidedzoneId);

	public List<Object> findSubareaGroupByProvince();

	public void deleteBatch(String ids);

	public void edit(BcSubarea subarea, String oldId);

	public void saveBatch(List<BcSubarea> subareaList);

}
