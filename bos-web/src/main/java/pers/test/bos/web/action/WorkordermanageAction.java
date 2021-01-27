package pers.test.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import pers.test.bos.domain.QpWorkordermanage;
import pers.test.bos.domain.TUser;
import pers.test.bos.service.IWorkordermanageService;
import pers.test.bos.utils.FileUtils;
import pers.test.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class WorkordermanageAction extends BaseAction<QpWorkordermanage> {

	@Autowired
	private IWorkordermanageService workordermanageService;

	/**
	 * 快速添加工作单
	 */
	@RequiresPermissions("quickworkordermanage")
	public String add() throws IOException {
		String f = "1";
		try {
			workordermanageService.save(model);
		} catch (Exception e) {
			e.printStackTrace();
			f = "0";
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().printf(f);
		return NONE;
	}
	
	@RequiresPermissions("workordermanageimport")
	public String pageQuery() {
		DetachedCriteria dc = pageBean.getDetachedCriteria();
		String id = model.getId();
		String arrivecity = model.getArrivecity();
		String product = model.getProduct();
		String prodtimelimit = model.getProdtimelimit();// 产品时限
		String senderphone = model.getSenderphone();
		String senderaddr = model.getSenderaddr();
		String receiverphone = model.getReceiverphone();
		String receiveraddr = model.getReceiveraddr();
		if (StringUtils.isNotBlank(id)) {
			dc.add(Restrictions.like("id", "%" + id + "%"));// 模糊查询,前者数据库字段,后者表示 前后可有别值
		}
		if (StringUtils.isNotBlank(arrivecity)) {
			dc.add(Restrictions.like("arrivecity", "%" + arrivecity + "%"));// 模糊查询,前者数据库字段,后者表示 前后可有别值
		}
		if (StringUtils.isNotBlank(product)) {
			dc.add(Restrictions.like("product", "%" + product + "%"));// 模糊查询,前者数据库字段,后者表示 前后可有别值
		}
		if (StringUtils.isNotBlank(prodtimelimit)) {
			dc.add(Restrictions.like("prodtimelimit", "%" + prodtimelimit + "%"));// 模糊查询,前者数据库字段,后者表示 前后可有别值
		}
		if (StringUtils.isNotBlank(senderphone)) {
			dc.add(Restrictions.like("senderphone", "%" + senderphone + "%"));// 模糊查询,前者数据库字段,后者表示 前后可有别值
		}
		if (StringUtils.isNotBlank(senderaddr)) {
			dc.add(Restrictions.like("senderaddr", "%" + senderaddr + "%"));// 模糊查询,前者数据库字段,后者表示 前后可有别值
		}
		if (StringUtils.isNotBlank(receiverphone)) {
			dc.add(Restrictions.like("receiverphone", "%" + receiverphone + "%"));// 模糊查询,前者数据库字段,后者表示 前后可有别值
		}
		if (StringUtils.isNotBlank(receiveraddr)) {
			dc.add(Restrictions.like("receiveraddr", "%" + receiveraddr + "%"));// 模糊查询,前者数据库字段,后者表示 前后可有别值
		}
		workordermanageService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[] { "updatetime" });
		return NONE;
	}

	private File workordermanagerFile;

	public void setWorkordermanagerFile(File workordermanagerFile) {
		this.workordermanagerFile = workordermanagerFile;
	}

	/**
	 * 导入数据
	 */
	@RequiresPermissions("workordermanage-import")
	public String importXls() throws FileNotFoundException, IOException {
		List<QpWorkordermanage> workordermanagerList = new ArrayList<QpWorkordermanage>();
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(workordermanagerFile));
		HSSFSheet hssfSheet = workbook.getSheetAt(0);// 获取第一个sheet
		for (Row row : hssfSheet) {
			int rowNum = row.getRowNum();// 获得行号
			if (rowNum == 0) {
				continue;// 不读取第一行数据
			}
			for (int i = 0; i <= 11; i++) {
				row.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
			}
			String id = row.getCell(0).getStringCellValue();// 编号
			String arrivecity = row.getCell(1).getStringCellValue();// 起始号
			String product = row.getCell(2).getStringCellValue();// 终止号
			String prodtimelimit = row.getCell(3).getStringCellValue();// 单双号
			String prodtype = row.getCell(4).getStringCellValue();
			String sendername = row.getCell(5).getStringCellValue();
			String senderphone = row.getCell(6).getStringCellValue();
			String senderaddr = row.getCell(7).getStringCellValue();
			String receivername = row.getCell(8).getStringCellValue();
			String receiverphone = row.getCell(9).getStringCellValue();
			String receiveraddr = row.getCell(10).getStringCellValue();
			String actlweitString = row.getCell(11).getStringCellValue();
			Double actlweit = Double.parseDouble(actlweitString);
			Date updatetime = new Date();
			QpWorkordermanage workordermanage = new QpWorkordermanage(id, arrivecity, product, null, null, null,
					prodtimelimit, prodtype, sendername, senderphone, senderaddr, receivername, receiverphone,
					receiveraddr, null, actlweit, null, null, updatetime);
			workordermanagerList.add(workordermanage);
		}
		workordermanageService.saveBatch(workordermanagerList);
		return LIST;
	}

	/**
	 * 导出模板
	 */
	public String exportXls() throws IOException {
		/* 输出到excel */
		HSSFWorkbook workbook = new HSSFWorkbook();// excel
		HSSFSheet sheet = workbook.createSheet("分区数据");// sheet
		HSSFRow headRow = sheet.createRow(0);// 标题行
		headRow.createCell(0).setCellValue("编号");
		headRow.createCell(1).setCellValue("到达地");
		headRow.createCell(2).setCellValue("产品");
		headRow.createCell(3).setCellValue("产品时限");
		headRow.createCell(4).setCellValue("产品类型");
		headRow.createCell(5).setCellValue("发件人姓名");
		headRow.createCell(6).setCellValue("发件人电话");
		headRow.createCell(7).setCellValue("发件人地址");
		headRow.createCell(8).setCellValue("收件人姓名");
		headRow.createCell(9).setCellValue("收件人电话");
		headRow.createCell(10).setCellValue("收件人地址");
		headRow.createCell(11).setCellValue("实际重量(kg)");
		/* 遍历写入数据 */
		HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);// 创建新的行
		dataRow.createCell(0).setCellValue("201129001");
		dataRow.createCell(1).setCellValue("内蒙古呼和浩特市");
		dataRow.createCell(2).setCellValue("水果");
		dataRow.createCell(3).setCellValue("5日内送达");
		dataRow.createCell(4).setCellValue("食物");
		dataRow.createCell(5).setCellValue("小李");
		dataRow.createCell(6).setCellValue("18511111119");
		dataRow.createCell(7).setCellValue("天津市河北区中山路99号");
		dataRow.createCell(8).setCellValue("小黄");
		dataRow.createCell(9).setCellValue("18511111118");
		dataRow.createCell(10).setCellValue("内蒙古自治区呼和浩特市和平路100号");
		dataRow.createCell(11).setCellValue("5");
		/* 输出文件 */
		String filename = "工作单导入模板.xls";// 设置文件名
		String contentType = ServletActionContext.getServletContext().getMimeType(filename);// 根据文件名动态获取输出格式
		ServletOutputStream out = ServletActionContext.getResponse().getOutputStream();// 设置输出流
		ServletActionContext.getResponse().setContentType(contentType);
		String agent = ServletActionContext.getRequest().getHeader("User-Agent");// 获取浏览器类型
		filename = FileUtils.encodeDownloadFilename(filename, agent);// 根据浏览器对文件名进行编码
		ServletActionContext.getResponse().setHeader("content-disposition", "attachment;filename=" + filename);
		workbook.write(out);
		return NONE;
	}

	private String oldId;

	public void setOldId(String oldId) {
		this.oldId = oldId;
	}

	/**
	 * 修改数据
	 */
	@RequiresPermissions("workordermanage-edit")
	public String edit() {
		if(model.getId().equals(oldId)){
			//id没改变
			workordermanageService.saveorupdate(model);
		}else {
			//id改变了
			workordermanageService.editIdChange(model,oldId);
		}
		return LIST;
	}

	/**
	 * 查找所有id
	 */
	public String findAllId() {
		List<QpWorkordermanage> list = workordermanageService.findAll();
		String allIds = "";
		for (QpWorkordermanage workordermanage : list) {
			String id = workordermanage.getId();
			allIds += id + ",";
		}
		int length = allIds.length();
		allIds = allIds.substring(0, length - 1);// 去除最后的,
		ServletActionContext.getResponse().setContentType("text/plain;charset=utf-8");// 设置输出为文本
		try {
			ServletActionContext.getResponse().getWriter().print(allIds);// 将从数据库查询到的数据输回
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;
	}
	
}
