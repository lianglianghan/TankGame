/**
 * ���ܣ�̹����Ϸ��5.0
 * 1.����̹��(��̹���������Ͻ���Ϊ�ο���)
 * 2.�ҵ�̹�˿������������ƶ�
 * 3.���Է��ӵ����ӵ���������(���ֻ������5��)
 * 4.���ҵ�̹�˻��е��˵�̹��ʱ�����˾���ʧ
 * 5.�����˵�̹�˻�����ʱ���ұ�ը��ʧ
 * 6.��ֹ����̹���ص��˶�
 * 	  6.1�������ж��Ƿ���ײ�ĺ���д���Լ���EnemyTnak������ȥ
 * 7.���Էֹ�
 *    7.1��һ����ʼ��Panel������һ���յ�
 *    7.2��˸Ч��
 * 8.����������Ϸ��ʱ����ͣ�ͼ�����ͨ��space����������ͣ�������
 *    8.1���û������ͣ��ʱ�򣬰��ӵ���̹�˵��ٶ���Ϊ0������̹�˵ķ���Ҫ�仯(��ʦ�ģ�
 *    8.2�Ҹ���������̹�˵ĸ��������Ŀ��أ�������أ��ƶ����أ�ը����ը���أ�
 * 9.���Լ�¼��ҵĳɼ�
 *    9.1���ļ���
 *    9.2��дһ����¼�࣬��ɶ���ҵļ�¼
 *    9.3����ɱ��湲�������˶���������̹��
 *    9.4�����˳���Ϸ�����Լ�¼��ʱ�ĵ���̹�����꣬�����Իָ�
 * 10.java��β��������ļ�
 *    10.1�������ļ��Ĳ���
 */
package com.test4;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
public class MyTankGame3 extends JFrame implements ActionListener,WindowListener{

	MyPanel mp=null;
	int num=0;
	//����һ����ʼ���
	MyStartPanel msp=null;
	
	//��������Ҫ�Ĳ˵�
	JMenuBar jmb=null;
	//��ʼ��Ϸ
	JMenu jm1=null;
	JMenuItem jmi1=null;
	//�˳�ϵͳ
	JMenuItem jmi2=null;
	//�����˳�
	JMenuItem jmi3=null;
	JMenuItem jmi4=null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyTankGame3 mtg=new MyTankGame3();
		
	}
	
	//���캯��
	public MyTankGame3()
	{
		//mp=new MyPanel();
		//����mp�߳�
		//Thread t=new Thread(mp);
		//t.start();
		//this.add(mp);
		//this.addKeyListener(mp);
		
		//�����˵����˵�ѡ��
		jmb=new JMenuBar();
		jm1=new JMenu("��Ϸ��G)");
		//�������Ƿ�
		jm1.setMnemonic('G');

		jmi1 =new JMenuItem("��ʼ����Ϸ(N)");
		jmi2=new JMenuItem("�˳���Ϸ(E)");
		jmi3=new JMenuItem("�����˳���Ϸ(C)");
		jmi4=new JMenuItem("�����Ͼ���Ϸ(S)");
		
		jmi4.addActionListener(this);
		jmi4.setActionCommand("continue");
		
		jmi3.addActionListener(this);
		jmi3.setActionCommand("saveExit");
		
		jmi2.addActionListener(this);
		jmi2.setActionCommand("exit");
		jmi2.setMnemonic('E');
		this.addWindowListener(this);
		
		//��jmi1��Ӧ
		jmi1.addActionListener(this);
		jmi1.setActionCommand("newgame");
		jm1.add(jmi1);
		jm1.add(jmi2);
		jm1.add(jmi3);
		jm1.add(jmi4);
		jmb.add(jm1);
		
		msp=new MyStartPanel();
		Thread t=new Thread(msp);
		t.start();
		
		this.setJMenuBar(jmb);
		this.add(msp);
		
		this.setSize(600,500);
		this.setLocation(350, 150);
		this.setTitle("̹�˴�ս�����");
		this.setIconImage(new ImageIcon("images/tankgame.jpg").getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//���û���ͬ�ĵ��������ͬ�Ĵ���
		
		if(e.getActionCommand().equals("newgame"))
		{
			if(mp==null);
			else
				this.remove(mp);
			//����ս�����
			mp=new MyPanel("newGame");
			//����mp�߳�
			Thread t=new Thread(mp);
			t.start();
			//��ɾ���ɵĿ�ʼ���
			this.remove(msp);
			this.add(mp);
			this.addKeyListener(mp);
			//��ʾ��ˢ��JFrame
			this.setVisible(true);
			
		}else if(e.getActionCommand().equals("exit"))
		{
			//�û�������˳�ϵͳ�Ĳ˵�
			//������ٵ�������
			Recorder.keepRecording();
			
			System.exit(0);
		}//�Դ����˳�������
		else if(e.getActionCommand().equals("saveExit"))
		{
			//��������
			//������ٵ��˵������͵��˵�����
			Recorder recorder=new Recorder();
			recorder.setEts(mp.ets);
			recorder.keepRecAndEnemyTank();
			
			//�˳�(0��ʾ�����˳���-1��ʾ�쳣�˳�)
			System.exit(0);
		}else if(e.getActionCommand().equals("continue"))
		{
			if(mp==null);
			else
				this.remove(mp);
			//�ָ�
			//����ս�����
			mp=new MyPanel("con");
			
			
			//����mp�߳�
			Thread t=new Thread(mp);
			t.start();
			//��ɾ���ɵĿ�ʼ���
			this.remove(msp);
			this.add(mp);
			this.addKeyListener(mp);
			//��ʾ��ˢ��JFrame
			this.setVisible(true);
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		//��������
		//������ٵ��˵������͵��˵�����
		if(this.msp==null)
		{
			System.out.println("���ڹر�");
			Recorder recorder=new Recorder();
			recorder.setEts(mp.ets);
			recorder.keepRecAndEnemyTank();
		}
		else;
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	

}

//����һ����ʾ����
class MyStartPanel extends JPanel implements Runnable
{
	int times=0;
	public void paint(Graphics g)
	{
		super.paint(g);
		g.fillRect(0, 0, 400, 300);
		//��ʾ��Ϣ
		
		if(times%2==0)
		{
			g.setColor(Color.yellow);
			//������Ϣ������
			Font myFont=new Font("������κ",Font.BOLD,30);
			g.setFont(myFont);
			g.drawString("stage: 1", 150, 150);
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			//����
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			if(times==1000)
			{
				times=0;
			}else
			{
			  times++;
			}
			//�ػ�
			this.repaint();
		}
	}
}

//�ҵ����
class MyPanel extends JPanel implements KeyListener,Runnable
{
	//���ڴ���pause
	int count=0;
	//����һ���ҵ�̹��
	Hero hero=null;
	
	
	
	//������˵�̹���飨���ü��ϣ��̱߳Ƚϰ�ȫ��
	Vector<EnemyTank> ets=new Vector<EnemyTank>();
	Vector<Node> nodes=new Vector<Node>();
	Vector<Tank> tanks=new Vector<Tank>();
	
	//����ը������
	Vector<Bomb> bombs=new  Vector<Bomb>();
	
	int enSize=5;
	
	//��������ͼƬ,����ͼƬ�������һ��ը��
	Image image1=null;
	Image image2=null;
	Image image3=null;
	//���캯��
	public MyPanel(String flag)
	{
		//�ָ���¼
		Recorder.getRecording();
		
		hero=new Hero(180, 100);
		
		if(flag.equals("newGame"))
		{
			//��ʼ�����˵�̹��
			for(int i=0;i<enSize;i++)
			{
				
				//����һ�����˵�̹�˶���
				EnemyTank et=new EnemyTank((i+1)*50, 0);
				et.setColor(0);
				et.setDirect(2);
				//��MyPanel�ĵ���̹��������������̹��
				Thread t=new Thread(et);
				t.start();
				//���������һ���ӵ�
				Shot s =new Shot(et.x+10, et.y+30, 2);
				//���������
				et.ss.add(s);
				Thread t2=new Thread(s);
				t2.start();
				//����
				ets.add(et);
				this.pass();
			}
		}else
		{
			this.nodes=new Recorder().getNodesAndEnNums();
			//�ָ����˵�̹��
			for(int i=0;i<nodes.size();i++)
			{
				Node node=nodes.get(i);
				//����һ�����˵�̹�˶���
				EnemyTank et=new EnemyTank(node.x,node.y);
				et.setColor(0);
				et.setDirect(node.direct);
				//��MyPanel�ĵ���̹��������������̹��
				
				//et.setH(this.hero);
				Thread t=new Thread(et);
				t.start();
				//���������һ���ӵ�
				Shot s =new Shot(et.x+10, et.y+30, 2);
				//���������
				et.ss.add(s);
				Thread t2=new Thread(s);
				t2.start();
				//����
				ets.add(et);
				this.pass();
			}
		}
		try {
			image1=ImageIO.read(new File("bomb_1.gif"));
			image2=ImageIO.read(new File("bomb_2.gif"));
			image3=ImageIO.read(new File("bomb_3.gif"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		//���ſ�ս����
		AePlayWave apw=new AePlayWave("111.wav");
		Thread t=new Thread(apw);
		t.start();
		
//		//��ʼ��ͼƬ
//		image1=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
//		image2=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
//		image3=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));
	}
	
	//дһ��������MyPanel�ϵ�̹����������Tank
	public void pass()
	{
		for(int i=0;i<this.ets.size();i++)
		{
			if(this.ets.get(i).islive)
			{
				tanks.add((Tank)this.ets.get(i));
			}
		}
		if(this.hero.islive)
		{
			tanks.add((Tank)this.hero);
		}
		for(int j=0;j<tanks.size();j++)
		{
			tanks.get(j).setTs(tanks);
		}
	}
	//������ʾ��Ϣ
	public void showInfo(Graphics g)
	{
		//������ʾ��Ϣ̹�ˣ���̹�˲�����ս����
		this.drawTank(80, 330, g, 0, 0);
		g.setColor(Color.black);
		g.drawString(Recorder.getEnNum()+"", 110, 350);
	    this.drawTank(140, 330, g, 0, 1);
		g.setColor(Color.black);
		g.drawString(Recorder.getMyLife()+"", 170, 350);
		
		//������ҵ��ܳɼ�
		g.setColor(Color.black);
		Font f=new Font("����",Font.BOLD,20);
		g.setFont(f);
		g.drawString("�����ܳɼ�",420,30);
		
		this.drawTank(420, 60, g, 0, 0);
		
		g.setColor(Color.black);
		g.drawString(Recorder.getAllEnNum()+"", 460, 80);
	}
	//��дpaint
	public void paint(Graphics g)
	{
		super.paint(g);
		g.fillRect(0, 0, 400, 300);
		
		//������ʾ��Ϣ
		this.showInfo(g);
		
		//�����Լ���̹��
		if(hero.islive)
		{
			this.drawTank(hero.getX(), hero.getY(), g, this.hero.getDirect(), 1);
		}
		//��ss��ȡ��ÿһ���ӵ���������
		//ע������Ҫ�����ӵ�����ɫ������̹�������ӵ�����ɫ���ɺ�ɫ
		//��ʱ������Ϊyellow����ʱ����Ҫ��̬�ı�
		g.setColor(Color.yellow);
		for(int i=0;i<this.hero.ss.size();i++)
		{
			Shot myshot=hero.ss.get(i);
			//�����ӵ�,����һ���ӵ�
			if(myshot!=null&&myshot.islive==true)
			{
				g.drawRect(myshot.x, myshot.y, 1, 1);
			}
			if(myshot.islive==false)
			{
				//��ss��ɾ�����ӵ�
				hero.ss.remove(myshot);
			}
		}
		
	
		//����ը��
		for(int i=0;i<bombs.size();i++)
		{
			System.out.println("bombs.size()="+bombs.size());
			Bomb b=bombs.get(i);
			if(b.life>6&&b.isAllow_bomb())
			{
				g.drawImage(image1, b.x, b.y, 30, 30,this);
				
			}else if(b.life>3&&b.isAllow_bomb())
			{
				g.drawImage(image2, b.x, b.y, 30, 30, this);
			}else if(b.life>0&&b.isAllow_bomb())
			{
				g.drawImage(image3, b.x, b.y, 30, 30,this);
			}
			//��b������ֵ��С
			if(b.allow_bomb)
			{
				b.lifeDown();
			}
			//���ը������ֵΪ0���ͰѸ�ը����bombs������ȥ��
			if(b.life==0)
			{
				bombs.remove(b);
			}
		}
		//�������˵�̹��
		for(int i=0;i<ets.size();i++)
		{
			EnemyTank et=ets.get(i);
			if(et.islive)
			{
			  this.drawTank(et.getX(), et.getY(), g, et.getDirect(), 0);
			  //�������˵��ӵ�
			  for(int j=0;j<et.ss.size();j++)
			  {
				  
				  //ȡ���ӵ�
				  Shot enemyShot=et.ss.get(j);
				  if(enemyShot.islive)
				  {
					  g.drawRect(enemyShot.x, enemyShot.y, 1, 1);
				  }else{
					  //������˵�̹�������ʹ���remove��
					  et.ss.remove(enemyShot);
				  }
				  
			  }
			}
		}
	}
	
	
	
	//дһ��ר�ŵĺ������жϵ��˵��ӵ����ҵ��ӵ��Ƿ���ײ����ײ�Ļ������߶���ʧ
	public void shot_touch()
	{
		for(int i=0;i<this.hero.ss.size();i++)
		{
			for(int j=0;j<this.ets.size();j++)
			{
				for(int k=0;k<this.ets.get(j).ss.size();k++)
				{
					if(this.hero.ss.get(i).x==this.ets.get(j).ss.get(k).x&&this.hero.ss.get(i).y==this.ets.get(j).ss.get(k).y)
					{
						System.out.println("������ײ");
						this.hero.ss.get(i).islive=false;
						this.ets.get(j).ss.get(k).islive=false;
					}
				}
			}
		}
	}
	
	//дһ��ר�ŵĺ�������������ͣ�ͼ���
	public void pause()
	{
		this.count++;
		if(this.count%2!=0)
		{
			this.hero.setAllow_move(false);
			for(int i=0;i<this.hero.ss.size();i++)
			{
				this.hero.ss.get(i).setAllow_move(false);
			}
			for(int i=0;i<this.ets.size();i++)
			{
				this.ets.get(i).setAllow_move(false);
				this.ets.get(i).setAllow_shot(false);
				for(int j=0;j<this.ets.get(i).ss.size();j++)
				{
					this.ets.get(i).ss.get(j).setAllow_move(false);
				}
			}
			for(int i=0;i<this.bombs.size();i++)
			{
				this.bombs.get(i).setAllow_bomb(false);
			}
		}else
		{
			this.hero.setAllow_move(true);
			for(int i=0;i<this.hero.ss.size();i++)
			{
				this.hero.ss.get(i).setAllow_move(true);
			}
			for(int i=0;i<this.ets.size();i++)
			{
				this.ets.get(i).setAllow_move(true);
				this.ets.get(i).setAllow_shot(true);
				for(int j=0;j<this.ets.get(i).ss.size();j++)
				{
					this.ets.get(i).ss.get(j).setAllow_move(true);
				}
			}
			for(int i=0;i<this.bombs.size();i++)
			{
				this.bombs.get(i).setAllow_bomb(true);
			}
			count=0;
		}
	}
	//�жϵ��˵��ӵ��Ƿ������
	public void hitMe()
	{
		//ȡ��ÿһ�����˵�̹��
		for(int i=0;i<this.ets.size();i++)
		{
			//ȡ��̹��
			EnemyTank et=ets.get(i);
			
			//ȡ��ÿһ���ӵ�
			for(int j=0;j<et.ss.size();j++)
			{
				//ȡ���ӵ�
				Shot enemyshot=et.ss.get(j);
				if(hero.islive)
				{
					if(this.hitTank(enemyshot, hero))
					{
						Recorder.reduceMyLife();
						//����ҵ�̹�����ˣ��ҵ��ӵ�Ҳ������ʧ
//						for(int k=0;k<this.hero.ss.size();k++)
//						{
//							this.hero.ss.get(k).islive=false;
//						}
					}
				}
				
			}
		}
	}
	//�ҵ��ӵ��Ƿ���е���
	public void hitEnemyTank()
	{
		//�ж��Ƿ���е��˵�̹��
		for(int i=0;i<hero.ss.size();i++)
		{
			
			//ȡ���ӵ�
			Shot myshot=hero.ss.get(i);
			//�ж��ӵ��Ƿ���Ч
			this.shot_touch();
			if(myshot.islive)
			{
				//ȡ��ÿ��̹�ˣ���֮�ж�
				for(int j=0;j<ets.size();j++)
				{
					//ȡ��̹��
					EnemyTank et =ets.get(j);
					
					if(et.islive)
					{
						if(this.hitTank(myshot, et))
						{
							Recorder.reduceEnNum();
							Recorder.addEnNum();
						}
					}
				}
			}
		}
	}
	//дһ������ר���ж��ӵ��Ƿ���̹��
	public boolean hitTank(Shot s,Tank et)
	{
		boolean b2=false;
		//�жϸ�̹�˵ķ���
		switch(et.direct)
		{
		//������˵ķ��������ϻ�������
		case 0:
		case 2:
			if(s.x>=et.x-1&&s.x<et.x+20&&s.y>=et.y&&s.y<=et.y+30)
			{
				//����
				
				//�ӵ�����
				s.islive=false;
				//����̹������
				et.islive=false;
				b2=true;
				//����һ��ը��������Vector��
				Bomb b=new Bomb(et.x, et.y);
				bombs.add(b);
			}
			break;
		case 1:
		case 3:
			if(s.x>=et.x-1&&s.x<=et.x+30&&s.y>=et.y&&s.y<=et.y+20)
			{
				//����
				//�ӵ�����
				s.islive=false;
				//����̹������
				et.islive=false;
				b2=true;
				Bomb b=new Bomb(et.x, et.y);
				bombs.add(b);
			}
			break;
		}
		return b2;
	}
	
	//����̹�˵ĺ���(��չ��
	public void drawTank(int x,int y,Graphics g,int direct,int type)
	{
		//�ж���ʲô���͵�̹��
		switch(type)
		{
		case 0:
			g.setColor(Color.cyan);
			break;
		case 1:
			g.setColor(Color.yellow);
			
		}
		
		//�жϷ���
		switch(direct)
		{
		//����
		case 0:
			
			//�����ҵ�̹�ˣ���ʱ�ٷ�װ��һ��������
			//1.������ߵľ���
			g.fill3DRect(x, y, 5, 30,false);
			//2.�����ұߵľ���
			g.fill3DRect(x+15, y, 5, 30,false);
			//�����м�ľ���
			g.fill3DRect(x+5, y+5, 10, 20,false);
			//����Բ��
			g.fillOval(x+4, y+10, 10, 10);
			//5.������
			g.drawLine(x+9, y+10, x+9, y-2);
			g.fillOval(x+7, y-4, 5, 5);
			break;
		case 1:
			//��Ͳ����
			//�����������
			g.fill3DRect(x, y, 30, 5, false);
			//�����������
			g.fill3DRect(x, y+15, 30, 5, false);
			//�����м�ľ���
			g.fill3DRect(x+5, y+5, 20, 10, false);
			//����Բ��
			g.fillOval(x+10, y+4, 10, 10);
			//������
			g.drawLine(x+15, y+9, x+32, y+9);
			g.fillOval(x+30, y+7, 5, 5);
			break;
		case 2:
			//����
			g.fill3DRect(x, y, 5, 30,false);
			//2.�����ұߵľ���
			g.fill3DRect(x+15, y, 5, 30,false);
			//�����м�ľ���
			g.fill3DRect(x+5, y+5, 10, 20,false);
			//����Բ��
			g.fillOval(x+4, y+10, 10, 10);
			//5.������
			g.drawLine(x+9, y+15, x+9, y+32);
			g.fillOval(x+7, y+30, 5, 5);
			break;
		case 3:
			//����
			//�����������
			g.fill3DRect(x, y, 30, 5, false);
			//�����������
			g.fill3DRect(x, y+15, 30, 5, false);
			//�����м�ľ���
			g.fill3DRect(x+5, y+5, 20, 10, false);
			//����Բ��
			g.fillOval(x+10, y+4, 10, 10);
			//������
			g.drawLine(x+15, y+9, x-2, y+9);
			g.fillOval(x-4, y+7, 5, 5);
			break;
		}
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	//�����´���a ��ʾ���� s ��ʾ���� w ����  d ����
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
//		AePlayWave apw=new AePlayWave("222.wav");
//		Thread t=new Thread(apw);
//		t.start();
		
		
		if(e.getKeyCode()==KeyEvent.VK_W&&this.hero.isAllow_move())
		{
			//�����ҵ�̹�˵ķ���
			this.hero.setDirect(0);
			this.hero.moveUP();
		}else if(e.getKeyCode()==KeyEvent.VK_D&&this.hero.isAllow_move())
		{
				//����
			this.hero.setDirect(1);
			this.hero.moveRight();
		}else if(e.getKeyCode()==KeyEvent.VK_S&&this.hero.isAllow_move())
		{
			//����
			this.hero.setDirect(2);
			this.hero.moveDown();
		}else if(e.getKeyCode()==KeyEvent.VK_A&&this.hero.isAllow_move())
		{
			//����
			this.hero.setDirect(3);
			this.hero.moveLeft();
		}
	
		//�ж�����Ƿ���j��
		if(e.getKeyCode()==KeyEvent.VK_J)
		{
			System.out.println("this.hero.ss.size()"+this.hero.ss.size());
			if(this.hero.ss.size()<=5&&this.hero.islive)
			{
				this.hero.shotEnemy();
			}
		}
		
		if(e.getKeyCode()==KeyEvent.VK_SPACE)
		{
			this.pause();
		}
		//�������»���Panel
		this.repaint();
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//ÿ��100����ȥ�ػ�
		while(true)
		{
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			
			
			this.hitEnemyTank();
			
			//�жϵ��˵�̹���Ƿ������
			this.hitMe();
			//�ػ�
			this.repaint();
		}
	}
}
