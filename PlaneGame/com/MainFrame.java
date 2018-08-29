package com;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MainFrame extends JFrame implements KeyListener{
	int y=0;//地图坐标
	MyPlane myplane;//声明我的飞机
	Bullet mybullet;//声明我的子弹
	Boss boss;//声明boss
	Boolean flag=true;//判断是否开启线程
	Boolean loop=true;
	int score=0;//游戏分数
	int level=1;//关卡
	int speed=5;//玩家飞机移动速度
	int enspeed=3;//敌方飞机移动速度
	int enp=0;//敌军飞机种类
	Random r=new Random();//声明道具出现随机时间
	int time=20000;
	//声明所需的各种线程
	MoveMap mmap=new MoveMap();
    AddBullet abullet=new AddBullet();
    MoveBullet mbullet=new MoveBullet();
	AddEnplane aeplane=new AddEnplane();
	EnplaneMove epmove=new EnplaneMove();
	Touch touch =new Touch();
	Redraw re=new Redraw();
	AddItem aitem=new AddItem();
	MoveItem mitem=new MoveItem();
	MoveBoss mboss=new MoveBoss();
	AddBossBullet abbullet=new AddBossBullet();
	MoveBossBullet mbbullet=new MoveBossBullet();
	
	
	//gui使用CopyOnWriteArrayList集合，集合分为（list,set,map）
	CopyOnWriteArrayList enplane=new CopyOnWriteArrayList();//声明并创建敌军飞机集合
	CopyOnWriteArrayList boom=new CopyOnWriteArrayList();//声明并创建一个爆炸集合
	CopyOnWriteArrayList blist=new CopyOnWriteArrayList();//声明并创建我的飞机子弹集合
	CopyOnWriteArrayList iitem=new CopyOnWriteArrayList();//声明并创建补给道具集合
	CopyOnWriteArrayList bbullet=new CopyOnWriteArrayList();//声明并创建boss子弹
	public MainFrame(){
		this.setTitle("飞机大战");
		this.setSize(512,700);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(3);
		
		//我的飞机
		myplane=new MyPlane(new ImageIcon("./com/myplane/img_plane_8.png"),200,580,5);
		//boss
		boss=new Boss(new ImageIcon("./com/boss/boss1.png"),2,-200,200);
		//启动线程
		mmap.start();//地图移动线程
        abullet.start();//添加我的子弹线程
        mbullet.start();//我的子弹移动线程
		new Redraw().start();//重绘线程
		aeplane.start();//添加敌军线程
		epmove.start();//敌军移动线程
		touch.start();//碰撞线程
		aitem.start();//补给道具添加线程
		mitem.start();//补给道具移动线程
		mboss.start();
		abbullet.start();
		mbbullet.start();
		
		//给我的飞机加上监听
		this.addKeyListener(this);
		//道具出现的随机时间 (20S-40S之间)
		time=r.nextInt(20000)+time;

	}
	//画板
	@Override
	public void paint(Graphics g) {
		/*画面闪烁原因：线程多，重绘多，画的东西多
		 * 解决方法：利用缓冲画布
		 * */	
		Image bufferImage;
		//根据窗体创建缓冲画布
		bufferImage=this.createImage(512,700);
		//得到缓冲画笔
		Graphics g1=bufferImage.getGraphics();
		//画地图
		if(level==1){		
			g1.drawImage(new ImageIcon("./com/map/img_bg_level_1.jpg").getImage(),0,y,null);
			g1.drawImage(new ImageIcon("./com/map/img_bg_level_1.jpg").getImage(),0,y-768,null);
			g1.drawImage(new ImageIcon("./com/boss/boss1.png").getImage(), boss.x, boss.y, null);
		}
		if(level==2){
			g1.drawImage(new ImageIcon("./com/map/img_bg_level_2.jpg").getImage(),0,y,null);
			g1.drawImage(new ImageIcon("./com/map/img_bg_level_2.jpg").getImage(),0,y-768,null);
			g1.drawImage(new ImageIcon("./com/boss/boss2.png").getImage(), boss.x, boss.y, null);
		}
		if(level==3){
			g1.drawImage(new ImageIcon("./com/map/img_bg_level_3.jpg").getImage(),0,y,null);
			g1.drawImage(new ImageIcon("./com/map/img_bg_level_3.jpg").getImage(),0,y-768,null);
			g1.drawImage(new ImageIcon("./com/boss/boss3.png").getImage(), boss.x, boss.y, null);
		}
		if(level>=4){
			g1.drawImage(new ImageIcon("./com/map/img_bg_level_4.jpg").getImage(),0,y,null);
			g1.drawImage(new ImageIcon("./com/map/img_bg_level_4.jpg").getImage(),0,y-768,null);
			g1.drawImage(new ImageIcon("./com/boss/boss4.png").getImage(), boss.x, boss.y, null);
		}
		//画我的飞机
		g1.drawImage(myplane.ii.getImage(),myplane.mx,myplane.my,null);			
		//画boss血条
		g1.setColor(Color.black);
		g1.drawRect(boss.x+30, boss.y, 200, 15);
		g1.setColor(Color.green);
		g1.fillRect(boss.x+31, boss.y+1, boss.hp, 14);
		//死亡处理，停止子弹线程，飞机消失
		if(myplane.hp<=0){
			myplane.ii=new ImageIcon("");
			abullet.stop();//停止子弹
		}
		//画敌军飞机
		for(int i=0;i<enplane.size();i++){
			g1.drawImage(((Enemy)enplane.get(i)).eii.getImage(),((Enemy)enplane.get(i)).ex,((Enemy)enplane.get(i)).ey,null);
		}
        //画我的子弹
		for(int i=0;i<blist.size();i++){
			g1.drawImage(((Bullet)blist.get(i)).bii.getImage(),((Bullet)blist.get(i)).bx,((Bullet)blist.get(i)).by,null);
		}
		//画boss子弹
		for(int i=0;i<bbullet.size();i++){
			g1.drawImage(((BossBullet)bbullet.get(i)).ii.getImage(),((BossBullet)bbullet.get(i)).x,((BossBullet)bbullet.get(i)).y,null);
		}
		//画爆炸动画
		for(int i=0;i<boom.size();i++){
			try {
				g1.drawImage(((Boom)boom.get(i)).ii.getImage(),((Boom)boom.get(i)).x,((Boom)boom.get(i)).y,null);
			} catch (Exception e) {
				System.out.println("被玩坏了，但程序不会崩！");
			}
		}
		//画游戏分数
		g1.setColor(Color.pink);
		g1.setFont(new Font("华文琥珀",Font.BOLD,18));
		g1.drawString("当前分数:"+score,360,60);
		if(score>200&&score<500){
			speed=8;
			enspeed=4;
		}
		if(score>500){
			speed=11;
			enspeed=5;
		}
		//显示关卡
		if(level==1){
			g1.setFont(new Font("华文琥珀",Font.BOLD,20));
			g1.drawString("第一关",380,90);
		}
		if(level==2){
			g1.setFont(new Font("华文琥珀",Font.BOLD,20));
			g1.drawString("第二关",380,90);
		}
		if(level==3){
			g1.setFont(new Font("华文琥珀",Font.BOLD,20));
			g1.drawString("第三关",380,90);
		}
		if(level==4){
			g1.setFont(new Font("华文琥珀",Font.BOLD,20));
			g1.drawString("第四关",380,90);
		}
		//画我的血条
		g1.drawRect(50,50,100,15);
		g1.setColor(Color.red);
		if(myplane.hp==5){
			g1.fillRect(51,51,99,14);
		}
		if(myplane.hp==4){
			g1.fillRect(51,51,79,14);
		}
		if(myplane.hp==3){
			g1.fillRect(51,51,59,14);
		}
		if(myplane.hp==2){
			g1.fillRect(51,51,39,14);
		}
		if(myplane.hp==1){
			g1.fillRect(51,51,19,14);
		}
		if(myplane.hp==0){
			g1.fillRect(51,51,0,14);
		}
		//画补给道具
		for(int i=0;i<iitem.size();i++){
			g1.drawImage(((Item)iitem.get(i)).ii.getImage(),((Item)iitem.get(i)).x,((Item)iitem.get(i)).y,null);
		}
		//将地图画上主面板
		g.drawImage(bufferImage,0,0,null);
		
	}
	//地图移动线程
	class MoveMap extends Thread{
		@Override
		public void run() {
			// TODO 自动生成方法存根
			super.run();
			while(true){
				try {
					Thread.sleep(60);
					if(y<=768){
						y+=2;
					}else{
						y=0;
					}
				} catch (InterruptedException e) {
					// TODO 自动生成 catch 块
					e.printStackTrace();
				}
			}
			
		}
	}
	//重绘线程
	class Redraw extends Thread{
		@Override
		public void run() {
			// TODO 自动生成方法存根
			super.run();
			while(true){
				try {
					Thread.sleep(1);
					MainFrame.this.repaint();
				} catch (InterruptedException e) {
					// TODO 自动生成 catch 块
					e.printStackTrace();
				}
			}
			
		}
	}
	//向集合添加敌军飞机线程
	class AddEnplane extends Thread{
		@Override
		public void run() {
			while(true){
				try {
					Thread.sleep(2000);
					//敌军飞机种类随机(0-11不包括11的随机数，图片从1开始)
					enp=r.nextInt(11)+1;
					//System.out.println(enp);
					enplane.add(new Enemy(new ImageIcon("./com/enemy/d"+enp+".png"),0,10));
				} catch (InterruptedException e) {
					// TODO 自动生成 catch 块
					e.printStackTrace();
				}
			}
		}
	}
	//敌军飞机移动线程
	class EnplaneMove extends Thread{
		@Override
		public void run() {
			for(;;){
				try {
					Thread.sleep(50);
					//遍历每一个敌军飞机
					for(int i=0;i<enplane.size();i++){
						((Enemy)enplane.get(i)).ey+=enspeed;
					}
				} catch (InterruptedException e) {
					// TODO 自动生成 catch 块
					e.printStackTrace();
				}
			}
		}
	}
	//判断碰撞线程(子弹、飞机、补给道具)
	class Touch extends Thread{
		@Override
		public void run() {
			for(;;){			
				try {
					Thread.sleep(1);
					//我的飞机的图片转化为矩形框
					Rectangle rec=new Rectangle(myplane.mx,myplane.my,myplane.ii.getIconWidth(),
							myplane.ii.getIconHeight());
					//补给道具的图片转化为矩形框
					for(int i=0;i<iitem.size();i++){
						Rectangle ritem=new Rectangle(((Item)iitem.get(i)).x,((Item)iitem.get(i)).y,
								((Item)iitem.get(i)).ii.getIconWidth(),
								((Item)iitem.get(i)).ii.getIconHeight());
						//如果补给道具和我的飞机碰撞
						if(rec.intersects(ritem)){
							if(myplane.hp<5){
								myplane.hp+=1;
								iitem.remove(i);
							}
						}
					}
					//遍历boss子弹击中玩家飞机
					for(int i=0;i<bbullet.size();i++){
						//遍历boss飞机的子弹
						Rectangle recbbullet=new Rectangle(((BossBullet)bbullet.get(i)).x,
								((BossBullet)bbullet.get(i)).y,((BossBullet)bbullet.get(i)).ii.getIconWidth(),
								((BossBullet)bbullet.get(i)).ii.getIconHeight());
						if(rec.intersects(recbbullet)){
							myplane.hp--;
							bbullet.remove(i);
							//判断游戏结束
							if(myplane.hp==0){
								//清除子弹
								blist.clear();
								int n=JOptionPane.showConfirmDialog(null,"是否重新开始游戏","游戏结束",2,2,null);
								if(n==0){
									//关闭当前窗口
									MainFrame.this.dispose();
									new MainFrame().setVisible(true);
								}else{
									//关闭虚拟机
									System.exit(0);
								}
							}
						}
					}
					//遍历飞机碰撞
					for(int i=0;i<enplane.size();i++){
						//敌军飞机的图片转化成矩形框
						Rectangle rec1=new Rectangle(((Enemy)enplane.get(i)).ex,
								((Enemy)enplane.get(i)).ey,((Enemy)enplane.get(i)).eii.getIconWidth(),
								((Enemy)enplane.get(i)).eii.getIconHeight());
						//boss飞机的图片转化成矩形框
						Rectangle recboss=new Rectangle(boss.x,boss.y,boss.ii.getIconWidth(),
								boss.ii.getIconHeight());
						//我的飞机和敌军飞机相撞
						if(rec.intersects(rec1)||rec.intersects(recboss)){
							//System.out.println("撞击了");
							score+=5;//加分
							myplane.hp-=1;
							//判断游戏结束
							if(myplane.hp==0){
								//清除子弹
								blist.clear();
								int n=JOptionPane.showConfirmDialog(null,"是否重新开始游戏","游戏结束",2,2,null);
								if(n==0){
									//关闭当前窗口
									MainFrame.this.dispose();
									new MainFrame().setVisible(true);
								}else{
									//关闭虚拟机
									System.exit(0);
								}
							}
							enplane.remove(i);
							//爆炸效果
							Booms booms=new Booms();
							booms.x=rec1.x;
							booms.y=rec1.y;
							booms.start();
						}						
					}
					//遍历被我的子弹消灭的敌军飞机
					for(int i=0;i<blist.size();i++){
						//遍历我的飞机的子弹
						Rectangle recbullet=new Rectangle(((Bullet)blist.get(i)).bx,
								((Bullet)blist.get(i)).by,((Bullet)blist.get(i)).bii.getIconWidth(),
								((Bullet)blist.get(i)).bii.getIconHeight());
						//我的子弹打中boss
						//boss飞机的图片转化成矩形框
						Rectangle recboss=new Rectangle(boss.x,boss.y,boss.ii.getIconWidth(),
								boss.ii.getIconHeight());
						if(recbullet.intersects(recboss)){
							score++;
							boss.hp-=2;
							blist.remove(i);
							if(boss.hp==0){
								level++;
								boss.hp=200;
								if(level>4){
									int n=JOptionPane.showConfirmDialog(null,"是否重新开始游戏","游戏通关",2,2,null);
									if(n==0){
										//关闭当前窗口
										MainFrame.this.dispose();
										new MainFrame().setVisible(true);
									}else{
										//关闭虚拟机
										System.exit(0);
									}
								}
							}
						}
						for(int j=0;j<enplane.size();j++){							
							//敌军飞机
							Rectangle rec1=new Rectangle(((Enemy)enplane.get(j)).ex,
									((Enemy)enplane.get(j)).ey,((Enemy)enplane.get(j)).eii.getIconWidth(),
									((Enemy)enplane.get(j)).eii.getIconHeight());
							//判断子弹是否击中
							if(recbullet.intersects(rec1)){
								blist.remove(i);
								enplane.remove(j);
								score+=5;//分数
								//爆炸效果
								Booms booms1=new Booms();
								booms1.x=rec1.x;
								booms1.y=rec1.y;
								booms1.start();
								break;
							}
						}
					}				
				} catch (InterruptedException e) {
					// TODO 自动生成 catch 块
					e.printStackTrace();
				}
			}
		}
	}
	//爆炸线程
	class Booms extends Thread{
		int x=0,y=0;
		@Override
		public void run() {		
			for(int i=1;i<=22;i++){
				try {
					Thread.sleep(10);
					boom.add(new Boom(new ImageIcon("./com/booms/"+i+".png"),x,y));
				} catch (InterruptedException e) {
					// TODO 自动生成 catch 块
					e.printStackTrace();
					System.out.println("爆炸了");
				}
			}
			boom.clear();
		}
	}
	//往集合中添加我的子弹数据
	class AddBullet extends Thread{
		@Override
		public void run() {
			while(true){
				try {
					Thread.sleep(300);
					blist.add(new Bullet(new ImageIcon("./com/bullet/img_bullet_1.png"),myplane.mx+51,myplane.my-35));
				} catch (InterruptedException e) {
					// TODO 自动生成 catch 块
					e.printStackTrace();
				}
			}
		}
	}
	//往集合中添加boss子弹数据
	class AddBossBullet extends Thread{
		@Override
		public void run() {
			while(true){
				try {
					Thread.sleep(2000);
					bbullet.add(new BossBullet(new ImageIcon("./com/bullet/bu02_01.png"),boss.x+102,boss.y+160));
				} catch (InterruptedException e) {
					// TODO 自动生成 catch 块
					e.printStackTrace();
				}
			}
		}
	}
	//我的子弹移动线程
	class MoveBullet extends Thread{		
		@Override
		public void run() {
			while(true){
				try {
					Thread.sleep(10);
					//遍历每一个子弹
					for(int i=0;i<blist.size();i++){
						((Bullet)blist.get(i)).by-=3;
					}
				} catch (InterruptedException e) {
					// TODO 自动生成 catch 块
					e.printStackTrace();
				}
			}
		}
	}
	//boss子弹移动线程
	class MoveBossBullet extends Thread{		
		@Override
		public void run() {
			while(true){
				try {
					Thread.sleep(20);
					//遍历每一个子弹
					for(int i=0;i<bbullet.size();i++){
						((BossBullet)bbullet.get(i)).y+=3;
					}
				} catch (InterruptedException e) {
					// TODO 自动生成 catch 块
					e.printStackTrace();
				}
			}
		}
	}
	//向集合添加补给道具线程
	class AddItem extends Thread{
		@Override
		public void run() {
			while(true){
				try {
					Thread.sleep(time);
					iitem.add(new Item(new ImageIcon("./com/item/img_item_life.png"),0,30));
				} catch (InterruptedException e) {
					// TODO 自动生成 catch 块
					e.printStackTrace();
				}
			}
		}
	}
	//补给道具移动线程
	class MoveItem extends Thread{
		@Override
		public void run() {
			// TODO 自动生成方法存根
			super.run();
			while(true){
				try {
					Thread.sleep(20);
					//遍历每一个补给道具
					for(int i=0;i<iitem.size();i++){
						((Item)iitem.get(i)).y+=3;
					}
				} catch (InterruptedException e) {
					// TODO 自动生成 catch 块
					e.printStackTrace();
				}
			}
		}
	}
	//Boss移动线程
	class MoveBoss extends Thread{
		@Override
		public void run() {
			for(;;){
				try {
					Thread.sleep(30);
					if(boss.x>0&&loop==true){
						boss.x+=2;
						if(boss.x>=250){
							loop=false;
						}
					}
					if(boss.x<=250&&loop==false){
						boss.x-=2;
						if(boss.x<=10){
							loop=true;
						}
					}
					boss.y+=1;
					if(boss.y>50){
						boss.y=50;
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
	//非特殊键（shift之类）
	public void keyTyped(KeyEvent e) {
		// TODO 自动生成方法存根
		
	}
	//按下触发
	public void keyPressed(KeyEvent e) {
		//键盘控制和边界处理
		if(e.getKeyCode()==KeyEvent.VK_UP&&myplane.my>=50&&flag){
			myplane.my-=speed;
		}
		if(e.getKeyCode()==KeyEvent.VK_DOWN&&myplane.my<=580&&flag){
			myplane.my+=speed;
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT&&myplane.mx>=8&&flag){
			myplane.mx-=speed;
		}
		if(e.getKeyCode()==KeyEvent.VK_RIGHT&&myplane.mx<=385&&flag){
			myplane.mx+=speed;
		}
		//使用空格键暂定和开始游戏
		if(e.getKeyCode()==KeyEvent.VK_SPACE){
			if(flag){
				//暂停游戏(暂停线程)
				mmap.suspend();
				abullet.suspend();
				mbullet.suspend();
				epmove.suspend();
				aeplane.suspend();
				touch.suspend();
				re.suspend();
				aitem.suspend();
				mitem.suspend();
				abbullet.suspend();
				mbbullet.suspend();
				mboss.suspend();
				flag=false;
			}else{
				//开始游戏（开始线程）
				mmap.resume();
				abullet.resume();
				mbullet.resume();
				epmove.resume();
				aeplane.resume();
				touch.resume();
				re.resume();
				aitem.resume();
				mitem.resume();
				abbullet.resume();
				mbbullet.resume();
				mboss.resume();
				flag=true;
			}
		}
		
	}
	//松开触发
	public void keyReleased(KeyEvent e) {
		// TODO 自动生成方法存根
		
	}
	public static void main(String[] args) {
		//MainFrame mframe=new MainFrame();
		WelCome welframe=new WelCome();
		welframe.setVisible(true);
	}
	
	
}
