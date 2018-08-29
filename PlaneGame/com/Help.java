package com;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Help extends JFrame implements MouseListener{
	Container c;
	//构造函数
	public Help(){
		this.setTitle("飞机大战");
		this.setSize(512,700);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(3);
		//添加鼠标监听事件
		this.addMouseListener(this);
		c=this.getContentPane();
		c.setLayout(new BorderLayout());
	}
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		g.drawImage(new ImageIcon("./com/map/help.png").getImage(), 0, 0, null);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		//判断点击开始按钮区域，返回开始界面
		if(e.getX()>=38&&e.getX()<=90&&e.getY()>=50&&e.getY()<=100){
			WelCome mwel=new WelCome();
			//关闭当前窗口
			Help.this.dispose();
			mwel.setVisible(true);		
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
