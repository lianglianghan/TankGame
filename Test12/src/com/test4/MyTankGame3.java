/**
 * 功能：坦克游戏的5.0
 * 1.画出坦克(该坦克是以左上角作为参考点)
 * 2.我的坦克可以上下左右移动
 * 3.可以发子弹，子弹可以连发(最多只能连发5颗)
 * 4.当我的坦克击中敌人的坦克时，敌人就消失
 * 5.当敌人的坦克击中我时，我爆炸消失
 * 6.防止敌人坦克重叠运动
 * 	  6.1决定把判断是否碰撞的函数写到自己的EnemyTnak类里面去
 * 7.可以分关
 *    7.1做一个开始的Panel，他是一个空的
 *    7.2闪烁效果
 * 8.可以在玩游戏的时候暂停和继续（通过space键来控制暂停与继续）
 *    8.1当用户点击暂停的时候，把子弹和坦克的速度设为0，并让坦克的方向不要变化(老师的）
 *    8.2我个人设置了坦克的各个东东的开关（射击开关，移动开关，炸弹爆炸开关）
 * 9.可以记录玩家的成绩
 *    9.1用文件流
 *    9.2单写一个记录类，完成对玩家的记录
 *    9.3先完成保存共共击毁了多少辆敌人坦克
 *    9.4存盘退出游戏，可以记录当时的敌人坦克坐标，并可以恢复
 * 10.java如何操作声音文件
 *    10.1对声音文件的操作
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
	//定义一个开始面板
	MyStartPanel msp=null;
	
	//做出我需要的菜单
	JMenuBar jmb=null;
	//开始游戏
	JMenu jm1=null;
	JMenuItem jmi1=null;
	//退出系统
	JMenuItem jmi2=null;
	//存盘退出
	JMenuItem jmi3=null;
	JMenuItem jmi4=null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyTankGame3 mtg=new MyTankGame3();
		
	}
	
	//构造函数
	public MyTankGame3()
	{
		//mp=new MyPanel();
		//启动mp线程
		//Thread t=new Thread(mp);
		//t.start();
		//this.add(mp);
		//this.addKeyListener(mp);
		
		//创建菜单及菜单选项
		jmb=new JMenuBar();
		jm1=new JMenu("游戏（G)");
		//设置助记符
		jm1.setMnemonic('G');

		jmi1 =new JMenuItem("开始新游戏(N)");
		jmi2=new JMenuItem("退出游戏(E)");
		jmi3=new JMenuItem("存盘退出游戏(C)");
		jmi4=new JMenuItem("继续上局游戏(S)");
		
		jmi4.addActionListener(this);
		jmi4.setActionCommand("continue");
		
		jmi3.addActionListener(this);
		jmi3.setActionCommand("saveExit");
		
		jmi2.addActionListener(this);
		jmi2.setActionCommand("exit");
		jmi2.setMnemonic('E');
		this.addWindowListener(this);
		
		//对jmi1响应
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
		this.setTitle("坦克大战精简版");
		this.setIconImage(new ImageIcon("images/tankgame.jpg").getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//对用户不同的点击做出不同的处理
		
		if(e.getActionCommand().equals("newgame"))
		{
			if(mp==null);
			else
				this.remove(mp);
			//创建战场面板
			mp=new MyPanel("newGame");
			//启动mp线程
			Thread t=new Thread(mp);
			t.start();
			//先删除旧的开始面板
			this.remove(msp);
			this.add(mp);
			this.addKeyListener(mp);
			//显示，刷新JFrame
			this.setVisible(true);
			
		}else if(e.getActionCommand().equals("exit"))
		{
			//用户点击了退出系统的菜单
			//保存击毁敌人数量
			Recorder.keepRecording();
			
			System.exit(0);
		}//对存盘退出做处理
		else if(e.getActionCommand().equals("saveExit"))
		{
			//工作处理
			//保存击毁敌人的数量和敌人的坐标
			Recorder recorder=new Recorder();
			recorder.setEts(mp.ets);
			recorder.keepRecAndEnemyTank();
			
			//退出(0表示正常退出，-1表示异常退出)
			System.exit(0);
		}else if(e.getActionCommand().equals("continue"))
		{
			if(mp==null);
			else
				this.remove(mp);
			//恢复
			//创建战场面板
			mp=new MyPanel("con");
			
			
			//启动mp线程
			Thread t=new Thread(mp);
			t.start();
			//先删除旧的开始面板
			this.remove(msp);
			this.add(mp);
			this.addKeyListener(mp);
			//显示，刷新JFrame
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
		//工作处理
		//保存击毁敌人的数量和敌人的坐标
		if(this.msp==null)
		{
			System.out.println("正在关闭");
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

//就是一个提示作用
class MyStartPanel extends JPanel implements Runnable
{
	int times=0;
	public void paint(Graphics g)
	{
		super.paint(g);
		g.fillRect(0, 0, 400, 300);
		//提示信息
		
		if(times%2==0)
		{
			g.setColor(Color.yellow);
			//开关信息的字体
			Font myFont=new Font("华文新魏",Font.BOLD,30);
			g.setFont(myFont);
			g.drawString("stage: 1", 150, 150);
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			//休眠
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
			//重画
			this.repaint();
		}
	}
}

//我的面板
class MyPanel extends JPanel implements KeyListener,Runnable
{
	//用于处理pause
	int count=0;
	//定义一个我的坦克
	Hero hero=null;
	
	
	
	//定义敌人的坦克组（采用集合，线程比较安全）
	Vector<EnemyTank> ets=new Vector<EnemyTank>();
	Vector<Node> nodes=new Vector<Node>();
	Vector<Tank> tanks=new Vector<Tank>();
	
	//定义炸弹集合
	Vector<Bomb> bombs=new  Vector<Bomb>();
	
	int enSize=5;
	
	//定义三张图片,三张图片才能组成一颗炸弹
	Image image1=null;
	Image image2=null;
	Image image3=null;
	//构造函数
	public MyPanel(String flag)
	{
		//恢复记录
		Recorder.getRecording();
		
		hero=new Hero(180, 100);
		
		if(flag.equals("newGame"))
		{
			//初始化敌人的坦克
			for(int i=0;i<enSize;i++)
			{
				
				//创建一辆敌人的坦克对象
				EnemyTank et=new EnemyTank((i+1)*50, 0);
				et.setColor(0);
				et.setDirect(2);
				//将MyPanel的敌人坦克向量交给敌人坦克
				Thread t=new Thread(et);
				t.start();
				//给敌人添加一颗子弹
				Shot s =new Shot(et.x+10, et.y+30, 2);
				//加入给敌人
				et.ss.add(s);
				Thread t2=new Thread(s);
				t2.start();
				//加入
				ets.add(et);
				this.pass();
			}
		}else
		{
			this.nodes=new Recorder().getNodesAndEnNums();
			//恢复敌人的坦克
			for(int i=0;i<nodes.size();i++)
			{
				Node node=nodes.get(i);
				//创建一辆敌人的坦克对象
				EnemyTank et=new EnemyTank(node.x,node.y);
				et.setColor(0);
				et.setDirect(node.direct);
				//将MyPanel的敌人坦克向量交给敌人坦克
				
				//et.setH(this.hero);
				Thread t=new Thread(et);
				t.start();
				//给敌人添加一颗子弹
				Shot s =new Shot(et.x+10, et.y+30, 2);
				//加入给敌人
				et.ss.add(s);
				Thread t2=new Thread(s);
				t2.start();
				//加入
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
		
		//播放开战声音
		AePlayWave apw=new AePlayWave("111.wav");
		Thread t=new Thread(apw);
		t.start();
		
//		//初始化图片
//		image1=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
//		image2=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
//		image3=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));
	}
	
	//写一个函数把MyPanel上的坦克向量交给Tank
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
	//画出提示信息
	public void showInfo(Graphics g)
	{
		//画出提示信息坦克（该坦克不参与战斗）
		this.drawTank(80, 330, g, 0, 0);
		g.setColor(Color.black);
		g.drawString(Recorder.getEnNum()+"", 110, 350);
	    this.drawTank(140, 330, g, 0, 1);
		g.setColor(Color.black);
		g.drawString(Recorder.getMyLife()+"", 170, 350);
		
		//画出玩家的总成绩
		g.setColor(Color.black);
		Font f=new Font("宋体",Font.BOLD,20);
		g.setFont(f);
		g.drawString("您的总成绩",420,30);
		
		this.drawTank(420, 60, g, 0, 0);
		
		g.setColor(Color.black);
		g.drawString(Recorder.getAllEnNum()+"", 460, 80);
	}
	//重写paint
	public void paint(Graphics g)
	{
		super.paint(g);
		g.fillRect(0, 0, 400, 300);
		
		//画出提示信息
		this.showInfo(g);
		
		//画出自己的坦克
		if(hero.islive)
		{
			this.drawTank(hero.getX(), hero.getY(), g, this.hero.getDirect(), 1);
		}
		//从ss中取出每一颗子弹，并画出
		//注意这里要设置子弹的颜色，否则当坦克死后子弹的颜色会变成黑色
		//暂时先设置为yellow，到时候需要动态改变
		g.setColor(Color.yellow);
		for(int i=0;i<this.hero.ss.size();i++)
		{
			Shot myshot=hero.ss.get(i);
			//画出子弹,画出一颗子弹
			if(myshot!=null&&myshot.islive==true)
			{
				g.drawRect(myshot.x, myshot.y, 1, 1);
			}
			if(myshot.islive==false)
			{
				//从ss中删除该子弹
				hero.ss.remove(myshot);
			}
		}
		
	
		//画出炸弹
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
			//让b的生命值减小
			if(b.allow_bomb)
			{
				b.lifeDown();
			}
			//如果炸弹生命值为0，就把该炸弹从bombs向量中去掉
			if(b.life==0)
			{
				bombs.remove(b);
			}
		}
		//画出敌人的坦克
		for(int i=0;i<ets.size();i++)
		{
			EnemyTank et=ets.get(i);
			if(et.islive)
			{
			  this.drawTank(et.getX(), et.getY(), g, et.getDirect(), 0);
			  //画出敌人的子弹
			  for(int j=0;j<et.ss.size();j++)
			  {
				  
				  //取出子弹
				  Shot enemyShot=et.ss.get(j);
				  if(enemyShot.islive)
				  {
					  g.drawRect(enemyShot.x, enemyShot.y, 1, 1);
				  }else{
					  //如果敌人的坦克死亡就从中remove中
					  et.ss.remove(enemyShot);
				  }
				  
			  }
			}
		}
	}
	
	
	
	//写一个专门的函数来判断敌人的子弹和我的子弹是否相撞，相撞的话则两者都消失
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
						System.out.println("两者相撞");
						this.hero.ss.get(i).islive=false;
						this.ets.get(j).ss.get(k).islive=false;
					}
				}
			}
		}
	}
	
	//写一个专门的函数用来处理暂停和继续
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
	//判断敌人的子弹是否击中我
	public void hitMe()
	{
		//取出每一个敌人的坦克
		for(int i=0;i<this.ets.size();i++)
		{
			//取出坦克
			EnemyTank et=ets.get(i);
			
			//取出每一颗子弹
			for(int j=0;j<et.ss.size();j++)
			{
				//取出子弹
				Shot enemyshot=et.ss.get(j);
				if(hero.islive)
				{
					if(this.hitTank(enemyshot, hero))
					{
						Recorder.reduceMyLife();
						//如果我的坦克死了，我的子弹也跟着消失
//						for(int k=0;k<this.hero.ss.size();k++)
//						{
//							this.hero.ss.get(k).islive=false;
//						}
					}
				}
				
			}
		}
	}
	//我的子弹是否击中敌人
	public void hitEnemyTank()
	{
		//判断是否击中敌人的坦克
		for(int i=0;i<hero.ss.size();i++)
		{
			
			//取出子弹
			Shot myshot=hero.ss.get(i);
			//判断子弹是否有效
			this.shot_touch();
			if(myshot.islive)
			{
				//取出每个坦克，与之判断
				for(int j=0;j<ets.size();j++)
				{
					//取出坦克
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
	//写一个函数专门判断子弹是否集中坦克
	public boolean hitTank(Shot s,Tank et)
	{
		boolean b2=false;
		//判断该坦克的方向
		switch(et.direct)
		{
		//如果敌人的方向是向上或者向下
		case 0:
		case 2:
			if(s.x>=et.x-1&&s.x<et.x+20&&s.y>=et.y&&s.y<=et.y+30)
			{
				//击中
				
				//子弹死亡
				s.islive=false;
				//敌人坦克死亡
				et.islive=false;
				b2=true;
				//创建一颗炸弹，放入Vector中
				Bomb b=new Bomb(et.x, et.y);
				bombs.add(b);
			}
			break;
		case 1:
		case 3:
			if(s.x>=et.x-1&&s.x<=et.x+30&&s.y>=et.y&&s.y<=et.y+20)
			{
				//击中
				//子弹死亡
				s.islive=false;
				//敌人坦克死亡
				et.islive=false;
				b2=true;
				Bomb b=new Bomb(et.x, et.y);
				bombs.add(b);
			}
			break;
		}
		return b2;
	}
	
	//画出坦克的函数(扩展）
	public void drawTank(int x,int y,Graphics g,int direct,int type)
	{
		//判断是什么类型的坦克
		switch(type)
		{
		case 0:
			g.setColor(Color.cyan);
			break;
		case 1:
			g.setColor(Color.yellow);
			
		}
		
		//判断方向
		switch(direct)
		{
		//向上
		case 0:
			
			//画出我的坦克（到时再封装成一个函数）
			//1.画出左边的矩形
			g.fill3DRect(x, y, 5, 30,false);
			//2.画出右边的矩形
			g.fill3DRect(x+15, y, 5, 30,false);
			//画出中间的矩形
			g.fill3DRect(x+5, y+5, 10, 20,false);
			//画出圆形
			g.fillOval(x+4, y+10, 10, 10);
			//5.画出线
			g.drawLine(x+9, y+10, x+9, y-2);
			g.fillOval(x+7, y-4, 5, 5);
			break;
		case 1:
			//炮筒向右
			//画出上面矩形
			g.fill3DRect(x, y, 30, 5, false);
			//画出下面矩形
			g.fill3DRect(x, y+15, 30, 5, false);
			//画出中间的矩形
			g.fill3DRect(x+5, y+5, 20, 10, false);
			//画出圆形
			g.fillOval(x+10, y+4, 10, 10);
			//画出线
			g.drawLine(x+15, y+9, x+32, y+9);
			g.fillOval(x+30, y+7, 5, 5);
			break;
		case 2:
			//向下
			g.fill3DRect(x, y, 5, 30,false);
			//2.画出右边的矩形
			g.fill3DRect(x+15, y, 5, 30,false);
			//画出中间的矩形
			g.fill3DRect(x+5, y+5, 10, 20,false);
			//画出圆形
			g.fillOval(x+4, y+10, 10, 10);
			//5.画出线
			g.drawLine(x+9, y+15, x+9, y+32);
			g.fillOval(x+7, y+30, 5, 5);
			break;
		case 3:
			//向左
			//画出上面矩形
			g.fill3DRect(x, y, 30, 5, false);
			//画出下面矩形
			g.fill3DRect(x, y+15, 30, 5, false);
			//画出中间的矩形
			g.fill3DRect(x+5, y+5, 20, 10, false);
			//画出圆形
			g.fillOval(x+10, y+4, 10, 10);
			//画出线
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
	//键按下处理a 表示向左 s 表示向下 w 向下  d 向右
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
//		AePlayWave apw=new AePlayWave("222.wav");
//		Thread t=new Thread(apw);
//		t.start();
		
		
		if(e.getKeyCode()==KeyEvent.VK_W&&this.hero.isAllow_move())
		{
			//设置我的坦克的方向
			this.hero.setDirect(0);
			this.hero.moveUP();
		}else if(e.getKeyCode()==KeyEvent.VK_D&&this.hero.isAllow_move())
		{
				//向右
			this.hero.setDirect(1);
			this.hero.moveRight();
		}else if(e.getKeyCode()==KeyEvent.VK_S&&this.hero.isAllow_move())
		{
			//向下
			this.hero.setDirect(2);
			this.hero.moveDown();
		}else if(e.getKeyCode()==KeyEvent.VK_A&&this.hero.isAllow_move())
		{
			//向左
			this.hero.setDirect(3);
			this.hero.moveLeft();
		}
	
		//判断玩家是否按下j键
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
		//必须重新绘制Panel
		this.repaint();
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//每隔100毫秒去重绘
		while(true)
		{
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			
			
			this.hitEnemyTank();
			
			//判断敌人的坦克是否击中我
			this.hitMe();
			//重绘
			this.repaint();
		}
	}
}
