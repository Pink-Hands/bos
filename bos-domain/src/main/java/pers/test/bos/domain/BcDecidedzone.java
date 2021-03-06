package pers.test.bos.domain;
// Generated 2020-10-31 22:56:21 by Hibernate Tools 5.3.0.Beta2

import java.util.HashSet;
import java.util.Set;

/**
 * 定区,包含多个分区,由公司圈定
 */
public class BcDecidedzone implements java.io.Serializable {

	private String id;
	private BcStaff bcStaff;
	private String name;
	private Set bcSubareas = new HashSet(0);

	public BcDecidedzone() {
	}

	public BcDecidedzone(String id) {
		this.id = id;
	}

	public BcDecidedzone(String id, BcStaff bcStaff, String name, Set bcSubareas) {
		this.id = id;
		this.bcStaff = bcStaff;
		this.name = name;
		this.bcSubareas = bcSubareas;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set getBcSubareas() {
		return this.bcSubareas;
	}

	public void setBcSubareas(Set bcSubareas) {
		this.bcSubareas = bcSubareas;
	}

}
