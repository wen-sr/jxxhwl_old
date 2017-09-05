package com.jxxhwl.jc.entity;

public class Pack {
	private String id;
	private String issuenumber;
	private String subcode;
	private int pack;
	private int oldpack;
	public int getOldpack() {
		return oldpack;
	}
	public void setOldpack(int oldpack) {
		this.oldpack = oldpack;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIssuenumber() {
		return issuenumber;
	}
	public void setIssuenumber(String issuenumber) {
		this.issuenumber = issuenumber;
	}
	public String getSubcode() {
		return subcode;
	}
	public void setSubcode(String subcode) {
		this.subcode = subcode;
	}
	public int getPack() {
		return pack;
	}
	public void setPack(int pack) {
		this.pack = pack;
	}
	public Pack() {
		super();
	}
	public Pack(String issuenumber, String subcode, int pack) {
		super();
		this.issuenumber = issuenumber;
		this.subcode = subcode;
		this.pack = pack;
	}
	
	
}
