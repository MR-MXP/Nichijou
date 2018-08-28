package com.util;

import java.util.ArrayList;

/**
 * 分页工具包
 * @author Administrator
 *
 */
public class PageUtil<T> {
	//当前页
	private int pageNo;
	//每页显示的条数
	private int pageSize;
	//总条数
	private int pageCount;
	//用于存放分页查询的数据
	private ArrayList<T> listAll;
	
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public ArrayList<T> getListAll() {
		return listAll;
	}
	public void setListAll(ArrayList<T> listAll) {
		this.listAll = listAll;
	}
	//首页的方法
	public int getIndex(){
		return 1;
	}
	//上一页的方法
	public int getUpPage(){
		int pageUp = pageNo-1;
		if(pageUp<1){
			pageUp = 1;
		}
		return pageUp;
	}
	//总页数的方法
	public int getCountPage(){
		int countPage;
		if(pageCount%pageSize==0){
			countPage = pageCount/pageSize;
		}else{
			countPage = pageCount/pageSize+1;
		}
		return countPage;
	}
	//下一页的方法
	public int getDownPage(){
		int pageDown = pageNo+1;
		if(pageDown>getCountPage()){
			pageDown = getCountPage();
		}
		return pageDown;
	}
	//尾页的方法
	public int getLastIndex(){
		return getCountPage();
	}
}
