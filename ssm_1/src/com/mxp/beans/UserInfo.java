package com.mxp.beans;

import java.util.Date;

public class UserInfo {
	private int id;
	private String name;
	private String pwd;
	private Date birthday;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public UserInfo() {
	}
	public UserInfo(int id, String name, String pwd, Date birthday) {
		this.id = id;
		this.name = name;
		this.pwd = pwd;
		this.birthday = birthday;
	}
	@Override
	public String toString() {
		return "userinfo [id=" + id + ", name=" + name + ", pwd=" + pwd + ", birthday=" + birthday + "]";
	}
	
}
