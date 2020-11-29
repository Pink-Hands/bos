package pers.test.bos.service;

import pers.test.bos.domain.QpNoticebill;
import pers.test.bos.utils.PageBean;

public interface INoticebillService {

	public void save(QpNoticebill model);

	public void pageQuery(PageBean pageBean);

	public void mansave(QpNoticebill model);

}
