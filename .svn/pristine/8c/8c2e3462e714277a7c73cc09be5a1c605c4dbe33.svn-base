package pers.test.bos.service;

import java.util.List;

import pers.test.bos.domain.TUser;
import pers.test.bos.utils.PageBean;

public interface IUserService {

	TUser login(TUser model);

	public void editPassword(String id, String password);

	public void save(TUser user, String[] roleIds);

	public void pageQuery(PageBean pageBean);

	public void deleteBatch(String ids);

	public TUser findById(String userId);

	public void update(TUser user, String[] roleIds, String birthdayString);

	public List<TUser> findAll();

}
