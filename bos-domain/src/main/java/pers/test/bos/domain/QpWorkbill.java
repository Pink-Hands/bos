package pers.test.bos.domain;
// Generated 2020-11-8 16:07:48 by Hibernate Tools 5.3.0.Beta2

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 工单,分配取派员
 */
public class QpWorkbill implements java.io.Serializable {

	private String id;
	private BcStaff bcStaff;
	private QpNoticebill qpNoticebill;
	private String type;// 工单类型：新、追、改、销
	private String pickstate;// 取件状态：未取件、取件中、已取件
	private Date buildtime;
	private Integer attachbilltimes;
	private String remark;

	public static final String TYPE_1 = "新单";
	public static final String TYPE_2 = "追单";
	public static final String TYPE_3 = "改单";
	public static final String TYPE_4 = "销单";

	public static final String PICKSTATE_NO = "未取件";
	public static final String PICKSTATE_RUNNING = "取件中";
	public static final String PICKSTATE_YES = "已取件";

	public QpWorkbill() {
	}

	public QpWorkbill(String id) {
		this.id = id;
	}

	public QpWorkbill(String id, Date buildtime) {
		this.id = id;
		this.buildtime = buildtime;
	}

	public QpWorkbill(BcStaff bcStaff) {
		this.bcStaff = bcStaff;
	}

	public QpWorkbill(String id, BcStaff bcStaff, QpNoticebill qpNoticebill, String type, String pickstate,
			Date buildtime, Integer attachbilltimes, String remark) {
		this.id = id;
		this.bcStaff = bcStaff;
		this.qpNoticebill = qpNoticebill;
		this.type = type;
		this.pickstate = pickstate;
		this.buildtime = buildtime;
		this.attachbilltimes = attachbilltimes;
		this.remark = remark;
	}

	public String getBuildtimeString() {
		if (buildtime != null) {
			String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(buildtime);
			return format;
		} else {
			return "";
		}
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BcStaff getBcStaff() {
		return this.bcStaff;
	}

	public void setBcStaff(BcStaff bcStaff) {
		this.bcStaff = bcStaff;
	}

	public QpNoticebill getQpNoticebill() {
		return this.qpNoticebill;
	}

	public void setQpNoticebill(QpNoticebill qpNoticebill) {
		this.qpNoticebill = qpNoticebill;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPickstate() {
		return this.pickstate;
	}

	public void setPickstate(String pickstate) {
		this.pickstate = pickstate;
	}

	public Date getBuildtime() {
		return this.buildtime;
	}

	public void setBuildtime(Date buildtime) {
		this.buildtime = buildtime;
	}

	public Integer getAttachbilltimes() {
		return this.attachbilltimes;
	}

	public void setAttachbilltimes(Integer attachbilltimes) {
		this.attachbilltimes = attachbilltimes;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
