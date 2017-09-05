package com.jxxhwl.jc.entity;

public class ProductionPlan {
	private String id;
	private String  plantingName;
	private String  subcode;
	private double  price;
	private String  descr;
	private String  publisher;
	private int  qtyPerItem;
	private int  qtyDeliver;
	private int  qtyCase;
	private String  batchno;
	private String deliverDate ;
	private String deliverDateEnd ;
	private String  handBagDate;
	private String  handBagDateEnd;
	private String  type;
	private String  addwho;
	private String adddate ;
	private String status ;
	private String remark ;
	public String getDeliverDateEnd() {
		return deliverDateEnd;
	}
	public void setDeliverDateEnd(String deliverDateEnd) {
		this.deliverDateEnd = deliverDateEnd;
	}
	public String getHandBagDateEnd() {
		return handBagDateEnd;
	}
	public void setHandBagDateEnd(String handBagDateEnd) {
		this.handBagDateEnd = handBagDateEnd;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPlantingName() {
		return plantingName;
	}
	public void setPlantingName(String plantingName) {
		this.plantingName = plantingName;
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
	public int getQtyPerItem() {
		return qtyPerItem;
	}
	public void setQtyPerItem(int qtyPerItem) {
		this.qtyPerItem = qtyPerItem;
	}
	public int getQtyDeliver() {
		return qtyDeliver;
	}
	public void setQtyDeliver(int qtyDeliver) {
		this.qtyDeliver = qtyDeliver;
	}
	public int getQtyCase() {
		return qtyCase;
	}
	public void setQtyCase(int qtyCase) {
		this.qtyCase = qtyCase;
	}
	public String getBatchno() {
		return batchno;
	}
	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}
	public String getDeliverDate() {
		return deliverDate;
	}
	public void setDeliverDate(String deliverDate) {
		this.deliverDate = deliverDate;
	}
	public String getHandBagDate() {
		return handBagDate;
	}
	public void setHandBagDate(String handBagDate) {
		this.handBagDate = handBagDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
