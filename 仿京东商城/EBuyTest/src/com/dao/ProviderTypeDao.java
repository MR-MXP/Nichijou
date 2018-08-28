package com.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.entity.ProviderType;
import com.util.DButil1;
import com.util.PageUtil;

public class ProviderTypeDao {
	DButil1 db = new DButil1();
	//显示用户
	public PageUtil<ProviderType> GetPageProviders(int pageNo,int pageSize){
		String sql = "select top "+pageSize+" * from GoodsType where typeIds not in(select top (("+pageNo+"-1)*"+pageSize+") typeIds from GoodsType ) order by typeIds asc";
		ResultSet rs = db.show(sql);
		//实例化分页对象
		PageUtil<ProviderType> page = new PageUtil<ProviderType>();
		//集合对象
		ArrayList<ProviderType> list = new ArrayList<ProviderType>();
		try {
			while(rs.next()){
				//实体对象
				ProviderType cu = new ProviderType();
				cu.setId(rs.getInt("typeIds"));
				cu.setName(rs.getString("typeName"));
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
	public PageUtil<ProviderType> pageWord(int pageNo,int pageSize,String name){
			String sql = "select top "+pageSize+" * from GoodsType where typeIds not in(select top (("+pageNo+"-1)*"+pageSize+") typeIds from GoodsType order by typeIds asc) and typeName like '%"+name+"%' order by typeIds asc";
			ResultSet rs = db.show(sql);
			//实例化分页对象
			PageUtil<ProviderType> page = new PageUtil<ProviderType>();
			//集合对象
			ArrayList<ProviderType> list = new ArrayList<ProviderType>();
			try {
				while(rs.next()){
					//实体对象
					ProviderType cu = new ProviderType();
					cu.setId(rs.getInt("typeIds"));
					cu.setName(rs.getString("typeName"));
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
	public ArrayList<ProviderType> getTypeArr(){
		String sql = "select * from GoodsType";
		ArrayList<ProviderType> list = new ArrayList<ProviderType>();
		ResultSet rs = db.show(sql);
		try {
			while(rs.next()){
				//实体对象
				ProviderType cu = new ProviderType();
				cu.setId(rs.getInt("typeIds"));
				cu.setName(rs.getString("typeName"));
				list.add(cu);
			}
			return list;
		} catch (Exception e) {
			// TODO: handle exception
		}	
		return null;
	}
	//显示单个用户
	public ProviderType getOne(String id){
		String sql = "select * from GoodsType where typeIds="+id;
		ResultSet rs = db.show(sql);
		ProviderType cu = new ProviderType();
		try {
			while(rs.next()){
				cu.setId(rs.getInt("typeIds"));
				cu.setName(rs.getString("typeName"));
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
	public int addPro(String name) {
		String sql = "insert into GoodsType values('"+name+"')";
		return db.exeUpdata(sql, null);
	}
	//删除用户	
	public int delProvider(String id) {
		String sql =  "delete from GoodsType where typeIds="+id;
		return db.exeUpdata(sql, null);
	}
	//修改商品
	public int upProvider(String name,int id){
		String sql = "UPDATE GoodsType set typeName = ? where typeIds = ?";
		Object[] obj={name,id};
		return db.exeUpdata(sql, obj);
	}
	//查询是否重名
	public boolean rProvider(String name){
		String sql = "select * from GoodsType where typeName='"+name+"'";
		db.getclose();
		return db.exist(sql, null);
	}
	//查询总条数
	public int getCount(){
		int count = 0; 
		String sql = "select count(*) from GoodsType";
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
		String sql = "select count(*) from GoodsType where typeName like '%"+name+"%'";
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
}
