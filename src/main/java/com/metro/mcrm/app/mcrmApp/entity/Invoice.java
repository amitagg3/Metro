package com.metro.mcrm.app.mcrmApp.entity;


public class Invoice {
	private String invc_num;
	private String order_num;
	private String customer_num;
	private String store;
	private String error_desc;
	private String status;
	private String order_status;
	private String created;
	private String order_created;
	private String bu_id;
	private String row_id;

	public Invoice() {
		super();
	}

	public Invoice(String invc_num, String order_num, String customer_num,
			String store, String error_desc, String status,
			String order_status, String created, String order_created,
			String bu_id, String row_id) {
		super();
		this.invc_num = invc_num;
		this.order_num = order_num;
		this.customer_num = customer_num;
		this.store = store;
		this.error_desc = error_desc;
		this.status = status;
		this.order_status = order_status;
		this.created = created;
		this.order_created = order_created;
		this.bu_id = bu_id;
		this.row_id = row_id;
	}

	public String getInvc_num() {
		return invc_num;
	}

	public void setInvc_num(String invc_num) {
		this.invc_num = invc_num;
	}

	public String getOrder_num() {
		return order_num;
	}

	public void setOrder_num(String order_num) {
		this.order_num = order_num;
	}

	public String getCustomer_num() {
		return customer_num;
	}

	public void setCustomer_num(String customer_num) {
		this.customer_num = customer_num;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public String getError_desc() {
		return error_desc;
	}

	public void setError_desc(String error_desc) {
		this.error_desc = error_desc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrder_status() {
		return order_status;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String string) {
		this.created = string;
	}

	public String getOrder_created() {
		return order_created;
	}

	public void setOrder_created(String string) {
		this.order_created = string;
	}

	public String getBu_id() {
		return bu_id;
	}

	public void setBu_id(String bu_id) {
		this.bu_id = bu_id;
	}

	public String getRow_id() {
		return row_id;
	}

	public void setRow_id(String row_id) {
		this.row_id = row_id;
	}

	@Override
	public String toString() {
		return "Invoice [invc_num=" + invc_num + ", order_num=" + order_num
				+ ", customer_num=" + customer_num + ", store=" + store
				+ ", error_desc=" + error_desc + ", status=" + status
				+ ", order_status=" + order_status + ", created=" + created
				+ ", order_created=" + order_created + ", bu_id=" + bu_id
				+ ", row_id=" + row_id + "]";
	}

	



}