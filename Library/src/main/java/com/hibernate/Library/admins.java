package com.hibernate.Library;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class admins {

	public admins() {
		// TODO Auto-generated constructor stub
	}
	@Id
	private int loginId;
	private int password;
	public int getLoginId() {
		return loginId;
	}
	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}
	public int getPassword() {
		return password;
	}
	public void setPassword(int password) {
		this.password = password;
	}
	public admins(int loginId, int password) {
		super();
		this.loginId = loginId;
		this.password = password;
	}

}
