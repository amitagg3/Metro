package com.metro.mcrm.app.mcrmApp.entity;
import java.util.Date;

public class MesconCustomer {
	private Date Insert_time_stamp;
	private String Operation;
	private String Country;
	private int Store_Num;
	private String Payload;
	private int	Processing_cd;
	
	
	public MesconCustomer(Date insert_time_stamp, String operation,
			String country, int store_Num, String payload, int processing_cd) {
		super();
		this.Insert_time_stamp = insert_time_stamp;
		this.Operation = operation;
		this.Country = country;
		this.Store_Num = store_Num;
		this.Payload = payload;
		this.Processing_cd = processing_cd;
	}
		
	@Override
	public String toString() {
		return "\nMesconCustomer [Insert_time_stamp=" + Insert_time_stamp
				+ ", Operation=" + Operation + ", Country=" + Country
				+ ", Store_Num=" + Store_Num + ", Processing_cd="
				+ Processing_cd + "]";
	}

	public MesconCustomer() {
		super();
	}

	public Date getInsert_time_stamp() {
		return Insert_time_stamp;
	}
	public void setInsert_time_stamp(Date insert_time_stamp) {
		Insert_time_stamp = insert_time_stamp;
	}
	public String getOperation() {
		return Operation;
	}
	public void setOperation(String operation) {
		Operation = operation;
	}
	public String getCountry() {
		return Country;
	}
	public void setCountry(String country) {
		Country = country;
	}
	public int getStore_Num() {
		return Store_Num;
	}
	public void setStore_Num(int store_Num) {
		Store_Num = store_Num;
	}
	public String getPayload() {
		return Payload;
	}
	public void setPayload(String payload) {
		Payload = payload;
	}
	public int getProcessing_cd() {
		return Processing_cd;
	}
	public void setProcessing_cd(int processing_cd) {
		Processing_cd = processing_cd;
	}
	
	
	


}
