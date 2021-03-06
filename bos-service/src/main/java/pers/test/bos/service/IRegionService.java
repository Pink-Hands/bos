package pers.test.bos.service;

import java.util.List;

import pers.test.bos.domain.BcRegion;
import pers.test.bos.utils.PageBean;

public interface IRegionService {

	public void saveBatch(List<BcRegion> regionList);

	public void pageQuery(PageBean pageBean);

	public List<BcRegion> findAll();

	public List<BcRegion> findListByQ(String q);

	public void save(BcRegion region);

	public void deleteBatch(String ids);

	public void edit(BcRegion region);

	public BcRegion findByShortcode(String shortcode);

}
