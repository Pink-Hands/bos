package pers.test.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pers.test.bos.dao.IRegionDao;
import pers.test.bos.domain.BcRegion;
import pers.test.bos.service.IRegionService;
import pers.test.bos.utils.PageBean;

@Service
@Transactional
public class RegionServiceImpl implements IRegionService {

	@Autowired
	private IRegionDao regionDao;

	/**
	 * 批量保存区域数据
	 */
	public void saveBatch(List<BcRegion> regionList) {
		for (BcRegion region : regionList) {
			regionDao.saveOrUpdate(region);
		}
	}

	/**
	 * 区域分页查询
	 */
	public void pageQuery(PageBean pageBean) {
		regionDao.pageQuery(pageBean);
	}

	/**
	 * 查询所有区域
	 */
	public List<BcRegion> findAll() {
		return regionDao.findAll();
	}

	/**
	 * 根据页面输入进行模糊查询
	 */
	public List<BcRegion> findListByQ(String q) {
		
		return regionDao.findListByQ(q);
	}
}
