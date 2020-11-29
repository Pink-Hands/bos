package pers.test.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pers.test.bos.dao.IRegionDao;
import pers.test.bos.dao.ISubareaDao;
import pers.test.bos.domain.BcRegion;
import pers.test.bos.domain.BcSubarea;
import pers.test.bos.service.IRegionService;
import pers.test.bos.utils.PageBean;

@Service
@Transactional
public class RegionServiceImpl implements IRegionService {

	@Autowired
	private IRegionDao regionDao;
	@Autowired
	private ISubareaDao subareaDao;

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

	/**
	 * 增加单条数据
	 */
	public void save(BcRegion region) {
		regionDao.save(region);
	}

	/**
	 * 批量删除区域数据
	 */
	public void deleteBatch(String ids) {
		String[] regionIds = ids.split(",");// 分割回数组
		for (String id : regionIds) {
			BcRegion region = new BcRegion(id);
			subareaDao.setByRegionid(id);
			regionDao.delete(region);
		}
	}

	/**
	 * 修改区域
	 */
	public void edit(BcRegion region) {
		regionDao.saveOrUpdate(region);
	}

	/**
	 * 根据简码找区域
	 */
	public BcRegion findByShortcode(String shortcode) {
		return regionDao.findByShortcode(shortcode);
	}
}
