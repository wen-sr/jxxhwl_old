package com.jxxhwl.jc.entity;

import java.util.List;

public class Accordion {
	private long accordion_id;
	private String accordion_name;
	private String accordion_icon;
	private String accordion_url;
	private String accordion_flag;
	private String accordion_group;
	private String order_by;
	private long function_id;
	private long foo_id;
	private List<Accordion> accordions;
	public List<Accordion> getAccordions() {
		return accordions;
	}
	public void setAccordions(List<Accordion> accordions) {
		this.accordions = accordions;
	}
	public long getAccordion_id() {
		return accordion_id;
	}
	public void setAccordion_id(long accordion_id) {
		this.accordion_id = accordion_id;
	}
	public String getAccordion_name() {
		return accordion_name;
	}
	public void setAccordion_name(String accordion_name) {
		this.accordion_name = accordion_name;
	}
	public String getAccordion_icon() {
		return accordion_icon;
	}
	public void setAccordion_icon(String accordion_icon) {
		this.accordion_icon = accordion_icon;
	}
	public String getAccordion_url() {
		return accordion_url;
	}
	public void setAccordion_url(String accordion_url) {
		this.accordion_url = accordion_url;
	}
	public String getAccordion_flag() {
		return accordion_flag;
	}
	public void setAccordion_flag(String accordion_flag) {
		this.accordion_flag = accordion_flag;
	}
	public String getAccordion_group() {
		return accordion_group;
	}
	public void setAccordion_group(String accordion_group) {
		this.accordion_group = accordion_group;
	}
	public String getOrder_by() {
		return order_by;
	}
	public void setOrder_by(String order_by) {
		this.order_by = order_by;
	}
	public long getFunction_id() {
		return function_id;
	}
	public void setFunction_id(long function_id) {
		this.function_id = function_id;
	}
	public long getFoo_id() {
		return foo_id;
	}
	public void setFoo_id(long foo_id) {
		this.foo_id = foo_id;
	}
}
