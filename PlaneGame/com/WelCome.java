package com;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;


public class WelCome extends JFrame implements MouseListener{
	Container c;
	//构造函数
	public WelCome(){
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
		g.drawImage(new ImageIcon("./com/map/bg.png").getImage(),0,0,null);
	}
	public void mouseClicked(MouseEvent e) {
		// TODO 自动生成方法存根
		//判断点击开始按钮区域，开始游戏
		if(e.getX()>=169&&e.getX()<=357&&e.getY()>=563&&e.getY()<=604){
			MainFrame mframe=new MainFrame();
			//关闭当前窗口
			WelCome.this.dispose();
			mframe.setVisible(true);		
		}	
		//判断点击开始按钮区域，游戏帮助
		if(e.getX()>=0&&e.getX()<=60&&e.getY()>=640&&e.getY()<=695){
			Help mhelp=new Help();
			//关闭当前窗口
			WelCome.this.dispose();
			mhelp.setVisible(true);		
		}
		//判断点击开始按钮区域，游戏帮助
		System.out.println(e.getY());
		if(e.getX()>=80&&e.getX()<=135&&e.getY()>=640&&e.getY()<=695){
			About mabout=new About();
			//关闭当前窗口
			WelCome.this.dispose();
			mabout.setVisible(true);		
		}
		//判断点击开始按钮区域，退出游戏
		if(e.getX()>=453&&e.getX()<=510&&e.getY()>=640&&e.getY()<=695){
			//关闭虚拟机
			System.exit(0);
		}
	}
	public void mousePressed(MouseEvent e) {
		// TODO 自动生成方法存根
		
	}
	public void mouseReleased(MouseEvent e) {
		// TODO 自动生成方法存根
		
	}
	public void mouseEntered(MouseEvent e) {
		// TODO 自动生成方法存根
		
	}
	public void mouseExited(MouseEvent e) {
		// TODO 自动生成方法存根
		
	}
}
