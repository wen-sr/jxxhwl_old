package com.jxxhwl.common.entity;
/**
 * 角色
 * @author liujie
 *
 */
public class Role {
	private long id;
	private String name;
	private String login_id;
	public String getLogin_id() {
		return login_id;
	}
	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
