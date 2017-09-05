package com.jxxhwl.common.entity;

import java.util.List;

public class Authority {
	private String id;
	private String text;
	private List<Authority> children;
	private String state;
	private boolean checked;
	public Authority() {
		super();
	}
	public Authority(String id, String text) {
		super();
		this.id = id;
		this.text = text;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<Authority> getChildren() {
		return children;
	}
	public void setChildren(List<Authority> children) {
		this.children = children;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
