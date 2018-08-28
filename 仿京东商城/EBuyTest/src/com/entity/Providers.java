package com.entity;

public class Providers {
	private int goodsId; //商品编号
	private String typeName;//商品类型
	private String goosName;//商品名
	private float price;//商品价格
	private float discount;//打折
	private int isNew;//是否是新品 0是 1不是
	private int statuss;//是否下架 0是 1不是
	private String photo;//商品图片(商品在文件中的路径名)
	private String ramark;//备注
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getGoosName() {
		return goosName;
	}
	public void setGoosName(String goosName) {
		this.goosName = goosName;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getDiscount() {
		return discount;
	}
	public void setDiscount(float discount) {
		this.discount = discount;
	}
	public int getIsNew() {
		return isNew;
	}
	public void setIsNew(int isNew) {
		this.isNew = isNew;
	}
	public int getStatuss() {
		return statuss;
	}
	public void setStatuss(int statuss) {
		this.statuss = statuss;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getRamark() {
		return ramark;
	}
	public void setRamark(String ramark) {
		this.ramark = ramark;
	}
	
}
