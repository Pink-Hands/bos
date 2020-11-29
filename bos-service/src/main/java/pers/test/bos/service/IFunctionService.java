package pers.test.bos.service;

import java.util.List;

import pers.test.bos.domain.AuthFunction;
import pers.test.bos.utils.PageBean;

public interface IFunctionService {

	public List<AuthFunction> findAll();

	public void save(AuthFunction model);

	public void pageQuery(PageBean pageBean);

	public List<AuthFunction> findMenu();

	public void deleteBatch(String ids);

	public void update(AuthFunction function);

	public List<AuthFunction> findMenusystem();

}
