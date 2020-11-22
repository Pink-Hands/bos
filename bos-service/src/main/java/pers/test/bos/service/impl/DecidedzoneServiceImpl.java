package pers.test.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pers.test.bos.dao.IDecidedzoneDao;
import pers.test.bos.dao.ISubareaDao;
import pers.test.bos.domain.BcDecidedzone;
import pers.test.bos.domain.BcSubarea;
import pers.test.bos.service.IDecidedzoneService;
import pers.test.bos.utils.PageBean;

@Service
@Transactional
public class DecidedzoneServiceImpl implements IDecidedzoneService {

	@Autowired
	private IDecidedzoneDao decidedzoneDao;
	@Autowired
	private ISubareaDao subareaDao;
	
	/**
	 * 保存定区,并在分区绑定定区
	 */
	public void save(BcDecidedzone model, String[] subareaid) {
		decidedzoneDao.save(model);//保存定区
		for (String id : subareaid) {
			BcSubarea subarea = subareaDao.findById(id);//根据id查找分区
			//分区 对 定区 为 多对一,由多的一方进行绑定
			subarea.setBcDecidedzone(model);//让分区绑定定区
		}
	}

	public void pageQuery(PageBean pageBean) {
		decidedzoneDao.pageQuery(pageBean);
	}

}
