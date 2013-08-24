package com.test5;

import java.util.*;
import java.io.*;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

//播放声音的类(该类有待研究)
class AePlayWave implements Runnable
{
	private String filename;
	public AePlayWave(String wavfile)
	{
		filename=wavfile;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		File soundfile=new File(filename);
		
		AudioInputStream audioInputStream = null;
		try {
			audioInputStream=AudioSystem.getAudioInputStream(soundfile);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			return;
		}
		
		AudioFormat format=audioInputStream.getFormat();
		SourceDataLine auline=null;
		DataLine.Info info=new DataLine.Info(SourceDataLine.class, format);
		
		try {
			auline=(SourceDataLine)AudioSystem.getLine(info);
			auline.open(format);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			return;
		}
		
		auline.start();
		int nBytesRead=0;
		byte [] abData=new byte[1024];
		
		try {
			while(nBytesRead!=-1)
			{
				nBytesRead=audioInputStream.read(abData,0,abData.length);
				if(nBytesRead>0)
				{
					auline.write(abData, 0, nBytesRead);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}finally
		{
			auline.drain();
			auline.close();
		}
	}
	
	
}

class Node
{
	int x;
	int y;
	int direct;
	
	public Node(int x,int y,int direct)
	{
		this.x=x;
		this.y=y;
		this.direct=direct;
	}
}

//记录类，同时可以保存玩家的设置
class Recorder
{
	//记录每关有多少敌人数量
	private static int enNum=20;
	//设置我有多少可以用的人
	private static int myLife=3;
	//记录总共消灭了多少敌人
	private static int allEnNum=0;
	//从文件中恢复记录点
	static Vector<Node> nodes=new Vector<Node>();
	
	private static FileWriter fw=null;//静态方法中的变量必须是静态的
	private static BufferedWriter bw=null;
	private static FileReader fr=null;
	private static BufferedReader br=null;
	
	private static Vector<EnemyTank> ets1=new Vector<EnemyTank>();
//	private  Vector<EnemyTank> ets=new Vector<EnemyTank>();
	
	//完成读取任务
	public Vector<Node> getNodesAndEnNums()
	{
		try {
			fr=new FileReader("d:\\myRecording.txt");
			br=new BufferedReader(fr);
			String n="";
			//先读取第一行
			n=br.readLine();
			allEnNum=Integer.parseInt(n);
			while((n=br.readLine())!=null)
			{
				String []xyz=n.split(" ");
			
				Node node=new Node(Integer.parseInt(xyz[0]),Integer.parseInt(xyz[1]),Integer.parseInt(xyz[2]));
				nodes.add(node);
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}finally{
			try {
				//后打开则先关闭
				br.close();
				fr.close();
			} catch (Exception e2) {
				e2.printStackTrace();
				// TODO: handle exception
			}
		}
		
		return nodes;
	}
	
	//保存敌人的数量和敌人坦克坐标，方向
	public static void keepRecAndEnemyTank()
	{
		try {
			//创建文件流
			fw=new FileWriter("d:\\myRecording.txt");
			bw=new BufferedWriter(fw);
			
			bw.write(allEnNum+"\r\n");
			
			//保存当前活的敌人坦克的坐标和方向
			for(int i=0;i<ets1.size();i++)
			{
				//取出第一个坦克
				EnemyTank et=ets1.get(i);
				
				if(et.islive)
				{
					//活的保存
					String recode=et.x+" "+et.y+" "+et.direct;
					
					//写入到文件中
					bw.write(recode+"\r\n");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}finally{
			
			//关闭流
			try {
				//后开的先关闭
				bw.close();
				fw.close();
			} catch (Exception e2) {
				e2.printStackTrace();
				// TODO: handle exception
			}
		}
	}
	
	
	//从文件中读取记录
	public static void getRecording()
	{
		try {
			fr=new FileReader("d:\\myRecording.txt");
			br=new BufferedReader(fr);
			String n=br.readLine();
			allEnNum=Integer.parseInt(n);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}finally{
			try {
				//后打开则先关闭
				br.close();
				fr.close();
			} catch (Exception e2) {
				e2.printStackTrace();
				// TODO: handle exception
			}
		}
	}
	
	//把玩家击毁敌人坦克数量保存到文件中
	public static void keepRecording()
	{
		try {
			//创建文件流
			fw=new FileWriter("d:\\myRecording.txt");
			bw=new BufferedWriter(fw);
			
			bw.write(allEnNum+"\r\n");
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}finally{
			
			//关闭流
			try {
				//后开的先关闭
				bw.close();
				fw.close();
			} catch (Exception e2) {
				e2.printStackTrace();
				// TODO: handle exception
			}
		}
	}
	
	public static int getEnNum() {
		return enNum;
	}
	public static void setEnNum(int enNum) {
		Recorder.enNum = enNum;
	}
	public static int getMyLife() {
		return myLife;
	}
	public static void setMyLife(int myLife) {
		Recorder.myLife = myLife;
	}
	
	//减少敌人数
	public static void reduceEnNum()
	{
		enNum--;
	}
	//减少自己的数量
	public static void reduceMyLife()
	{
		myLife--;
	}
	
	//消灭敌人
	public static void addEnNum()
	{
		allEnNum++;
	}
	public static int getAllEnNum() {
		return allEnNum;
	}
	public static void setAllEnNum(int allEnNum) {
		Recorder.allEnNum = allEnNum;
	}


	public  Vector<EnemyTank> getEts() {
		return ets1;
	}


	public  void setEts(Vector<EnemyTank> ets) {
		//静态的方法不能用this去访问（this是针对某一个具体的对象的）
		System.out.println("ok");
		ets1 = ets;
	}
}

//总结：若一个对象创建后坐标是移动的，就应该考虑是否做成一个线程
//炸弹类
class Bomb
{
	//定义炸弹的坐标
	int x,y;
	//炸弹的生命
	int life=9;
	boolean islive=true;
	//是否允许爆炸
	boolean allow_bomb=true;
	public Bomb(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
	
	//减少生命值
	public void lifeDown()
	{
		if(life>0)
		{
			life--;
		}
		else
		{
			this.islive=false;
		}
	}

	public boolean isAllow_bomb() {
		return allow_bomb;
	}

	public void setAllow_bomb(boolean allow_bomb) {
		this.allow_bomb = allow_bomb;
	}
}
//子弹类
class Shot implements Runnable
{
	int x;
	int y;
	int direct;
	int speed=2;
	//是否还活着
	boolean islive=true;
	//子弹是否允许移动
	boolean allow_move=true;
	public Shot(int x,int y,int direct)
	{
		this.x=x;
		this.y=y;
		this.direct=direct;
	}
	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		while(true)
		{
			try {
				Thread.sleep(50);
			} catch (Exception e) {
				// TODO: handle exception
			}
			if(this.isAllow_move())
			{
				switch(direct)
				{
				case 0:
					//上
					y-=speed;
					break;
				case 1:
					//右
					x+=speed;
					break;
				case 2:
					//下
					y+=speed;
					break;
				case 3:
					//左
					x-=speed;
					break;
				}
			}
			//System.out.println("子弹的坐标 x="+x+" y="+y);
			//子弹何时死亡？？？
			
			//判断fai子弹是否碰到边缘
			if(x<0||x>400||y<0||y>300)
			{
				islive=false;
				break;
			}
		}
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public boolean isAllow_move() {
		return allow_move;
	}
	public void setAllow_move(boolean allow_move) {
		this.allow_move = allow_move;
	}
}
//定义坦克类
class Tank
{
	//表示坦克的横坐标
	int x=0;
	//坦克的纵坐标
	int y=0;
	
	//坦克方向
	//0表示上  1表示右 2表示下 3表示左
	int direct=0;
	//是否允许坦克进行射击
	boolean allow_shot=true;
	//定义颜色
    int color;
	//坦克的速度
    Vector<Tank> ts=new Vector<Tank>();
    
    boolean islive=true;
	int speed=2;
	//是否允许坦克移动
	boolean allow_move=true;
	public Tank(int x,int y)
	{
		this.x=x;
		this.y=y;
	}

	//写一个专门的函数用来判断坦克间是否发生碰撞
	//判断是否碰到了别的敌人的坦克
		public boolean isTouchOtherTank()
		{
			boolean b=false;
			
			switch(this.direct)
			{
			case 0:
				//我的坦克向上
				//取出所有的敌人坦克
				for(int i=0;i<ts.size();i++)
				{
					//取出第一个坦克
					Tank et=ts.get(i);
					//如果不是自己
					if(et!=this)
					{
						//如果敌人的方向是向上或者向下
						if(et.direct==0||et.direct==2)
						{
							if(this.x>=et.x-5&&this.x<=et.x+25&&this.y>=et.y-5&&this.y<=et.y+35)
							{
								return true;
							}
							if(this.x+20>=et.x-5&&this.x+20<=et.x+25&&this.y>=et.y-5&&this.y<=et.y+35)
							{
								return true;
							}
						}
						if(et.direct==3||et.direct==1)
						{
							if(this.x>=et.x-5&&this.x<=et.x+35&&this.y>=et.y-5&&this.y<=et.y+25)
							{
								return true;
							}
							if(this.x+20>=et.x-5&&this.x+20<=et.x+35&&this.y>=et.y-5&&this.y<=et.y+25)
							{
								return true;
							}
						}
					}
				}
				break;
			case 1:
				//坦克向右
				//取出所有的敌人坦克
				for(int i=0;i<ts.size();i++)
				{
					//取出第一个坦克
					Tank et=ts.get(i);
					//如果不是自己
					if(et!=this)
					{
						//如果敌人的方向是向上或者向下
						if(et.direct==0||et.direct==2)
						{
							if(this.x+30>=et.x-5&&this.x+30<=et.x+25&&this.y>=et.y-5&&this.y<=et.y+35)
							{
								return true;
							}
							if(this.x+30>=et.x-5&&this.x+30<=et.x+25&&this.y+20>=et.y-5&&this.y+20<=et.y+35)
							{
								return true;
							}
						}
						if(et.direct==3||et.direct==1)
						{
							if(this.x+30>=et.x-5&&this.x+30<=et.x+35&&this.y>=et.y-5&&this.y<=et.y+25)
							{
								return true;
							}
							if(this.x+30>=et.x-5&&this.x+30<=et.x+35&&this.y+20>=et.y-5&&this.y+20<=et.y+25)
							{
								return true;
							}
						}
					}
				}
				break;
			case 2:
				//坦克向下
				//取出所有的敌人坦克
				for(int i=0;i<ts.size();i++)
				{
					//取出第一个坦克
					Tank et=ts.get(i);
					//如果不是自己
					if(et!=this)
					{
						//如果敌人的方向是向上或者向下
						if(et.direct==0||et.direct==2)
						{
							if(this.x>=et.x-5&&this.x<=et.x+25&&this.y+30>=et.y-5&&this.y+30<=et.y+35)
							{
								return true;
							}
							if(this.x+20>=et.x-5&&this.x+20<=et.x+25&&this.y+30>=et.y-5&&this.y+30<=et.y+35)
							{
								return true;
							}
						}
						if(et.direct==3||et.direct==1)
						{
							if(this.x>=et.x-5&&this.x<=et.x+35&&this.y+30>=et.y-5&&this.y+30<=et.y+25)
							{
								return true;
							}
							if(this.x+20>=et.x-5&&this.x+20<=et.x+35&&this.y+30>=et.y-5&&this.y+30<=et.y+25)
							{
								return true;
							}
						}
					}
				}
				break;
			case 3:
				//向左
				//取出所有的敌人坦克
				for(int i=0;i<ts.size();i++)
				{
					//取出第一个坦克
					Tank et=ts.get(i);
					//如果不是自己
					if(et!=this)
					{
						//如果敌人的方向是向上或者向下
						if(et.direct==0||et.direct==2)
						{
							if(this.x>=et.x-5&&this.x<=et.x+25&&this.y>=et.y-5&&this.y<=et.y+35)
							{
								return true;
							}
							if(this.x>=et.x-5&&this.x<=et.x+25&&this.y+20>=et.y-5&&this.y+20<=et.y+35)
							{
								return true;
							}
						}
						if(et.direct==3||et.direct==1)
						{
							if(this.x>=et.x-5&&this.x<=et.x+35&&this.y>=et.y-5&&this.y<=et.y+25)
							{
								return true;
							}
							if(this.x>=et.x-5&&this.x<=et.x+35&&this.y+20>=et.y-5&&this.y+20<=et.y+25)
							{
								return true;
							}
						}
					}
				}
				break;
			}
			
			return b;
		}
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getDirect() {
		return direct;
	}

	public void setDirect(int direct) {
		this.direct = direct;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public boolean isAllow_shot() {
		return allow_shot;
	}

	public void setAllow_shot(boolean allow_shot) {
		this.allow_shot = allow_shot;
	}

	public boolean isAllow_move() {
		return allow_move;
	}

	public void setAllow_move(boolean allow_move) {
		this.allow_move = allow_move;
	}

	public Vector<Tank> getTs() {
		return ts;
	}

	public void setTs(Vector<Tank> ts) {
		this.ts = ts;
	}

	public boolean isIslive() {
		return islive;
	}

	public void setIslive(boolean islive) {
		this.islive = islive;
	}
	
}

//敌人的坦克,把敌人坦克做成线程类
class EnemyTank extends Tank implements Runnable
{
	
	int times=0;
	//定义我的坦克用于判断我的坦克和敌人坦克是否相撞
	Hero h=null;
	//定义一个向量，可以访问到MyPanel上所有敌人的坦克
	Vector<EnemyTank> ets=new Vector<EnemyTank>();
	//定义一个向量，可以存放敌人的子弹
	Vector<Shot> ss=new Vector<Shot>();
	//敌人添加子弹，应当在放在创建坦克和敌人的坦克子弹死亡后
	public EnemyTank(int x,int y)
	{
		super(x,y);
	}
	
	//得到MyPanel的敌人坦克向量
	public void setEts(Vector<EnemyTank> vv)
	{
		this.ets=vv;
	}
	
@Override
	public void run()
	{
//		AePlayWave apw=new AePlayWave("敌人移动.wav");
//		Thread t1=new Thread(apw);
//		
				// TODO Auto-generated method stub
				while(true)
				{
					
						switch(this.direct)
						{
						case 0:
							//说明坦克正在向上移动
							for(int i=0;i<30;i++)
							{
								if(y>2&&!this.isTouchOtherTank()&&this.isAllow_move())
								{
									y-=speed;
									//t1.start();
								}
								
								try {
									Thread.sleep(50);
								} catch (Exception e) {
									e.printStackTrace();
									// TODO: handle exception
								}
							}
							
							break;
						case 1:
							//向右
							for(int i=0;i<30;i++)
							{
								//保证坦克正在向右
								if(x<360&&!this.isTouchOtherTank()&&this.isAllow_move())
								{
									x+=speed;
									//t1.start();
								}
								try {
									Thread.sleep(50);
								} catch (Exception e) {
									e.printStackTrace();
									// TODO: handle exception
								}
							}
							break;
						case 2:
							//向下
							for(int i=0;i<30;i++)
							{
								if(y<260&&!this.isTouchOtherTank()&&this.isAllow_move())
								{
									y+=speed;
									//t1.start();
								}
								
								try {
									Thread.sleep(50);
								} catch (Exception e) {
									e.printStackTrace();
									// TODO: handle exception
								}
							}
							
							break;
						case 3:
							//向左
							for(int i=0;i<30;i++)
							{
								if(x>2&&!this.isTouchOtherTank()&&this.isAllow_move())
								{
									x-=speed;
									//t1.start();
								}
								
								try {
									Thread.sleep(50);
								} catch (Exception e) {
									e.printStackTrace();
									// TODO: handle exception
								}
							}
							break;
						}
					this.times++;
					if(times%2==0)
					{
							if(islive&&this.allow_shot)
							{
								if(ss.size()<5)
								{
									Shot s=null;
									//没有子弹
									//添加
									switch(direct)
									{
									case 0:
										//创建一颗子弹
										s=new Shot(x+9,y,0);
										//把子弹加入向量
										ss.add(s);
										break;
									case 1:
										s=new Shot(x+30, y+9,1);
										ss.add(s);
										break;
									case 2:
										s=new Shot(x+9,y+30,2);
										ss.add(s);
										break;
									case 3:
										s=new Shot(x,y+9,3);
										ss.add(s);
										break;
									}
									
									
									//启动子弹
									Thread t=new Thread(s);
									t.start();
								
							}
						}
					}
					//让坦克随机产生一个新的方向
					if(this.isAllow_move())
					{
						this.direct=(int)(Math.random()*4);
					}
					//判断敌人坦克是否死亡
					if(this.islive==false)
					{
						//让坦克死亡后退出线程
						break;
					}
					
					
				}
			}

	public Hero getH() {
		return h;
	}

	public void setH(Hero h) {
		this.h = h;
	}
	
}


//我的坦克
class Hero extends Tank
{
	//子弹
	//Shot s=null;
	Vector<Shot> ss=new Vector<Shot>();
	Shot s=null;
	public Hero(int x,int y)
	{
		//调用父类构造函数
		super(x,y);
		
	}
	
	//开火
	public void shotEnemy()
	{
		
		switch(this.direct)
		{
		case 0:
			//创建一颗子弹
			s=new Shot(x+9,y,0);
			//把子弹加入向量
			ss.add(s);
			break;
		case 1:
			s=new Shot(x+30, y+9,1);
			ss.add(s);
			break;
		case 2:
			s=new Shot(x+9,y+30,2);
			ss.add(s);
			break;
		case 3:
			s=new Shot(x,y+9,3);
			ss.add(s);
			break;
		}
		
		//启动子弹线程
		Thread t=new Thread(s);
		t.start();
	}
	//坦克向上移动
	public void moveUP()
	{
		y-=speed;
	}
	//坦克向右移动
	public void moveRight()
	{
		x+=speed;
	}
	//向下移动
	public void moveDown()
	{
		y+=speed;
	}
	//向左移动
	public void moveLeft()
	{
		x-=speed;
	}
	
}