package pers.test.bos.domain;
// Generated 2020-11-14 14:49:18 by Hibernate Tools 5.3.0.Beta2

import java.util.HashSet;
import java.util.Set;

/**
 * 角色实体
 */
public class AuthRole implements java.io.Serializable {

	private String id;
	private String name;
	private String code;
	private String description;
	private Set authFunctions = new HashSet(0);//权限
	private Set TUsers = new HashSet(0);//角色

	public AuthRole() {
	}

	public AuthRole(String id) {
		this.id = id;
	}
	public AuthRole(String name,int num) {
		this.name = name;
	}

	public AuthRole(String id, String name, String code, String description, Set authFunctions, Set TUsers) {
		this.id = id;
		this.name = name;
		this.code = code;
		this.description = description;
		this.authFunctions = authFunctions;
		this.TUsers = TUsers;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set getAuthFunctions() {
		return this.authFunctions;
	}

	public void setAuthFunctions(Set authFunctions) {
		this.authFunctions = authFunctions;
	}

	public Set getTUsers() {
		return this.TUsers;
	}

	public void setTUsers(Set TUsers) {
		this.TUsers = TUsers;
	}

}
