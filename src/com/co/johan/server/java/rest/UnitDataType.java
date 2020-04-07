package com.co.johan.server.java.rest;

public class UnitDataType {
	
	String IdUnit;
	int idClient;
	String type;
	String Brand;
	String Reference;
	String icon;
	boolean Status;
	
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getIdUnit() {
		return IdUnit;
	}
	public void setIdUnit(String idUnit) {
		IdUnit = idUnit;
	}
	public int getIdClient() {
		return idClient;
	}
	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBrand() {
		return Brand;
	}
	public void setBrand(String brand) {
		Brand = brand;
	}
	public String getReference() {
		return Reference;
	}
	public void setReference(String reference) {
		Reference = reference;
	}
	public boolean isStatus() {
		return Status;
	}
	public void setStatus(boolean status) {
		Status = status;
	}
	

}
