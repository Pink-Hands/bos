package pers.test.bos.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pers.test.bos.dao.IFunctionDao;
import pers.test.bos.domain.AuthFunction;
import pers.test.bos.domain.TUser;
import pers.test.bos.service.IFunctionService;
import pers.test.bos.utils.BOSUtils;
import pers.test.bos.utils.PageBean;

@Service
@Transactional
public class FunctionServiceImpl implements IFunctionService {

	@Autowired
	private IFunctionDao dao;

	public List<AuthFunction> findAll() {

		return dao.findAll();
	}

	public void save(AuthFunction model) {

		AuthFunction parentFunction = model.getParentFunction();
		if (parentFunction != null && parentFunction.getId().equals("")) {
			model.setParentFunction(null);
		}
		dao.save(model);
	}

	public void pageQuery(PageBean pageBean) {
		dao.pageQuery(pageBean);
	}

	public List<AuthFunction> findMenu() {
		List<AuthFunction> list = null;
		TUser user = BOSUtils.getLoginUser();
		if (user.getId().equals("1")) {
			// 如果是超级管理员
			list = dao.findAllMenu();
		} else {
			// 如果是其他用户
			list = dao.findMenuByUserId(user.getId());
		}
		return list;
	}
	public List<AuthFunction> findMenusystem() {
		List<AuthFunction> list = null;
		TUser user = BOSUtils.getLoginUser();
		if (user.getId().equals("1")) {
			// 如果是超级管理员
			list = dao.findAllMenusystem();
		} else {
			// 如果是其他用户
			list = dao.findMenuByUserIdsystem(user.getId());
		}
		return list;
	}

	/**
	 * 批量删除权限
	 */
	public void deleteBatch(String ids) {
		if (StringUtils.isNotBlank(ids)) {
			String[] functionIds = ids.split(",");// 分割回数组
			for (String id : functionIds) {
				AuthFunction function = new AuthFunction(id);
				dao.delete(function);
			}
		}
	}

	public void update(AuthFunction function) {
		//String sql = "update auth_function set name = ? , code = ? , description = ? , page = ? , generatemenu = ? , zindx = ? , pid = ? where id = ?";
		//dao.executeUpadte(sql, model.getName(),model.getCode(),model.getDescription(),model.getPageaction(),model.getGeneratemenu(),model.getZindex(),model.getpId(),model.getId());
	
		dao.edit(function);
		
	}

	
}
