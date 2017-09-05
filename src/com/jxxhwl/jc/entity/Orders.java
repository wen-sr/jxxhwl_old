package com.jxxhwl.jc.entity;

public class Orders {
	private String id;
	private String issuenumber;
	private String subcode;
	private int orderqty;
	private String addwho;
	private String adddate;
	private String descr;
	private double price;
	private String publisher;
	private String shortname;
	private String barcode;
	private String editwho;
	private String editdate;
	public String getEditwho() {
		return editwho;
	}
	public void setEditwho(String editwho) {
		this.editwho = editwho;
	}
	public String getEditdate() {
		return editdate;
	}
	public void setEditdate(String editdate) {
		this.editdate = editdate;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getShortname() {
		return shortname;
	}
	public void setShortname(String shortname) {
		this.shortname = shortname;
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
	public int getOrderqty() {
		return orderqty;
	}
	public void setOrderqty(int orderqty) {
		this.orderqty = orderqty;
	}
	public String getAddwho() {
		return addwho;
	}
	public void setAddwho(String addwho) {
		this.addwho = addwho;
	}
	public String getAdddate() {
		return adddate;
	}
	public void setAdddate(String adddate) {
		this.adddate = adddate;
	}
	public Orders() {
		super();
	}
	public Orders(String issuenumber, String subcode) {
		super();
		this.issuenumber = issuenumber;
		this.subcode = subcode;
	}
	
}
