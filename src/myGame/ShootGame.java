package myGame;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask; 
import java.util.Arrays;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.color.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Font.*;
/** 主程序类 */
public class ShootGame extends JPanel {
	public static final int WIDTH = 400;   //窗口的宽
	public static final int HEIGHT = 654;   //窗口的高
	
	public static BufferedImage background; //背景
	public static BufferedImage statr;      //游戏开始
	public static BufferedImage pause;      //游戏暂停
	public static BufferedImage gameover;   //游戏结束
	public static BufferedImage airplane;   //敌机
	public static BufferedImage bee;        //小蜜蜂
	public static BufferedImage bullet;     //子弹
	public static BufferedImage hero0;      //英雄机0
	public static BufferedImage hero1;      //英雄机1
	 
	public static final int START = 0;   //启动状态
	public static final int RUNNING = 1; //运行状态
	public static final int PAUSE = 2;   //暂停状态
	public static final int GAME_OVER = 3; //结束状态
	private int state = START; //当前状态(默认状态)
	
	
	
	
	private Hero hero = new Hero();      //英雄机对象
	private FlyingObject[] flyings = {}; //敌人数组(包括敌机和小蜜蜂)
	private Bullet[] bullets = {};       //子弹数组	
	
	static { //初始化静态资源(图片)
		try{
			background = ImageIO.read(ShootGame.class.getResource("background.png"));
			statr = ImageIO.read(ShootGame.class.getResource("start.png"));
			pause = ImageIO.read(ShootGame.class.getResource("pause.png"));
			gameover = ImageIO.read(ShootGame.class.getResource("gameover.png"));
			airplane = ImageIO.read(ShootGame.class.getResource("airplane.png"));
			bee = ImageIO.read(ShootGame.class.getResource("bee.png"));
			bullet = ImageIO.read(ShootGame.class.getResource("bullet.png"));
			hero0 = ImageIO.read(ShootGame.class.getResource("hero0.png"));
			hero1 = ImageIO.read(ShootGame.class.getResource("hero1.png"));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 生成敌人(敌机+小蜜蜂)对象*/
	public FlyingObject nextOne() {
		Random rand = new Random();
		int type = rand.nextInt(20);
		if (type< 3) {
			return new Bee();
		} else {
            return new Airplane();
		}
	}
	
	 
	int flyEnterIndex = 0;  //敌人入场计数
	/**敌人(敌机+小蜜蜂)入场 */
	public void enterAction() {  //10个毫秒走一次
		flyEnterIndex++;
		if (flyEnterIndex%40==0) {     //每400毫秒走一次
			FlyingObject one = nextOne();//获取敌人对象
			flyings = Arrays.copyOf(flyings, flyings.length+1);//扩容
			flyings[flyings.length-1] = one; //将生成的敌人添加到数组的最后一个元素
		}
	}
	
	/**飞行物(敌机、小蜜蜂、子弹、游戏机)走一步*/
	public void stepAction() {  //10毫秒走一步
		hero.step();  //英雄机走一步
		for (int i = 0; i < flyings.length; i++) { //遍历所有敌人
			flyings[i].step(); //敌人走一步
		}
		for (int i = 0; i < bullets.length; i++) {
			bullets[i].step(); //子弹走一步
		}
	}
	
	int shootIndex = 0;  //子弹入场计数
	/** 子弹入场--英雄机发射子弹*/
	public void shootAction() { //10毫秒走一次
		shootIndex++;  //没10毫秒加1
		if (shootIndex%30==0) {  //每300毫秒发射一颗子弹
			Bullet[] bs = hero.shoot();//每次发射一颗或两颗子弹
			bullets = Arrays.copyOf(bullets, bullets.length+bs.length);//扩容，bs有几个元素就加几
			System.arraycopy(bs, 0, bullets, bullets.length-bs.length, bs.length); //数组的追加
		}
	}
	
	/**删除越界的敌人(敌机+小蜜蜂)和子弹*/
	public void outOfBoundsAction(){} {
		int index = 0;  //不越界敌人的个数，不越界敌人的数组
		FlyingObject[] flyingLives = new FlyingObject[flyings.length]; //不越界敌人数组
		for (int i = 0; i < flyingLives.length; i++) {  //遍历敌人数组
			FlyingObject f = flyings[i];  //获取每一个敌人
			if (!f.outOfBounds()) {  
				flyingLives[index] = f;  //将不越界
				index++;  //1.不越界的数组下标加1;2.不越界的数组个数加1
			}
		}
		flyings = Arrays.copyOf(flyingLives, index);//敌人
		
		index = 0; //归零
		Bullet[] bulletLives = new Bullet[bullets.length];
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			if (!b.outOfBounds()) {
				bulletLives[index] = b;
				index++;
			}
		}
		bullets = Arrays.copyOf(bulletLives, index);
	}
	
	/**所有子弹与所有敌人(敌机+小蜜蜂)的碰撞*/
	public void bangAction() {
		for (int i = 0; i < bullets.length; i++) { //遍历所有子弹
			Bullet b = bullets[i];   //获取每一个子弹
			bang(b);//1个子弹与所有敌人的碰撞
		}
	}
	
	int score = 0; //得分
	/**1个子弹与所有敌人(敌机+小蜜蜂)的碰撞*/
	public void bang(Bullet b) {
		int index = -1;  //
		for (int i = 0; i < flyings.length; i++) {
			FlyingObject f = flyings[i];
			if (f.shootBy(b)) {
				index = i;
				break;
			}
		}
		if (index!=-1) {  //撞上了
			FlyingObject one = flyings[index];
			if (one instanceof Enemy) {
				Enemy e = (Enemy)one;//将one强转为敌人
				score += e.getScore(); //玩家得分		
			}
			if (one instanceof Award) {
				Award a = (Award)one; //将one强转为奖励
				int type = a.getType();//获取奖励
				switch (type) {
				case Award.DOUBLE_FIRE: //若为火力
					hero.addDoubleFire(); //英雄机增火力值
					break;
				case Award.LIFE: //若为命
					hero.addLife(); //英雄机增命
					break;
				default:
					break;
				}
			}
			//交换被撞敌人与数组最后一个敌人
			FlyingObject t = flyings[index];
			flyings[index] = flyings[flyings.length-1];
			flyings[flyings.length-1] = t;
			flyings = Arrays.copyOf(flyings, flyings.length-1);//缩容
		}
	}
	
	/**检查游戏结束*/
	public void checkGameOverAction(){
		if (isGameOver()) { //游戏结束
			state = GAME_OVER;
		}
	}
	
	/**判断游戏是否结束,返回true则游戏结束*/
	public boolean isGameOver() {
		for (int i = 0; i < flyings.length; i++) {
			FlyingObject f = flyings[i];
			if (hero.hit(f)) { //撞上了
				hero.subtractLife();  //英雄机减命
				hero.clearDoubleFire(); //英雄机清空火力值
				//将被撞敌人与数组最后一个元素交换
				FlyingObject t = flyings[i];
				flyings[i] = flyings[flyings.length-1];
				flyings[flyings.length-1] = t;
				flyings = Arrays.copyOf(flyings, flyings.length-1);
			}
		}
		return hero.getLife()<=0;
	}
	/** 启动程序的执行*/
	
 	public void action() {
 		//创建侦听器对象
        MouseAdapter l = new MouseAdapter() {
        	/**重写鼠标移动事件*/
        	public void mouseMoved(MouseEvent e) {
        		if (state==RUNNING) {
        			int x = e.getX();  //获取鼠标的x坐标
            		int y = e.getY();  //获取鼠标的y坐标
            		hero.moveTo(x, y); //英雄机随着鼠标移动
				}
        	}
        	/**重写鼠标点击事件*/
        	public void mouseClicked(MouseEvent e) {
        		switch (state) {
				case START:  //启动状态是
					state = RUNNING; //游戏运行状态
					break;
				case GAME_OVER:
					score = 0;  //清理现场，数据归零
					hero = new Hero();
					flyings = new FlyingObject[0];
					bullets = new Bullet[0];
					state = START; //变为启动状态
					break;
				}
        	}
        	/**鼠标移出事件*/
        	 public void mouseExited(MouseEvent e) {
             	if (state==RUNNING) {
					state = PAUSE;
				}
             }
        	 /**鼠标移入事件*/
             public void mouseEntered(MouseEvent e) {
             	if (state==PAUSE) {
					state = RUNNING;
				}
             }
        };
        
       
        this.addMouseListener(l); //处理鼠标操作事件
        this.addMouseMotionListener(l); //处理鼠标滑动事件	
        
        
		Timer timer = new Timer();  //创建定时机对象
		int intervel = 10;  //时间间隔(时间以毫秒为单位)
		timer.schedule(new TimerTask() {		
			@Override
			public void run() {  //10个毫秒走一次
				if(state==RUNNING) {
				enterAction();  //敌人(敌机+小蜜蜂)走一步
				stepAction();   //飞行物走一步
				shootAction();  //子弹入场
				outOfBoundsAction(); //删除越界
				bangAction();  //子弹与敌人(敌机+小蜜蜂)的碰撞
				checkGameOverAction(); //检查游戏是否结束
				}
				repaint();    //重画---调用paint()方法
			}
		},intervel,intervel);
		
		
	}
	/**重写paint()方法 g:画笔*/
	public void paint(Graphics g) {
		g.drawImage(background,0,0,null);
		paintHero(g);
		paintFlyingObjects(g);
		paintBullets(g);
		paintScoreAndLife(g);
		paintState(g);
	}
	/**画英雄机对象 */
	public void paintHero(Graphics g) {
		g.drawImage(hero.image,hero.x,hero.y,null);
	}
	/**画敌人(敌机+小蜜蜂)对象 */
	public void paintFlyingObjects(Graphics g) {
		for (int i = 0; i < flyings.length; i++) { //遍历敌人
			FlyingObject f = flyings[i];   //获取每一个敌人
			g.drawImage(f.image,f.x,f.y,null);
			
		}
	}
	/**画子弹对象 */
	public void paintBullets(Graphics g) {
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			g.drawImage(b.image,b.x,b.y,null);
		}
	}
	/**画分和画命*/
	public void paintScoreAndLife(Graphics g) {
		g.setColor(new Color(0xFF0000));
		g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,24));//设置字体样式
		g.drawString("SCORE:"+score, 10, 25);//画分
		g.drawString("LIFE:"+hero.getLife(), 10, 45);
	}
	/**画状态*/
	public void paintState(Graphics g) {
		switch (state) {
		case START:
			g.drawImage(statr,0,0,null);
			break;
		case PAUSE:
			g.drawImage(pause,0,0,null);
			break;
		case GAME_OVER:
			g.drawImage(gameover,0,0,null);
			break;
		default:
			break;
		}
	}
	
	public static void main(String[] args) {  //main方法
		JFrame frame = new JFrame("Flying");  //窗口
		ShootGame game = new ShootGame();  //面板
		frame.add(game);  //将面板添加到窗口上
		
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
        game.action();
	}
    
	
}
