package pers.test.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import pers.test.bos.domain.BcRegion;
import pers.test.bos.domain.BcSubarea;
import pers.test.bos.service.IRegionService;
import pers.test.bos.service.ISubareaService;
import pers.test.bos.utils.FileUtils;
import pers.test.bos.utils.PinYin4jUtils;
import pers.test.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class SubareaAction extends BaseAction<BcSubarea> {

	@Autowired
	private ISubareaService subareaService;
	@Autowired
	private IRegionService regionService;
	/**
	 * 添加分区
	 */
	@RequiresPermissions("subarea-add")
	public String add() {
		subareaService.save(model);
		return LIST;
	}

	/**
	 * 分区分页查询
	 */
	@RequiresPermissions("subarea")
	public String pageQuery() {
		DetachedCriteria dc = pageBean.getDetachedCriteria();
		String addresskey = model.getAddresskey();
		if (StringUtils.isNotBlank(addresskey)) {
			dc.add(Restrictions.like("addresskey", "%" + addresskey + "%"));// 模糊查询,前者数据库字段,后者表示 前后可有别值
		}
		BcRegion region = model.getBcRegion();
		if (region != null) {
			String province = region.getProvince();
			String city = region.getCity();
			String district = region.getDistrict();

			dc.createAlias("bcRegion", "r");// 前者关联BcSuarea中的属性bcRegion,后者给表bc_region起别名

			if (StringUtils.isNotBlank(province)) {
				dc.add(Restrictions.like("r.province", "%" + province + "%"));// r.province即bcRegion.province
			}
			if (StringUtils.isNotBlank(city)) {
				dc.add(Restrictions.like("r.city", "%" + city + "%"));
			}
			if (StringUtils.isNotBlank(district)) {
				dc.add(Restrictions.like("r.district", "%" + district + "%"));
			}
		}
		subareaService.pageQuery(pageBean);
		this.java2Json(pageBean,
				new String[] { "currentPage", "detachedCriteria", "pageSize", "bcDecidedzone", "bcSubareas" });
		return NONE;
	}

	/**
	 * 分区数据导出功能
	 */
	@RequiresPermissions("subarea-export")
	public String exportXls() throws IOException {
		/* 查询所有数据 */
		List<BcSubarea> list = subareaService.findAll();
		/* 输出到excel */
		HSSFWorkbook workbook = new HSSFWorkbook();// excel
		HSSFSheet sheet = workbook.createSheet("分区数据");// sheet
		HSSFRow headRow = sheet.createRow(0);// 标题行
		headRow.createCell(0).setCellValue("分拣编号");
		headRow.createCell(1).setCellValue("起始号");
		headRow.createCell(2).setCellValue("终止号");
		headRow.createCell(3).setCellValue("单双号");
		headRow.createCell(4).setCellValue("省市区");
		headRow.createCell(5).setCellValue("位置");
		// 遍历写入数据
		for (BcSubarea subarea : list) {
			HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);// 创建新的行
			dataRow.createCell(0).setCellValue(subarea.getId());
			dataRow.createCell(1).setCellValue(subarea.getStartnum());
			dataRow.createCell(2).setCellValue(subarea.getEndnum());
			if (subarea.getSingle() == '1') {
				dataRow.createCell(3).setCellValue("1");
			} else if (subarea.getSingle() == '2') {
				dataRow.createCell(3).setCellValue("2");
			} else {
				dataRow.createCell(3).setCellValue("0");
			}
			dataRow.createCell(4).setCellValue(subarea.getBcRegion().getName());
			dataRow.createCell(5).setCellValue(subarea.getPosition());
		}
		/* 输出文件 */
		String filename = "分区数据.xls";// 设置文件名
		String contentType = ServletActionContext.getServletContext().getMimeType(filename);// 根据文件名动态获取输出格式
		ServletOutputStream out = ServletActionContext.getResponse().getOutputStream();// 设置输出流
		ServletActionContext.getResponse().setContentType(contentType);
		String agent = ServletActionContext.getRequest().getHeader("User-Agent");// 获取浏览器类型
		filename = FileUtils.encodeDownloadFilename(filename, agent);// 根据浏览器对文件名进行编码
		ServletActionContext.getResponse().setHeader("content-disposition", "attachment;filename=" + filename);
		workbook.write(out);
		return NONE;
	}

	/**
	 * 查询未关联定区的 分区数据
	 */
	public String listajax() {
		List<BcSubarea> list = subareaService.findListNotAssociation();
		this.java2Json(list, new String[] { "bcDecidedzone", "bcRegion" });
		return NONE;
	}

	// 属性驱动获取定区id
	private String decidedzoneId;

	public void setDecidedzoneId(String decidedzoneId) {
		this.decidedzoneId = decidedzoneId;
	}

	/**
	 * 根据定区id查询所关联的分区
	 */
	public String findListByDecidedzoneId() {
		List<BcSubarea> list = subareaService.findListByDecidedzoneId(decidedzoneId);
		this.java2Json(list, new String[] { "bcDecidedzone", "bcSubareas" });
		return NONE;
	}

	/**
	 * 查询区域分区分布图数据
	 */
	public String findSubareaGroupByProvince() {
		List<Object> list = subareaService.findSubareaGroupByProvince();
		this.java2Json(list, new String[] {});
		return NONE;
	}

	private String ids;

	public void setIds(String ids) {
		this.ids = ids;
	}

	/**
	 * 批量删除分区
	 */
	@RequiresPermissions("subarea-delete")
	public String deleteBatch() {
		subareaService.deleteBatch(ids);
		return LIST;
	}

	private String oldId;

	public void setOldId(String oldId) {
		this.oldId = oldId;
	}

	/**
	 * 修改分区
	 */
	@RequiresPermissions("subarea-edit")
	public String edit() {
		subareaService.edit(model, oldId);
		return LIST;
	}

	/**
	 * 查找所有分区id
	 */
	public String findAllId() {
		List<BcSubarea> list = subareaService.findAll();
		String allIds = "";
		for (BcSubarea subarea : list) {
			String id = subarea.getId();
			allIds += id + ",";
		}
		int length = allIds.length();
		if (length > 0) {
			allIds = allIds.substring(0, length - 1);// 去除最后的,
		}
		ServletActionContext.getResponse().setContentType("text/plain;charset=utf-8");// 设置输出为文本
		try {
			ServletActionContext.getResponse().getWriter().print(allIds);// 将从数据库查询到的数据输回
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;
	}

	private File subareaFile;

	public void setSubareaFile(File subareaFile) {
		this.subareaFile = subareaFile;
	}

	/**
	 * 导入分区数据
	 */
	@RequiresPermissions("subarea-import")
	public String importXls() throws FileNotFoundException, IOException {
		List<BcSubarea> subareaList = new ArrayList<BcSubarea>();
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(subareaFile));
		HSSFSheet hssfSheet = workbook.getSheetAt(0);// 获取第一个sheet
		for (Row row : hssfSheet) {
			int rowNum = row.getRowNum();// 获得行号
			if (rowNum == 0) {
				continue;// 不读取第一行数据
			}
			row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
			String id = row.getCell(0).getStringCellValue();// 分拣编号
			String startnum = row.getCell(1).getStringCellValue();// 起始号
			String endnum = row.getCell(2).getStringCellValue();// 终止号
			String stringSingle = row.getCell(3).getStringCellValue();// 单双号
			Character single = '0';
			if (stringSingle.equals("1")) {
				single = '1';
			} else if (stringSingle.equals("2")) {
				single = '2';
			}
			String regionmessage = row.getCell(4).getStringCellValue();// 省市区
			String position = row.getCell(5).getStringCellValue();// 位置
			String[] list = regionmessage.split(" ");
			String province = list[0];
			String city = list[1];
			String district = list[2];
			String code = province.substring(0, province.length() - 1) + city.substring(0, city.length() - 1)
					+ district.substring(0, district.length() - 1);
			String[] headByString = PinYin4jUtils.getHeadByString(code);
			String shortcode = StringUtils.join(headByString);//简码
			BcRegion region = regionService.findByShortcode(shortcode);
			BcSubarea subarea = new BcSubarea(id, null, region, null, startnum, endnum, single, position);
			subareaList.add(subarea);
		}
		subareaService.saveBatch(subareaList);
		return LIST;
	}

}
