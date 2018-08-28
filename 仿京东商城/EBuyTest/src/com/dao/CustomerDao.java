package com.dao;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.entity.Customers;
import com.util.DButil1;
import com.util.PageUtil;

public class CustomerDao {
	DButil1 db = new DButil1();
	//显示用户
	public PageUtil<Customers> showPageUser(int pageNo,int pageSize){
		String sql = "select top "+pageSize+" * from customerInfo where id not in(select top (("+pageNo+"-1)*"+pageSize+") id from customerInfo)";
		ResultSet rs = db.show(sql);
		//实例化分页对象
		PageUtil<Customers> page = new PageUtil<Customers>();
		//集合对象
		ArrayList<Customers> list = new ArrayList<Customers>();
		try {
			while(rs.next()){
				//实体对象
				Customers cu = new Customers();
				cu.setId(rs.getInt("id"));
				cu.setName(rs.getString("name"));
				cu.setSex(rs.getString("sex"));
				cu.setMovePhone(rs.getString("movePhone"));
				cu.setEmail(rs.getString("email"));
				cu.setPwd(rs.getString("pwd"));
				cu.setAddres(rs.getString("addres"));
				cu.setRegisterTime(rs.getString("registerTime"));
				cu.setVip(rs.getInt("vip"));
				list.add(cu);
			}
			page.setPageNo(pageNo);
			page.setPageSize(pageSize);
			page.setPageCount(getCount());
			page.setListAll(list);
			return page;
		} catch (Exception e) {
			// TODO: handle exception
		}	
		return null;
	}
	//模糊查询
	public PageUtil<Customers> pageWord(int pageNo,int pageSize,String name){
		String sql = "select top "+pageSize+" * from customerInfo where id not in(select top (("+pageNo+"-1)*"+pageSize+") id from customerInfo) and name like '%"+name+"%'";
		ResultSet rs = db.show(sql);
		//实例化分页对象
		PageUtil<Customers> page = new PageUtil<Customers>();
		//集合对象
		ArrayList<Customers> list = new ArrayList<Customers>();
		try {
			while(rs.next()){
				//实体对象
				Customers cu = new Customers();
				cu.setId(rs.getInt("id"));
				cu.setName(rs.getString("name"));
				cu.setSex(rs.getString("sex"));
				cu.setMovePhone(rs.getString("movePhone"));
				cu.setEmail(rs.getString("email"));
				cu.setPwd(rs.getString("pwd"));
				cu.setAddres(rs.getString("addres"));
				cu.setRegisterTime(rs.getString("registerTime"));
				cu.setVip(rs.getInt("vip"));
				list.add(cu);
			}
			page.setPageNo(pageNo);
			page.setPageSize(pageSize);
			page.setPageCount(getCount(name));
			page.setListAll(list);
			return page;
		} catch (Exception e) {
			// TODO: handle exception
		}	
		return null;
	}
	//查询总条数
	public int getCount(){
		int count = 0; 
		String sql = "select count(*) from customerInfo";
		ResultSet rs = db.show(sql);
		try {
			if(rs.next()){
				count = rs.getInt(1);
			}
		return count;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
		 
	}
	//模糊查询总条数
	public int getCount(String name){
		int count = 0; 
		String sql = "select count(*) from customerInfo where name like '%"+name+"%'";
		ResultSet rs = db.show(sql);
		try {
			if(rs.next()){
				count = rs.getInt(1);
			}
		return count;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
		 
	}
	//添加用户
	public int addCustomer(Customers c) {
		String sql = "insert into customerInfo values(?,?,?,?,?,?,?,?)";
		Object[] obj = {c.getName(),c.getSex(),c.getMovePhone(),c.getEmail(),c.getPwd(),c.getAddres(),c.getRegisterTime(),c.getVip()};
		return db.exeUpdata(sql, obj);
	}
	//修改用户
	public int updataC(Customers c){
		String sql = "UPDATE customerInfo set name=?,sex=?,movePhone=?,addres=?,vip=? where id = ?";
		Object[] obj={c.getName(),c.getSex(),c.getMovePhone(),c.getAddres(),c.getVip(),c.getId()};
		return db.exeUpdata(sql, obj);
	}
	//显示单个用户
	public Customers getOne(String id){
		String sql = "select * from customerInfo where id="+id;
		ResultSet rs = db.show(sql);
		Customers cu = new Customers();
		try {
			while(rs.next()){
				cu.setId(rs.getInt("id"));
				cu.setName(rs.getString("name"));
				cu.setSex(rs.getString("sex"));
				cu.setMovePhone(rs.getString("movePhone"));
				cu.setEmail(rs.getString("email"));
				cu.setPwd(rs.getString("pwd"));
				cu.setAddres(rs.getString("addres"));
				cu.setRegisterTime(rs.getString("registerTime"));
				cu.setVip(rs.getInt("vip"));
			}
			return cu;
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			//关闭数据库
			db.getclose();
		}
		return null;
	}
	//删除客户
	public int delCustomer(String[] id){
		int count=0;
		for(int i=0;i<id.length;i++){ 
			String sql = "delete from customerInfo where id="+id[i].toString();
			if(db.exeUpdata(sql, null)>0){
				count+=1;
			}
		}
		db.getclose();
		return count;
	}

	//客户的数量
	public int cNum(){
		String sql = "select count(*) from customerInfo";
		ResultSet rs = db.show(sql);
		int num;
		try {
			if(rs.next()){
				num = rs.getInt(1);
				return num;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			//关闭数据库
			db.getclose();
		}
		return 0;
	}
	//检查是否重名
	public boolean rCustomer(String email){
		String sql = "select * from customerInfo where email='"+email+"'";
		db.getclose();
		return db.exist(sql, null);
	}
	//检查用户账号密码是否正确
	public boolean rCusertomer(String name,String pwd){
		String sql = "select * from customerInfo where email = ? and pwd = ?";
		Object[] obj={name,pwd};
		return db.exist(sql, obj);
	}
	//获取当前时间
	public String getDate(){
		Date d = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(d);
	}
}
