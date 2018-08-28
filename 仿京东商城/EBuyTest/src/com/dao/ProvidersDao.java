package com.dao;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.entity.Providers;
import com.util.DButil1;
import com.util.PageUtil;

public class ProvidersDao {
	DButil1 db = new DButil1();
	//显示商品
	public PageUtil<Providers> showPageUser(int pageNo,int pageSize){
		String sql = "select top "+pageSize+" * from Goods where goodsId not in(select top (("+pageNo+"-1)*"+pageSize+") goodsId from Goods)";
		ResultSet rs = db.show(sql);
		//实例化分页对象
		PageUtil<Providers> page = new PageUtil<Providers>();
		//集合对象
		ArrayList<Providers> list = new ArrayList<Providers>();
		try {
			while(rs.next()){
				//实体对象
				Providers cu = new Providers();
				cu.setGoodsId(rs.getInt("goodsId"));
				cu.setTypeName(rs.getString("typeName"));
				cu.setGoosName(rs.getString("goosName"));
				cu.setPrice(rs.getFloat("price"));
				cu.setDiscount(rs.getFloat("discount"));
				cu.setIsNew(rs.getInt("isNew"));
				cu.setStatuss(rs.getInt("statuss"));
				cu.setPhoto(rs.getString("photo"));
				cu.setRamark(rs.getString("remark"));
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
	//查询第几条到第几条
	public PageUtil<Providers> showsome(int pageSize,int pageSizes){
		String sql = "select top "+pageSize+" * from Goods where goodsId not in(select top "+pageSizes+" goodsId from Goods)";
		ResultSet rs = db.show(sql);
		//实例化分页对象
		PageUtil<Providers> page = new PageUtil<Providers>();
		//集合对象
		ArrayList<Providers> list = new ArrayList<Providers>();
		try {
			while(rs.next()){
				//实体对象
				Providers cu = new Providers();
				cu.setGoodsId(rs.getInt("goodsId"));
				cu.setTypeName(rs.getString("typeName"));
				cu.setGoosName(rs.getString("goosName"));
				cu.setPrice(rs.getFloat("price"));
				cu.setDiscount(rs.getFloat("discount"));
				cu.setIsNew(rs.getInt("isNew"));
				cu.setStatuss(rs.getInt("statuss"));
				cu.setPhoto(rs.getString("photo"));
				cu.setRamark(rs.getString("remark"));
				list.add(cu);
			}
			page.setPageNo(1);
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
	public PageUtil<Providers> pageWord(int pageNo,int pageSize,String name){
		String sql = "select top "+pageSize+" * from Goods where goodsId not in(select top (("+pageNo+"-1)*"+pageSize+") goodsId from Goods) and goosName like '%"+name+"%'";
		ResultSet rs = db.show(sql);
		//实例化分页对象
		PageUtil<Providers> page = new PageUtil<Providers>();
		//集合对象
		ArrayList<Providers> list = new ArrayList<Providers>();
		try {
			while(rs.next()){
				//实体对象
				Providers cu = new Providers();
				cu.setGoodsId(rs.getInt("goodsId"));
				cu.setTypeName(rs.getString("typeName"));
				cu.setGoosName(rs.getString("goosName"));
				cu.setPrice(rs.getFloat("price"));
				cu.setDiscount(rs.getFloat("discount"));
				cu.setIsNew(rs.getInt("isNew"));
				cu.setStatuss(rs.getInt("statuss"));
				cu.setPhoto(rs.getString("photo"));
				cu.setRamark(rs.getString("remark"));
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
		String sql = "select count(*) from GoodsInfo";
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
		String sql = "select count(*) from GoodsInfo where goosName like '%"+name+"%'";
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
	//添加商品
	public int addGoods(Providers c) {
		String sql = "insert into GoodsInfo values(?,?,?,?,?,?,?,?)";
		String sql1 = "select typeIds from GoodsType where typeName = '"+c.getTypeName()+"'";
		ResultSet rs = db.show(sql1);
		try {
			if(rs.next()){
				int dd = rs.getInt(1);
				Object[] obj = {dd,c.getGoosName(),c.getPrice(),c.getDiscount(),c.getIsNew(),c.getStatuss(),c.getPhoto(),c.getRamark()};
				return db.exeUpdata(sql, obj);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}
	//添加商品图片
	public int addPhoto(String path,String id){
		String sql = "UPDATE GoodsInfo set photo=? where goodsId = ?";
		Object[] obj = {path,id};
		return db.exeUpdata(sql, obj);
	}
	//修改商品
	public int updataC(Providers c){
		String sql = "UPDATE GoodsInfo set typeId=?,goosName=?,price=?,discount=?,isNew=?,statuss=?,remark=? where goodsId = ?";
		String sql1 = "select typeIds from GoodsType where typeName = '"+c.getTypeName()+"'";
		ResultSet rs = db.show(sql1);
		try {
			if(rs.next()){
				int dd = rs.getInt(1);
				Object[] obj = {dd,c.getGoosName(),c.getPrice(),c.getDiscount(),c.getIsNew(),c.getStatuss(),c.getRamark(),c.getGoodsId()};
				return db.exeUpdata(sql, obj);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}
	//显示单个商品
	public Providers getOne(String id){
		String sql = "select * from Goods where goodsId="+id;
		ResultSet rs = db.show(sql);
		Providers cu = new Providers();
		try {
			while(rs.next()){
				cu.setGoodsId(rs.getInt("goodsId"));
				cu.setTypeName(rs.getString("typeName"));
				cu.setGoosName(rs.getString("goosName"));
				cu.setPrice(rs.getFloat("price"));
				cu.setDiscount(rs.getFloat("discount"));
				cu.setIsNew(rs.getInt("isNew"));
				cu.setStatuss(rs.getInt("statuss"));
				cu.setPhoto(rs.getString("photo"));
				cu.setRamark(rs.getString("remark"));
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
			String sql = "delete from GoodsInfo where goodsId="+id[i].toString();
			if(db.exeUpdata(sql, null)>0){
				count+=1;
			}
		}
		db.getclose();
		return count;
	}

	//客户的数量
	public int cNum(){
		String sql = "select count(*) from GoodsInfo";
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
	public boolean rProvider(String goosName){
		String sql = "select * from GoodsInfo where goosName='"+goosName+"'";
		db.getclose();
		return db.exist(sql, null);
	}
	//获取当前时间
	public String getDate(){
		Date d = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(d);
	}
}
