package com.dao;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.entity.Notice;
import com.util.DButil1;
import com.util.PageUtil;

public class NoticeDao {
	DButil1 db = new DButil1();
	//显示用户
	public PageUtil<Notice> GetPageProviders(int pageNo,int pageSize){
		String sql = "select top "+pageSize+" * from NoticeUser where noticeId not in(select top (("+pageNo+"-1)*"+pageSize+") noticeId from NoticeUser )";// order by id asc
		ResultSet rs = db.show(sql);
		//实例化分页对象
		PageUtil<Notice> page = new PageUtil<Notice>();
		//集合对象
		ArrayList<Notice> list = new ArrayList<Notice>();
		try {
			while(rs.next()){
				//实体对象
				Notice cu = new Notice();
				cu.setId(rs.getInt("noticeId"));
				cu.setTitle(rs.getString("title"));
				cu.setText(rs.getString("content"));
				cu.setUserID(rs.getInt("userID"));
				cu.setUserName(rs.getString("userName"));
				cu.setCreateTime(rs.getString("createTime"));
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
	public PageUtil<Notice> pageWord(int pageNo,int pageSize,String name){
			String sql = "select top "+pageSize+" * from NoticeUser where noticeId not in(select top (("+pageNo+"-1)*"+pageSize+") noticeId from NoticeUser order by id asc) and typeName like '%"+name+"%'";
			ResultSet rs = db.show(sql);
			//实例化分页对象
			PageUtil<Notice> page = new PageUtil<Notice>();
			//集合对象
			ArrayList<Notice> list = new ArrayList<Notice>();
			try {
				while(rs.next()){
					//实体对象
					Notice cu = new Notice();
					cu.setId(rs.getInt("noticeId"));
					cu.setTitle(rs.getString("title"));
					cu.setText(rs.getString("content"));
					cu.setUserID(rs.getInt("userID"));
					cu.setUserName(rs.getString("userName"));
					cu.setCreateTime(rs.getString("createTime"));
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
	//查询所有类型
	public ArrayList<Notice> getTypeArr(){
		String sql = "select * from NoticeUser";
		ArrayList<Notice> list = new ArrayList<Notice>();
		ResultSet rs = db.show(sql);
		try {
			while(rs.next()){
				//实体对象
				Notice cu = new Notice();
				cu.setId(rs.getInt("noticeId"));
				cu.setTitle(rs.getString("title"));
				cu.setText(rs.getString("content"));
				cu.setUserID(rs.getInt("userID"));
				cu.setUserName(rs.getString("userName"));
				cu.setCreateTime(rs.getString("createTime"));
				list.add(cu);
			}
			return list;
		} catch (Exception e) {
			// TODO: handle exception
		}	
		return null;
	}
	//显示单个用户
	public Notice getOne(String id){
		String sql = "select * from NoticeUser where noticeId="+id;
		ResultSet rs = db.show(sql);
		Notice cu = new Notice();
		try {
			while(rs.next()){
				cu.setId(rs.getInt("noticeId"));
				cu.setTitle(rs.getString("title"));
				cu.setText(rs.getString("content"));
				cu.setUserID(rs.getInt("userID"));
				cu.setUserName(rs.getString("userName"));
				cu.setCreateTime(rs.getString("createTime"));
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
	//添加用户
	public int addNotice(Notice n) {
		String sql = "insert into Bulletin values(?,?,?,?)";
		String sql1 ="select userID from NoticeUser where userName = '"+n.getUserName()+"'";
		ResultSet rs = db.show(sql1);
		try {
			if(rs.next()){
				int dd = rs.getInt("userID");
				Object[] obj = {n.getTitle(),n.getText(),dd,n.getCreateTime()};
				return db.exeUpdata(sql, obj);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}
	//删除用户	
	public int delNotice(String id) {
		String sql =  "delete from Bulletin where noticeId="+id;
		return db.exeUpdata(sql, null);
	}
	//修改商品
	public int upNotice(Notice n){
		String sql = "UPDATE Bulletin set title = ?,content = ?,userID = ?,createTime = ? where noticeId = ?";
		String sql1 = "select userID from NoticeUser where userName = '"+n.getUserName()+"'";
		ResultSet rs = db.show(sql1);
		try {
			if(rs.next()){
				int dd = rs.getInt(1);
				Object[] obj = {n.getTitle(),n.getText(),dd,n.getCreateTime(),n.getId()};
				return db.exeUpdata(sql, obj);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}
	//查询是否重名
	public boolean rProvider(String name){
		String sql = "select * from Bulletin where typeName='"+name+"'";
		db.getclose();
		return db.exist(sql, null);
	}
	//查询总条数
	public int getCount(){
		int count = 0; 
		String sql = "select count(*) from Bulletin";
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
		String sql = "select count(*) from Bulletin where typeName like '%"+name+"%'";
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
	public String getDate(){
		Date d = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(d);
	}
}
