package pers.test.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import pers.test.bos.domain.BcRegion;
import pers.test.bos.service.IRegionService;
import pers.test.bos.utils.PinYin4jUtils;
import pers.test.bos.web.action.base.BaseAction;

/**
 * 区域管理
 */
@Controller
@Scope("prototype")
public class RegionAction extends BaseAction<BcRegion> {

	@Autowired
	private IRegionService regionService;

	private File regionFile;

	// 属性驱动,接收上传的文件
	public void setRegionFile(File regionFile) {
		this.regionFile = regionFile;
	}

	/**
	 * 区域文件导入
	 */
	public String importXls() throws FileNotFoundException, IOException {

		List<BcRegion> regionList = new ArrayList<BcRegion>();
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(regionFile));
		HSSFSheet hssfSheet = workbook.getSheetAt(0);// 获取第一个sheet
		for (Row row : hssfSheet) {
			int rowNum = row.getRowNum();// 获得行号
			if (rowNum == 0) {
				continue;// 不读取第一行数据
			}
			String id = row.getCell(0).getStringCellValue();// 编号
			String province = row.getCell(1).getStringCellValue();// 省
			String city = row.getCell(2).getStringCellValue();// 市
			String district = row.getCell(3).getStringCellValue();// 区
			String postcode = row.getCell(4).getStringCellValue();// 邮编
			// shortcode
			String province2 = province.substring(0, province.length() - 1);
			String city2 = city.substring(0, city.length() - 1);
			String district2 = district.substring(0, district.length() - 1);
			String info = province2 + city2 + district2;
			String[] headByString = PinYin4jUtils.getHeadByString(info);
			String shortcode = StringUtils.join(headByString);
			// citycode
			String citycode = PinYin4jUtils.hanziToPinyin(city2, "");

			BcRegion region = new BcRegion(id, province, city, district, postcode, shortcode, citycode, null);
			regionList.add(region);
		}
		regionService.saveBatch(regionList);
		return NONE;
	}

	/**
	 * 区域分页查询
	 */
	public String pageQuery() throws IOException {
		
		regionService.pageQuery(pageBean);
		String[] excludes = new String[]{"currentPage","detachedCriteria","pageSize","bcSubareas"};
		this.java2Json(pageBean, excludes);
		return NONE;
	}
	
	private String q;
	public void setQ(String q) {
		this.q = q;
	}

	/**
	 * 根据条件查询区域,返回json数据
	 */
	public String listajax() {
		List<BcRegion> list = null;
		if(StringUtils.isNotBlank(q)) {
			list = regionService.findListByQ(q);
		}else {
			list = regionService.findAll();
		}
		this.java2Json(list, new String[] {"bcSubareas"});
		return NONE;
	}
}
