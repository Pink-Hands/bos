package pers.test.bos.dao;

import java.util.List;

import pers.test.bos.dao.base.IBaseDao;
import pers.test.bos.domain.AuthFunction;

public interface IFunctionDao extends IBaseDao<AuthFunction> {

	public List<AuthFunction> findFunctionListByUserId(String id);

	public List<AuthFunction> findAllMenu();

	public List<AuthFunction> findMenuByUserId(String userId);

	public void edit(AuthFunction function);

	public List<AuthFunction> findAllMenusystem();

	public List<AuthFunction> findMenuByUserIdsystem(String id);

}
