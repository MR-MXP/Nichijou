package com.entity;

/**
 * @author 梅新平
 *加入购物车的商品
 *1.商品实体
 *2.购买数量
 *3.价格
 */
public class GoodsInfo {
	private int id;
	private Providers p;
	private int pPum;
	private float price;
	
	public int getId() {
		return p.getGoodsId();
	}
	public Providers getP() {
		return p;
	}
	public void setP(Providers p) {
		this.p = p;
	}
	public int getpPum() {
		return pPum;
	}
	public void setpPum(int pPum) {
		this.pPum = pPum;
	}
	//商品价格= 商品数量*单个价格
	public float getPrice() {
		return pPum*p.getPrice();
	}
}
