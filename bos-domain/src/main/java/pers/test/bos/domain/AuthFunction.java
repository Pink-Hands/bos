package pers.test.bos.domain;
// Generated 2020-11-14 14:49:18 by Hibernate Tools 5.3.0.Beta2

import java.util.HashSet;
import java.util.Set;

/**
 * 权限实体
 */
public class AuthFunction implements java.io.Serializable {

	private String id;
	private AuthFunction parentFunction;//对应的上级权限
	private String name;
	private String text;//text == name
	private String code;//关键字
	private String description;
	private String pageaction;
	private String generatemenu;//是否生成菜单,1是0否
	private Integer zindex;
	private Set children = new HashSet(0);//对应的下级权限
	private Set roles = new HashSet(0);//对应的角色

	public String getpId() {
		if(parentFunction == null) {
			return "0";
		}
		return parentFunction.getId();
	}
	
	
	public AuthFunction() {
	}

	public AuthFunction(String id) {
		this.id = id;
	}

	public AuthFunction(String id, AuthFunction authFunction, String name, String code, String description, String pageaction,
			String generatemenu, Integer zindex, Set children, Set roles) {
		this.id = id;
		this.parentFunction = authFunction;
		this.name = name;
		this.code = code;
		this.description = description;
		this.pageaction = pageaction;
		this.generatemenu = generatemenu;
		this.zindex = zindex;
		this.children = children;
		this.roles = roles;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AuthFunction getParentFunction() {
		return parentFunction;
	}

	public void setParentFunction(AuthFunction parentFunction) {
		this.parentFunction = parentFunction;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		this.text = name;//属性驱动同时赋值给text
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPageaction() {
		return pageaction;
	}

	public void setPageaction(String pageaction) {
		this.pageaction = pageaction;
	}

	public String getGeneratemenu() {
		return generatemenu;
	}

	public void setGeneratemenu(String generatemenu) {
		this.generatemenu = generatemenu;
	}

	public Integer getZindex() {
		return zindex;
	}

	public void setZindex(Integer zindex) {
		this.zindex = zindex;
	}

	public Set getChildren() {
		return children;
	}

	public void setChildren(Set children) {
		this.children = children;
	}

	public Set getRoles() {
		return roles;
	}

	public void setRoles(Set roles) {
		this.roles = roles;
	}

	public String getText() {
		return text;
	}

	

}
