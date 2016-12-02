package com.yang.control;

import java.io.Serializable;

public class CustomerBean implements Serializable {
	private String custName;
	private String email;
	private String phone;
	private AddressBean address;
	public CustomerBean(){};
	public CustomerBean(String custName,String email,String phone,AddressBean address ){
		this.custName=custName;
		this.email=email;
		this.phone=phone;
		this.address=address;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public AddressBean getAddress() {
		return address;
	}
	public void setAddress(AddressBean address) {
		this.address = address;
	}
	
}
