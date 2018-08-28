package com.dao;

import java.sql.ResultSet;
import com.util.DButil1;

public class AdminDao {
	DButil1 db = new DButil1();
	//检查密码
	public String rpwd(String name){
		String pwd = null;
		String sql="select userPwd from UserInfo where userName = '"+name+"'";
		ResultSet rs = db.show(sql);
		try {
			if(rs.next()){
				pwd = rs.getString(1);
			}
			return pwd;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	//修改密码
	public int upPwd(String name,String pwd){
		String sql = "UPDATE UserInfo set userPwd = ? where userName = ?";
		Object[] obj={pwd,name};
		return db.exeUpdata(sql, obj);
	}
}
