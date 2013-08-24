package com.test1;

import java.util.*;

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
	int speed=3;
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
    
    boolean islive=true;
	int speed=1;
	//是否允许坦克移动
	boolean allow_move=true;
	public Tank(int x,int y)
	{
		this.x=x;
		this.y=y;
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
	
}

//敌人的坦克,把敌人坦克做成线程类
class EnemyTank extends Tank implements Runnable
{
	
	int times=0;
	
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
	
	//判断是否碰到了别的敌人的坦克
	public boolean isTouchOtherEnemy()
	{
		boolean b=false;
		
		switch(this.direct)
		{
		case 0:
			//我的坦克向上
			//取出所有的敌人坦克
			for(int i=0;i<ets.size();i++)
			{
				//取出第一个坦克
				EnemyTank et=ets.get(i);
				//如果不是自己
				if(et!=this)
				{
					//如果敌人的方向是向上或者向下
					if(et.direct==0||et.direct==2)
					{
						if(this.x>=et.x-3&&this.x<=et.x+23&&this.y>=et.y-3&&this.y<=et.y+33)
						{
							return true;
						}
						if(this.x+20>=et.x-3&&this.x+20<=et.x+23&&this.y>=et.y-3&&this.y<=et.y+33)
						{
							return true;
						}
					}
					if(et.direct==3||et.direct==1)
					{
						if(this.x>=et.x-3&&this.x<=et.x+33&&this.y>=et.y-3&&this.y<=et.y+23)
						{
							return true;
						}
						if(this.x+20>=et.x-3&&this.x+20<=et.x+33&&this.y>=et.y-3&&this.y<=et.y+23)
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
			for(int i=0;i<ets.size();i++)
			{
				//取出第一个坦克
				EnemyTank et=ets.get(i);
				//如果不是自己
				if(et!=this)
				{
					//如果敌人的方向是向上或者向下
					if(et.direct==0||et.direct==2)
					{
						if(this.x+30>=et.x-3&&this.x+30<=et.x+23&&this.y>=et.y-3&&this.y<=et.y+33)
						{
							return true;
						}
						if(this.x+30>=et.x-3&&this.x+30<=et.x+23&&this.y+20>=et.y-3&&this.y+20<=et.y+33)
						{
							return true;
						}
					}
					if(et.direct==3||et.direct==1)
					{
						if(this.x+30>=et.x-3&&this.x+30<=et.x+33&&this.y>=et.y-3&&this.y<=et.y+23)
						{
							return true;
						}
						if(this.x+30>=et.x-3&&this.x+30<=et.x+33&&this.y+20>=et.y-3&&this.y+20<=et.y+23)
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
			for(int i=0;i<ets.size();i++)
			{
				//取出第一个坦克
				EnemyTank et=ets.get(i);
				//如果不是自己
				if(et!=this)
				{
					//如果敌人的方向是向上或者向下
					if(et.direct==0||et.direct==2)
					{
						if(this.x>=et.x-3&&this.x<=et.x+23&&this.y+30>=et.y-3&&this.y+30<=et.y+33)
						{
							return true;
						}
						if(this.x+20>=et.x-3&&this.x+20<=et.x+23&&this.y+30>=et.y-3&&this.y+30<=et.y+33)
						{
							return true;
						}
					}
					if(et.direct==3||et.direct==1)
					{
						if(this.x>=et.x-3&&this.x<=et.x+33&&this.y+30>=et.y-3&&this.y+30<=et.y+23)
						{
							return true;
						}
						if(this.x+20>=et.x-3&&this.x+20<=et.x+23&&this.y+30>=et.y-3&&this.y+30<=et.y+23)
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
			for(int i=0;i<ets.size();i++)
			{
				//取出第一个坦克
				EnemyTank et=ets.get(i);
				//如果不是自己
				if(et!=this)
				{
					//如果敌人的方向是向上或者向下
					if(et.direct==0||et.direct==2)
					{
						if(this.x>=et.x-3&&this.x<=et.x+23&&this.y>=et.y-3&&this.y<=et.y+33)
						{
							return true;
						}
						if(this.x>=et.x-3&&this.x<=et.x+23&&this.y+20>=et.y-3&&this.y+20<=et.y+33)
						{
							return true;
						}
					}
					if(et.direct==3||et.direct==1)
					{
						if(this.x>=et.x-3&&this.x<=et.x+33&&this.y>=et.y-3&&this.y<=et.y+23)
						{
							return true;
						}
						if(this.x>=et.x-3&&this.x<=et.x+23&&this.y+20>=et.y-3&&this.y+20<=et.y+23)
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
	@Override
	public void run()
	{
				// TODO Auto-generated method stub
				while(true)
				{
					
						switch(this.direct)
						{
						case 0:
							//说明坦克正在向上移动
							for(int i=0;i<30;i++)
							{
								if(y>2&&!this.isTouchOtherEnemy()&&this.isAllow_move())
								{
									y-=speed;
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
								if(x<360&&!this.isTouchOtherEnemy()&&this.isAllow_move())
								{
									x+=speed;
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
								if(y<260&&!this.isTouchOtherEnemy()&&this.isAllow_move())
								{
									y+=speed;
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
								if(x>2&&!this.isTouchOtherEnemy()&&this.isAllow_move())
								{
									x-=speed;
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