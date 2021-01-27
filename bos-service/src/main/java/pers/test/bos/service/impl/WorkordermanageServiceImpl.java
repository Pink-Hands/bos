package pers.test.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pers.test.bos.dao.IWorkordermanagerDao;
import pers.test.bos.domain.BcSubarea;
import pers.test.bos.domain.QpWorkordermanage;
import pers.test.bos.service.IWorkordermanageService;
import pers.test.bos.utils.PageBean;

@Service
@Transactional
public class WorkordermanageServiceImpl implements IWorkordermanageService {

	@Autowired
	private IWorkordermanagerDao workordermanageDao;
	
	public void save(QpWorkordermanage model) {
		workordermanageDao.save(model);
	}

	public void pageQuery(PageBean pageBean) {
		workordermanageDao.pageQuery(pageBean);
	}

	public void saveBatch(List<QpWorkordermanage> workordermanagerList) {
		for (QpWorkordermanage workordermanage : workordermanagerList) {
			workordermanageDao.saveOrUpdate(workordermanage);
		}
	}

	public void saveorupdate(QpWorkordermanage model) {
		QpWorkordermanage workordermanage = workordermanageDao.findById(model.getId());
		workordermanage.setArrivecity(model.getArrivecity());
		workordermanage.setProduct(model.getProduct());
		workordermanage.setProdtimelimit(model.getProdtimelimit());
		workordermanage.setSenderphone(model.getSenderphone());
		workordermanage.setSenderaddr(model.getSenderaddr());
		workordermanage.setReceiverphone(model.getReceiverphone());
		workordermanage.setReceiveraddr(model.getReceiveraddr());
		workordermanageDao.saveOrUpdate(workordermanage);
	}

	
	public void editIdChange(QpWorkordermanage model, String oldId) {
		QpWorkordermanage workordermanage = workordermanageDao.findById(oldId);
		model.setProdtype(workordermanage.getProdtype());
		model.setSendername(workordermanage.getSendername());
		model.setReceivername(workordermanage.getReceivername());
		model.setActlweit(workordermanage.getActlweit());
		workordermanageDao.save(model);
		workordermanageDao.delete(workordermanage);
	}

	@Override
	public List<QpWorkordermanage> findAll() {
		return workordermanageDao.findAll();
	}

}
