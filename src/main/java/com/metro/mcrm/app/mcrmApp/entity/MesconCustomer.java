package com.metro.mcrm.app.mcrmApp.entity;

public class MesconCustomer {
	private String insert_time_stamp;
	private String operation;
	private String country;
	private int store_Num;
	private String payload;
	private int	processing_cd;
	private String key1;
	private String key4;
	private String processed_time;
	private String error_msg;
	
	public String getInsert_time_stamp() {
		return insert_time_stamp;
	}


	public void setInsert_time_stamp(String insert_time_stamp) {
		this.insert_time_stamp = insert_time_stamp;
	}


	public String getKey4() {
		return key4;
	}


	public void setKey4(String key4) {
		this.key4 = key4;
	}


	public String getKey1() {
		return key1;
	}


	public void setKey1(String key1) {
		this.key1 = key1;
	}


	public String getProcessed_time() {
		return processed_time;
	}


	public void setProcessed_time(String processed_time) {
		this.processed_time = processed_time;
	}


	public String getError_msg() {
		return error_msg;
	}


	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}


	public MesconCustomer(String insert_time_stamp, String operation,
			String country, int store_Num, String payload, int processing_cd) {
		super();
		this.insert_time_stamp = insert_time_stamp;
		this.operation = operation;
		this.country = country;
		this.store_Num = store_Num;
		this.payload = payload;
		this.processing_cd = processing_cd;
	}

	
	@Override
	public String toString() {
		return "MesconCustomer [insert_time_stamp=" + insert_time_stamp
				+ ", operation=" + operation + ", country=" + country
				+ ", store_Num=" + store_Num + ", processing_cd="
				+ processing_cd + "]";
	}


	public MesconCustomer() {
		super();
	}

	

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getStore_Num() {
		return store_Num;
	}

	public void setStore_Num(int store_Num) {
		this.store_Num = store_Num;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public int getProcessing_cd() {
		return processing_cd;
	}

	public void setProcessing_cd(int processing_cd) {
		this.processing_cd = processing_cd;
	}

	
	


}
