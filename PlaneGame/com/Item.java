package com;

import java.util.Random;

import javax.swing.ImageIcon;

public class Item {
	ImageIcon ii;
	int x;
	int y;
	Random r=new Random();
	public Item(ImageIcon ii, int x, int y) {
		super();
		this.ii = ii;
		this.x = r.nextInt(450)+30+x;
		this.y = y;
	}
	public Item() {
		super();
	}
}
