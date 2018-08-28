package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.util.DButil1;
import com.entity.Users;

public class UserDao {
	DButil1 db = new DButil1();
	//添加管理员
	public int addUser(Users user) {
		String sql = "insert into Userinfo values(?,?)";
		Object[] obj = {user.getUserName(),user.getUserPwd()};
		return db.exeUpdata(sql, obj);
	}
	//显示管理员
	public ArrayList<Users> showUser() {
		try {
			String sql = "select * from Userinfo";
			ResultSet rs = db.show(sql);
			ArrayList<Users> list = new ArrayList<>();
			while(rs.next()) {
				Users user = new Users();
				user.setId(rs.getInt("id"));
				user.setUserName(rs.getString("userName"));
				user.setUserPwd(rs.getString("userPwd"));
				list.add(user);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	//删除管理员
	public int deleteUser(int id) {
		String sql = "delete from Userinfo where id="+id+"";
		return db.exeUpdata(sql, null);
	}
	//修改管理员信息
	public int updataUser(Users user, int id) {
		String sql = "update Userinfo set name=?,pwd=? where id="+id+"";
		Object[] obj= {user.getUserName(),user.getUserPwd()};
		return db.exeUpdata(sql,obj);
	}
	//显示单个信息
	public Users showone(int id) {
		try {
			String sql = "select * from Userinfo where id = "+id+"";
			ResultSet rs = db.show(sql);
			while(rs.next()) {
				Users user = new Users();
				user.setId(rs.getInt("id"));
				user.setUserName(rs.getString("userName"));
				user.setUserPwd(rs.getString("userPwd"));
				return user;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	//模糊查找
	public ArrayList<Users> finUser(String name) {
		String sql = "select * from Userinfo where name like '%"+name+"%'";
		ResultSet rs = db.show(sql);
		ArrayList<Users> list = new ArrayList<>();
		try {
			while(rs.next()) {
				Users user = new Users();
				user.setId(rs.getInt("id"));
				user.setId(rs.getInt("id"));
				user.setUserName(rs.getString("userName"));
				user.setUserPwd(rs.getString("userPwd"));
				list.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	//验证账号密码
	public boolean login(String name,String pwd) {
		String sql = "select * from UserInfo where userName = ? and userPwd = ?";
		Object[] obj = {name,pwd};
		return db.exist(sql, obj);
	}
	public String getDate(){
		Date d = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(d);
	}
}
