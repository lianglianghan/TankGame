package com.test1;

import java.util.*;

//�ܽ᣺��һ�����󴴽����������ƶ��ģ���Ӧ�ÿ����Ƿ�����һ���߳�
//ը����
class Bomb
{
	//����ը��������
	int x,y;
	//ը��������
	int life=9;
	boolean islive=true;
	//�Ƿ�����ը
	boolean allow_bomb=true;
	public Bomb(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
	
	//��������ֵ
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
//�ӵ���
class Shot implements Runnable
{
	int x;
	int y;
	int direct;
	int speed=3;
	//�Ƿ񻹻���
	boolean islive=true;
	//�ӵ��Ƿ������ƶ�
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
					//��
					y-=speed;
					break;
				case 1:
					//��
					x+=speed;
					break;
				case 2:
					//��
					y+=speed;
					break;
				case 3:
					//��
					x-=speed;
					break;
				}
			}
			//System.out.println("�ӵ������� x="+x+" y="+y);
			//�ӵ���ʱ����������
			
			//�ж�fai�ӵ��Ƿ�������Ե
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
//����̹����
class Tank
{
	//��ʾ̹�˵ĺ�����
	int x=0;
	//̹�˵�������
	int y=0;
	
	//̹�˷���
	//0��ʾ��  1��ʾ�� 2��ʾ�� 3��ʾ��
	int direct=0;
	//�Ƿ�����̹�˽������
	boolean allow_shot=true;
	//������ɫ
    int color;
	//̹�˵��ٶ�
    
    boolean islive=true;
	int speed=1;
	//�Ƿ�����̹���ƶ�
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

//���˵�̹��,�ѵ���̹�������߳���
class EnemyTank extends Tank implements Runnable
{
	
	int times=0;
	
	//����һ�����������Է��ʵ�MyPanel�����е��˵�̹��
	Vector<EnemyTank> ets=new Vector<EnemyTank>();
	//����һ�����������Դ�ŵ��˵��ӵ�
	Vector<Shot> ss=new Vector<Shot>();
	//��������ӵ���Ӧ���ڷ��ڴ���̹�˺͵��˵�̹���ӵ�������
	public EnemyTank(int x,int y)
	{
		super(x,y);
	}
	
	//�õ�MyPanel�ĵ���̹������
	public void setEts(Vector<EnemyTank> vv)
	{
		this.ets=vv;
	}
	
	//�ж��Ƿ������˱�ĵ��˵�̹��
	public boolean isTouchOtherEnemy()
	{
		boolean b=false;
		
		switch(this.direct)
		{
		case 0:
			//�ҵ�̹������
			//ȡ�����еĵ���̹��
			for(int i=0;i<ets.size();i++)
			{
				//ȡ����һ��̹��
				EnemyTank et=ets.get(i);
				//��������Լ�
				if(et!=this)
				{
					//������˵ķ��������ϻ�������
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
			//̹������
			//ȡ�����еĵ���̹��
			for(int i=0;i<ets.size();i++)
			{
				//ȡ����һ��̹��
				EnemyTank et=ets.get(i);
				//��������Լ�
				if(et!=this)
				{
					//������˵ķ��������ϻ�������
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
			//̹������
			//ȡ�����еĵ���̹��
			for(int i=0;i<ets.size();i++)
			{
				//ȡ����һ��̹��
				EnemyTank et=ets.get(i);
				//��������Լ�
				if(et!=this)
				{
					//������˵ķ��������ϻ�������
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
			//����
			//ȡ�����еĵ���̹��
			for(int i=0;i<ets.size();i++)
			{
				//ȡ����һ��̹��
				EnemyTank et=ets.get(i);
				//��������Լ�
				if(et!=this)
				{
					//������˵ķ��������ϻ�������
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
							//˵��̹�����������ƶ�
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
							//����
							for(int i=0;i<30;i++)
							{
								//��֤̹����������
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
							//����
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
							//����
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
									//û���ӵ�
									//���
									switch(direct)
									{
									case 0:
										//����һ���ӵ�
										s=new Shot(x+9,y,0);
										//���ӵ���������
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
									
									
									//�����ӵ�
									Thread t=new Thread(s);
									t.start();
								
							}
						}
					}
					//��̹���������һ���µķ���
					if(this.isAllow_move())
					{
						this.direct=(int)(Math.random()*4);
					}
					//�жϵ���̹���Ƿ�����
					if(this.islive==false)
					{
						//��̹���������˳��߳�
						break;
					}
					
					
				}
			}
	
}


//�ҵ�̹��
class Hero extends Tank
{
	//�ӵ�
	//Shot s=null;
	Vector<Shot> ss=new Vector<Shot>();
	Shot s=null;
	public Hero(int x,int y)
	{
		//���ø��๹�캯��
		super(x,y);
		
	}
	
	//����
	public void shotEnemy()
	{
		
		switch(this.direct)
		{
		case 0:
			//����һ���ӵ�
			s=new Shot(x+9,y,0);
			//���ӵ���������
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
		
		//�����ӵ��߳�
		Thread t=new Thread(s);
		t.start();
	}
	//̹�������ƶ�
	public void moveUP()
	{
		y-=speed;
	}
	//̹�������ƶ�
	public void moveRight()
	{
		x+=speed;
	}
	//�����ƶ�
	public void moveDown()
	{
		y+=speed;
	}
	//�����ƶ�
	public void moveLeft()
	{
		x-=speed;
	}
	
}