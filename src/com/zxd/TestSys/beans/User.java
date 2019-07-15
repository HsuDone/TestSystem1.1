package com.zxd.TestSys.beans;


public class User {
	private String UserId;
	private String Name;
	private String UserPsw;
	private String Phone;
	private String E_mail;
	
	public User() {
	}
	public User(String userId, String name, String userPsw, String phone, String e_mail) {
		super();
		UserId = userId;
		Name = name;
		UserPsw = userPsw;
		Phone = phone;
		E_mail = e_mail;
		
	}
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		this.Name = name;
	}
	public String getUserPsw() {
		return UserPsw;
	}
	public void setUserPsw(String userPsw) {
		UserPsw = userPsw;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		this.Phone = phone;
	}
	public String getE_mail() {
		return E_mail;
	}
	public void setE_mail(String e_mail) {
		this.E_mail = e_mail;
	}
	@Override
	public String toString() {
		return "User [UserId=" + UserId + ", Name=" + Name + ", UserPsw=" + UserPsw + ", Phone=" + Phone + ", E_mail="
				+ E_mail + "]";
	}
	
}
