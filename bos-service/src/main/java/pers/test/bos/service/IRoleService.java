package pers.test.bos.service;

import java.util.List;

import pers.test.bos.domain.AuthRole;
import pers.test.bos.utils.PageBean;

public interface IRoleService {

	public void save(AuthRole role, String functionIds);

	public void pageQuery(PageBean pageBean);

	public List<AuthRole> findAll();

	public void deleteBatch(String ids);

	public AuthRole findById(String roleId);

	public void update(AuthRole role, String functionIds);

}
