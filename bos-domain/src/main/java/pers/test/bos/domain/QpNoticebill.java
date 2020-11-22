package pers.test.bos.domain;
// Generated 2020-11-8 16:07:48 by Hibernate Tools 5.3.0.Beta2

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 业务通知单,接受委托
 */
public class QpNoticebill implements java.io.Serializable {

	private String id;
	private BcStaff bcStaff;
	private TUser TUser;
	private String customerId;
	private String customerName;
	private String delegater;
	private String telephone;
	private String pickaddress;
	private String arrivecity;
	private String product;
	private Date pickdate;
	private Integer num;
	private Double weight;
	private String volume;
	private String remark;
	private String ordertype;//分单类型：自动分单、人工分单
	private Set qpWorkbills = new HashSet(0);

	public static final String ORDERTYPE_AUTO = "自动分单";
	public static final String ORDERTYPE_MAN = "人工分单";
	public QpNoticebill() {
	}

	public QpNoticebill(String id) {
		this.id = id;
	}

	public QpNoticebill(String id, BcStaff bcStaff, TUser TUser, String customerId, String customerName,
			String delegater, String telephone, String pickaddress, String arrivecity, String product, Date pickdate,
			Integer num, Double weight, String volume, String remark, String ordertype, Set qpWorkbills) {
		this.id = id;
		this.bcStaff = bcStaff;
		this.TUser = TUser;
		this.customerId = customerId;
		this.customerName = customerName;
		this.delegater = delegater;
		this.telephone = telephone;
		this.pickaddress = pickaddress;
		this.arrivecity = arrivecity;
		this.product = product;
		this.pickdate = pickdate;
		this.num = num;
		this.weight = weight;
		this.volume = volume;
		this.remark = remark;
		this.ordertype = ordertype;
		this.qpWorkbills = qpWorkbills;
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

	public TUser getTUser() {
		return this.TUser;
	}

	public void setTUser(TUser TUser) {
		this.TUser = TUser;
	}

	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getDelegater() {
		return this.delegater;
	}

	public void setDelegater(String delegater) {
		this.delegater = delegater;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPickaddress() {
		return this.pickaddress;
	}

	public void setPickaddress(String pickaddress) {
		this.pickaddress = pickaddress;
	}

	public String getArrivecity() {
		return this.arrivecity;
	}

	public void setArrivecity(String arrivecity) {
		this.arrivecity = arrivecity;
	}

	public String getProduct() {
		return this.product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Date getPickdate() {
		return this.pickdate;
	}

	public void setPickdate(Date pickdate) {
		this.pickdate = pickdate;
	}

	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Double getWeight() {
		return this.weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getVolume() {
		return this.volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrdertype() {
		return this.ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

	public Set getQpWorkbills() {
		return this.qpWorkbills;
	}

	public void setQpWorkbills(Set qpWorkbills) {
		this.qpWorkbills = qpWorkbills;
	}

}
