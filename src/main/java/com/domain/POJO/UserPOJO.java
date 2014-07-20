package com.domain.POJO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="userPOJO")
public class UserPOJO {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;
	@Column(name="user")
	private String user = "";
	@Column(name="password")
	private String password = "";
	@Column(name="email")
	private String email = "";
	@Column(name="tokenRegister")
	private String tokenRegister = "";
	@Column(name="tokenRecoverPassword")
	private String tokenRecoverPassword = "";
	@Column(name="confirmRegistration")
	private boolean confirmRegistration = false;
	
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTokenRegister() {
		return tokenRegister;
	}
	public void setTokenRegister(String tokenRegister) {
		this.tokenRegister = tokenRegister;
	}
	public String getTokenRecoverPassword() {
		return tokenRecoverPassword;
	}
	public void setTokenRecoverPassword(String tokenRecoverPassword) {
		this.tokenRecoverPassword = tokenRecoverPassword;
	}
	public boolean isConfirmRegistration() {
		return confirmRegistration;
	}
	public void setConfirmRegistration(boolean confirmRegistration) {
		this.confirmRegistration = confirmRegistration;
	}
	@Override
	public String toString() {
		return "UserPOJO [id=" + id + ", user=" + user + ", password="
				+ password + ", email=" + email + ", tokenRegister="
				+ tokenRegister + ", tokenRecoverPassword="
				+ tokenRecoverPassword + ", confirmRegistration="
				+ confirmRegistration + "]";
	}
}