package pers.test.bos.service;

import pers.test.bos.domain.BcDecidedzone;
import pers.test.bos.utils.PageBean;

public interface IDecidedzoneService {

	public void save(BcDecidedzone model, String[] subareaid);

	public void pageQuery(PageBean pageBean);

	public void deleteBatch(String ids);

	public void edit(BcDecidedzone decidedzone, String[] subareaid, String oldId);

}
