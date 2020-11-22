package pers.test.bos.service.impl;

import java.util.List;

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
		if(parentFunction != null && parentFunction.getId().equals("")) {
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
		if(user.getId().equals("1")) {
			//如果是超级管理员
			list = dao.findAllMenu();
		}else {
			//如果是其他用户
			list = dao.findMenuByUserId(user.getId());
		}
		return list;
	}

}
