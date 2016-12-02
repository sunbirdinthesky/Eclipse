package com.yang.control;

public class AddressBean {
	private String city;
	private String street;
	private String zipCode;
	public AddressBean(){}
			
	public AddressBean(String city,String street,String zipCode){
		this.city=city;
		this.street=street;
		this.zipCode=zipCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
}
