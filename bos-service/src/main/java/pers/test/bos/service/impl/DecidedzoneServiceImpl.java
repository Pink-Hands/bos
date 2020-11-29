package pers.test.bos.service.impl;

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pers.test.bos.dao.IDecidedzoneDao;
import pers.test.bos.dao.ISubareaDao;
import pers.test.bos.domain.BcDecidedzone;
import pers.test.bos.domain.BcSubarea;
import pers.test.bos.service.IDecidedzoneService;
import pers.test.bos.utils.PageBean;
import pers.test.crm.ICustomerService;

@Service
@Transactional
public class DecidedzoneServiceImpl implements IDecidedzoneService {

	@Autowired
	private IDecidedzoneDao decidedzoneDao;
	@Autowired
	private ISubareaDao subareaDao;
	@Autowired
	private ICustomerService customerService;

	/**
	 * 保存定区,并在分区绑定定区
	 */
	public void save(BcDecidedzone model, String[] subareaid) {
		decidedzoneDao.save(model);// 保存定区
		for (String id : subareaid) {
			BcSubarea subarea = subareaDao.findById(id);// 根据id查找分区
			// 分区 对 定区 为 多对一,由多的一方进行绑定
			subarea.setBcDecidedzone(model);// 让分区绑定定区
		}
	}

	public void pageQuery(PageBean pageBean) {
		decidedzoneDao.pageQuery(pageBean);
	}

	public void deleteBatch(String ids) {
		if (StringUtils.isNotBlank(ids)) {
			String[] decidedzoneIds = ids.split(",");// 分割回数组
			for (String id : decidedzoneIds) {
				subareaDao.setByDecidedzoneid(id);
				customerService.assigncustomersuntodecidedzone(id);
				BcDecidedzone decidedzone = new BcDecidedzone(id);
				decidedzoneDao.delete(decidedzone);
			}
		}
	}

	/**
	 * 修改定区,关联取派员 分区 客户
	 */
	public void edit(BcDecidedzone decidedzone, String[] subareaid, String oldId) {
		if (decidedzone.getId().equals(oldId)) {
			// 如果id没有改变
			BcDecidedzone findById = decidedzoneDao.findById(oldId);
			findById.setBcStaff(decidedzone.getBcStaff());
			findById.setName(decidedzone.getName());
			Set<BcSubarea> bcSubareas = findById.getBcSubareas();
			for (BcSubarea subarea : bcSubareas) {
				subarea.setBcDecidedzone(null);//将原有的置null
			}
			if(subareaid!=null) {
				for (String id : subareaid) {
					BcSubarea subarea = subareaDao.findById(id);// 根据id查找分区
					subarea.setBcDecidedzone(decidedzone);// 让分区绑定定区
				}
			}
			decidedzoneDao.save(findById);
		} else {
			//如果id改变了
			//删除旧数据
			subareaDao.setByDecidedzoneid(oldId);
			customerService.updatedecidedzone(oldId,decidedzone.getId());
			BcDecidedzone oldDecidedzone = new BcDecidedzone(oldId);
			decidedzoneDao.delete(oldDecidedzone);
			//保存新数据
			decidedzoneDao.save(decidedzone);
			for (String id : subareaid) {
				BcSubarea subarea = subareaDao.findById(id);// 根据id查找分区
				subarea.setBcDecidedzone(decidedzone);// 让分区绑定定区
			}
		}

	}

}
