package com.jxxhwl.jc.entity;

public class Sku {
	private String id;
	private String issuenumber;
	private String subcode;
	private double price;
	private String descr;
	private String publisher;
	private String barcode;
	private int pack;
	private String addwho;
	private String shortname;
	private int bundle;
	public int getBundle() {
		return bundle;
	}
	public void setBundle(int bundle) {
		this.bundle = bundle;
	}
	public String getShortname() {
		return shortname;
	}
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
	public String getStorerkey() {
		return storerkey;
	}
	public void setStorerkey(String storerkey) {
		this.storerkey = storerkey;
	}
	private String adddate;
	private String editwho;
	private String editdate;
	private String storerkey;
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
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public int getPack() {
		return pack;
	}
	public void setPack(int pack) {
		this.pack = pack;
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
	public Sku() {
		super();
	}
	public Sku(String issuenumber, String subcode) {
		super();
		this.issuenumber = issuenumber;
		this.subcode = subcode;
	}
	
	
}
