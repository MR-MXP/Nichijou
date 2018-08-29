package com;

import java.util.Random;

import javax.swing.ImageIcon;

public class Enemy {
	int ex;
	int ey;
	ImageIcon eii;
	Random er=new Random();
	public Enemy(ImageIcon eii, int ey, int ex) {
		super();
		this.ex = er.nextInt(400)+ex;
		this.ey = ey;
		this.eii = eii;
	}
	public Enemy() {
		super();     
	}
}
