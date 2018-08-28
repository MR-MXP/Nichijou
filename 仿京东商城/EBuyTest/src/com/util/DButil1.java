package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
/**
 * 
 * @author 梅新平
 * 数据库工具
 *
 */
public class DButil1 {
	private static String url;
	private static String name;
	private static String pwd;
	private static String driver;
	private Connection conn = null;
	private PreparedStatement ps = null;
	//实例化DButil是启动数据库驱动
	static {
		ResourceBundle rb = ResourceBundle.getBundle("sql");
		url = rb.getString("url");
		name = rb.getString("name");
		pwd = rb.getString("pwd");
		driver = rb.getString("driver");
	}
	public DButil1() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//获取连接对象
	public Connection getConn() {
		try {
			conn = DriverManager.getConnection(url, name, pwd);
			return conn;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	//查询所有用户
	public ResultSet show(String sql) {
		getConn();
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	//查询用户是否存在
	public boolean exist(String sql,Object[] obj) {
		getConn();
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			if(obj!=null) {
				for(int i=0;i<obj.length;i++) {
				 ps.setObject(i+1,obj[i]);
				}
			}
			rs = ps.executeQuery();
			//用户存在
			if(rs.next()) {
				return true;
			//用户不存在
			}else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	//增删改
	public int exeUpdata(String sql,Object[] obj) {
		getConn();
		try {
			ps = conn.prepareStatement(sql);
			if(obj!=null) {
				for(int i=0;i<obj.length;i++) {
				 ps.setObject(i+1,obj[i]);
				}	
			}
			return ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	//关闭数据库
	public void getclose(ResultSet rs) {
		try {
			if(rs!=null) {
				rs.close();
			}
			if(ps!=null) {
				ps.close();
			}
			if(getConn()!=null) {
				getConn().close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void getclose() {
		try {
			if(ps!=null) {
				ps.close();
			}
			if(getConn()!=null) {
				getConn().close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}

