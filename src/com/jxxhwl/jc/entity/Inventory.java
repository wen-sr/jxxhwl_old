package com.jxxhwl.jc.entity;

public class Inventory {
	private String id;
	private String issuenumber;
	private String subcode;
	private int qtyreceipt;
	private int qtyallocated;
	private int qtyshipped;
	private int qtyfree;
	private String barcode;
	private double price;
	private String descr;
	private int pack;
	private String publisher;
	private String shortname;
	private String receiptId;
	private int bundle;
	public int getBundle() {
		return bundle;
	}
	public void setBundle(int bundle) {
		this.bundle = bundle;
	}
	public String getReceiptId() {
		return receiptId;
	}
	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
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
	public int getPack() {
		return pack;
	}
	public void setPack(int pack) {
		this.pack = pack;
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
	public int getQtyreceipt() {
		return qtyreceipt;
	}
	public void setQtyreceipt(int qtyreceipt) {
		this.qtyreceipt = qtyreceipt;
	}
	public int getQtyallocated() {
		return qtyallocated;
	}
	public void setQtyallocated(int qtyallocated) {
		this.qtyallocated = qtyallocated;
	}
	public int getQtyshipped() {
		return qtyshipped;
	}
	public void setQtyshipped(int qtyshipped) {
		this.qtyshipped = qtyshipped;
	}
	public int getQtyfree() {
		return qtyfree;
	}
	public void setQtyfree(int qtyfree) {
		this.qtyfree = qtyfree;
	}
	public Inventory() {
		super();
	}
	public Inventory(String issuenumber, String subcode, int qtyallocated) {
		super();
		this.issuenumber = issuenumber;
		this.subcode = subcode;
		this.qtyallocated = qtyallocated;
	}
	public Inventory(String issuenumber, String subcode, int qtyreceipt,
			int qtyallocated) {
		super();
		this.issuenumber = issuenumber;
		this.subcode = subcode;
		this.qtyreceipt = qtyreceipt;
		this.qtyallocated = qtyallocated;
	}
	public Inventory(String issuenumber, String subcode, String barcode) {
		super();
		this.issuenumber = issuenumber;
		this.subcode = subcode;
		this.barcode = barcode;
	}
	public Inventory(String issuenumber, String subcode) {
		super();
		this.issuenumber = issuenumber;
		this.subcode = subcode;
	}
	public Inventory(String issuenumber, String subcode, int qtyreceipt,
			int qtyallocated, int pack) {
		super();
		this.issuenumber = issuenumber;
		this.subcode = subcode;
		this.qtyreceipt = qtyreceipt;
		this.qtyallocated = qtyallocated;
		this.pack = pack;
	}
	public Inventory(String issuenumber, String subcode, int qtyreceipt,
			int pack, String receiptId) {
		super();
		this.issuenumber = issuenumber;
		this.subcode = subcode;
		this.qtyreceipt = qtyreceipt;
		this.pack = pack;
		this.receiptId = receiptId;
	}
	
}
