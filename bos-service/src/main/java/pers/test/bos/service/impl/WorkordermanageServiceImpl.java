package pers.test.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pers.test.bos.dao.IWorkordermanagerDao;
import pers.test.bos.domain.QpWorkordermanage;
import pers.test.bos.service.IWorkordermanageService;

@Service
@Transactional
public class WorkordermanageServiceImpl implements IWorkordermanageService {

	@Autowired
	private IWorkordermanagerDao workordermanageDao;
	
	public void save(QpWorkordermanage model) {
		workordermanageDao.save(model);
	}

}
