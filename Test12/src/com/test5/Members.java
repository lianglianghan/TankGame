package com.test5;

import java.util.*;
import java.io.*;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

//������������(�����д��о�)
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

//��¼�࣬ͬʱ���Ա�����ҵ�����
class Recorder
{
	//��¼ÿ���ж��ٵ�������
	private static int enNum=20;
	//�������ж��ٿ����õ���
	private static int myLife=3;
	//��¼�ܹ������˶��ٵ���
	private static int allEnNum=0;
	//���ļ��лָ���¼��
	static Vector<Node> nodes=new Vector<Node>();
	
	private static FileWriter fw=null;//��̬�����еı��������Ǿ�̬��
	private static BufferedWriter bw=null;
	private static FileReader fr=null;
	private static BufferedReader br=null;
	
	private static Vector<EnemyTank> ets1=new Vector<EnemyTank>();
//	private  Vector<EnemyTank> ets=new Vector<EnemyTank>();
	
	//��ɶ�ȡ����
	public Vector<Node> getNodesAndEnNums()
	{
		try {
			fr=new FileReader("d:\\myRecording.txt");
			br=new BufferedReader(fr);
			String n="";
			//�ȶ�ȡ��һ��
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
				//������ȹر�
				br.close();
				fr.close();
			} catch (Exception e2) {
				e2.printStackTrace();
				// TODO: handle exception
			}
		}
		
		return nodes;
	}
	
	//������˵������͵���̹�����꣬����
	public static void keepRecAndEnemyTank()
	{
		try {
			//�����ļ���
			fw=new FileWriter("d:\\myRecording.txt");
			bw=new BufferedWriter(fw);
			
			bw.write(allEnNum+"\r\n");
			
			//���浱ǰ��ĵ���̹�˵�����ͷ���
			for(int i=0;i<ets1.size();i++)
			{
				//ȡ����һ��̹��
				EnemyTank et=ets1.get(i);
				
				if(et.islive)
				{
					//��ı���
					String recode=et.x+" "+et.y+" "+et.direct;
					
					//д�뵽�ļ���
					bw.write(recode+"\r\n");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}finally{
			
			//�ر���
			try {
				//�󿪵��ȹر�
				bw.close();
				fw.close();
			} catch (Exception e2) {
				e2.printStackTrace();
				// TODO: handle exception
			}
		}
	}
	
	
	//���ļ��ж�ȡ��¼
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
				//������ȹر�
				br.close();
				fr.close();
			} catch (Exception e2) {
				e2.printStackTrace();
				// TODO: handle exception
			}
		}
	}
	
	//����һ��ٵ���̹���������浽�ļ���
	public static void keepRecording()
	{
		try {
			//�����ļ���
			fw=new FileWriter("d:\\myRecording.txt");
			bw=new BufferedWriter(fw);
			
			bw.write(allEnNum+"\r\n");
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}finally{
			
			//�ر���
			try {
				//�󿪵��ȹر�
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
	
	//���ٵ�����
	public static void reduceEnNum()
	{
		enNum--;
	}
	//�����Լ�������
	public static void reduceMyLife()
	{
		myLife--;
	}
	
	//�������
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
		//��̬�ķ���������thisȥ���ʣ�this�����ĳһ������Ķ���ģ�
		System.out.println("ok");
		ets1 = ets;
	}
}

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
	int speed=2;
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
    Vector<Tank> ts=new Vector<Tank>();
    
    boolean islive=true;
	int speed=2;
	//�Ƿ�����̹���ƶ�
	boolean allow_move=true;
	public Tank(int x,int y)
	{
		this.x=x;
		this.y=y;
	}

	//дһ��ר�ŵĺ��������ж�̹�˼��Ƿ�����ײ
	//�ж��Ƿ������˱�ĵ��˵�̹��
		public boolean isTouchOtherTank()
		{
			boolean b=false;
			
			switch(this.direct)
			{
			case 0:
				//�ҵ�̹������
				//ȡ�����еĵ���̹��
				for(int i=0;i<ts.size();i++)
				{
					//ȡ����һ��̹��
					Tank et=ts.get(i);
					//��������Լ�
					if(et!=this)
					{
						//������˵ķ��������ϻ�������
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
				//̹������
				//ȡ�����еĵ���̹��
				for(int i=0;i<ts.size();i++)
				{
					//ȡ����һ��̹��
					Tank et=ts.get(i);
					//��������Լ�
					if(et!=this)
					{
						//������˵ķ��������ϻ�������
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
				//̹������
				//ȡ�����еĵ���̹��
				for(int i=0;i<ts.size();i++)
				{
					//ȡ����һ��̹��
					Tank et=ts.get(i);
					//��������Լ�
					if(et!=this)
					{
						//������˵ķ��������ϻ�������
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
				//����
				//ȡ�����еĵ���̹��
				for(int i=0;i<ts.size();i++)
				{
					//ȡ����һ��̹��
					Tank et=ts.get(i);
					//��������Լ�
					if(et!=this)
					{
						//������˵ķ��������ϻ�������
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

//���˵�̹��,�ѵ���̹�������߳���
class EnemyTank extends Tank implements Runnable
{
	
	int times=0;
	//�����ҵ�̹�������ж��ҵ�̹�˺͵���̹���Ƿ���ײ
	Hero h=null;
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
	
@Override
	public void run()
	{
//		AePlayWave apw=new AePlayWave("�����ƶ�.wav");
//		Thread t1=new Thread(apw);
//		
				// TODO Auto-generated method stub
				while(true)
				{
					
						switch(this.direct)
						{
						case 0:
							//˵��̹�����������ƶ�
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
							//����
							for(int i=0;i<30;i++)
							{
								//��֤̹����������
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
							//����
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
							//����
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

	public Hero getH() {
		return h;
	}

	public void setH(Hero h) {
		this.h = h;
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