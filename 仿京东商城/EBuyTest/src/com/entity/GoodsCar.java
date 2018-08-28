package com.entity;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @author 梅新平
 * 	map<编号,实体>
 * 	创建购物车方法
 *	得到购物车的方法
 *	更改数量
 *	添加商品到购物车
 *	移除单个商品
 *	清空购物车
 *	计算总价格
 */
public class GoodsCar {
	//创建map集合用于存放商品(编号,商品实体对象)
	private HashMap<Integer, GoodsInfo> car = null;
	//构造方法
	public GoodsCar() {
		// TODO Auto-generated constructor stub
		car = new HashMap<Integer, GoodsInfo>();
	}
	//获取包含所有商品的购物车
	public HashMap<Integer, GoodsInfo> getCar(){
		return car;
	}
	//添加商品到购物车 goods要添加的商品
	public void addGoods(GoodsInfo goods){
		if(car.containsKey(goods.getId())){
			//如果有相同的商品，则在原数量上加新的数量
			//good 表示是旧的商品
			GoodsInfo good = car.get(goods.getId());
			good.setpPum(good.getpPum()+goods.getpPum());
		}else{
			//不存在相同id的情况下，直接加入到购物车
			car.put(goods.getId(), goods);
		}
	}
	//修改购物车里面的商品数量
	public void changGoodsNum(int id,int num){
		if(car.containsKey(id)){
			GoodsInfo goods = car.get(id);
			goods.setpPum(num);
		}
	}
	//移除单个商品
	public void removeGoods(int id){
		if(car.containsKey(id)){
			car.remove(id);
		}
	}
	//清空
	public void clearGoods(){
		car.clear();
	}
	//求所有商品的总价格
	public double getTotalSum(){
		double sum = 0.0;
		Iterator<Integer> it = car.keySet().iterator();
		while(it.hasNext()){
			GoodsInfo goods = car.get(it.next());
			sum+=goods.getpPum()*goods.getPrice();
		}
		return sum;
	}
}
