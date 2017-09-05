package com.jxxhwl.jc.entity;

public class Receipt {
	private String id;
	private String issuenumber;
	private String subcode;
	private String barcode;
	private String descr;
	private String storerkey;
	private String publisher;
	private double price;
	private int qtyreceipt;
	private int pack;
	private String status;
	private String addwho;
	private String adddate;
	private String editwho;
	private String editdate;
	private Double amt;
	private int orderqty;
	private int qtyunreceipt;
	private String receiptno;
	private String printPlant;
	private String printPlantName;
	public String getPrintPlantName() {
		return printPlantName;
	}
	public void setPrintPlantName(String printPlantName) {
		this.printPlantName = printPlantName;
	}
	public String getPrintPlant() {
		return printPlant;
	}
	public void setPrintPlant(String printPlant) {
		this.printPlant = printPlant;
	}
	public String getReceiptno() {
		return receiptno;
	}
	public void setReceiptno(String receiptno) {
		this.receiptno = receiptno;
	}
	public int getQtyunreceipt() {
		return qtyunreceipt;
	}
	public void setQtyunreceipt(int qtyunreceipt) {
		this.qtyunreceipt = qtyunreceipt;
	}
	public int getOrderqty() {
		return orderqty;
	}
	public void setOrderqty(int orderqty) {
		this.orderqty = orderqty;
	}
	public Double getAmt() {
		return amt;
	}
	public void setAmt(Double amt) {
		this.amt = amt;
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
	public String getStorerkey() {
		return storerkey;
	}
	public void setStorerkey(String storerkey) {
		this.storerkey = storerkey;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getQtyreceipt() {
		return qtyreceipt;
	}
	public void setQtyreceipt(int qtyreceipt) {
		this.qtyreceipt = qtyreceipt;
	}
	public int getPack() {
		return pack;
	}
	public void setPack(int pack) {
		this.pack = pack;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	
}
