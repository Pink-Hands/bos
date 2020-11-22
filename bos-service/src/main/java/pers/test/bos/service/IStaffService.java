package pers.test.bos.service;

import java.util.List;

import pers.test.bos.domain.BcStaff;
import pers.test.bos.utils.PageBean;

public interface IStaffService {

	public void save(BcStaff model);

	public void pageQuery(PageBean pageBean);
	
	public void deleteBatch(String ids);

	public void restoreBatch(String ids);

	public BcStaff findById(String id);

	public void update(BcStaff staff);

	public List<BcStaff> findListNotDelete();
	
	
}
