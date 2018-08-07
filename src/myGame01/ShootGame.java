package myGame01;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
import java.awt.Color;
import java.awt.Font;



/**设计游戏---主类*/
public class ShootGame extends JPanel{
	    /**常量*/
      public static final int WIDTH = 400;   //窗口的宽
      public static final int HEIGHT = 654;  //窗口的高
      	/** 画游戏的各种状态(开始，暂停，结束，游戏中)*/
      public static final int START = 0;  //开始状态
      public static final int RUNNING = 1; //游戏运行状态
      public static final int PAUSE = 2;  //暂停状态
      public static final int GAMEOVER = 3; //游戏状态
      public int state = START;    //默认当前状态
      
      
       /**static块*/
      public static BufferedImage background; //背景
      public static BufferedImage start;      //开始
      public static BufferedImage pause;      //暂停
      public static BufferedImage gameover;   //结束
      public static BufferedImage airplane;   //敌机
      public static BufferedImage bee;        //小蜜蜂
      public static BufferedImage bullet;     //子弹
      public static BufferedImage hero0;       //英雄机
      public static BufferedImage hero1;      //英雄机1
      
      /**创建英雄机，子弹数组，飞行物对象*/
      private Hero hero = new Hero();
      private Bullet[] bullets = {};
      private FlyingObject[] flyings = {};
     
      
     
      static {
		try {
			background = ImageIO.read(ShootGame.class.getResource("background.png"));
			bee = ImageIO.read(ShootGame.class.getResource("bee.png"));
			bullet = ImageIO.read(ShootGame.class.getResource("bullet.png"));
			airplane = ImageIO.read(ShootGame.class.getResource("airplane.png"));
			hero0 = ImageIO.read(ShootGame.class.getResource("hero0.png"));
			hero1 = ImageIO.read(ShootGame.class.getResource("hero1.png"));
			start = ImageIO.read(ShootGame.class.getResource("start.png"));
			pause = ImageIO.read(ShootGame.class.getResource("pause.png"));
			gameover = ImageIO.read(ShootGame.class.getResource("gameover.png"));
			
		} catch ( IOException e) {
			e.printStackTrace();
		}
    	
      }
        
      
      
      /**
       * 小蜜蜂和敌机进场
       */
      	int count = 0;   //用于来计时
      public void flyingsEnter() {
  			count++;
      	if (count%20==0) {  //每400个毫秒生成一个对象
      		FlyingObject one = createFlyings();
      		flyings = Arrays.copyOf(flyings, flyings.length+1);//数组扩容
      		flyings[flyings.length-1] = one;//将新生成的对象加入到flyings数组的最后一个位置
  		}		
  	}

      /**
       * 飞行物走一步(包括英雄机，小蜜蜂和敌机)
       * 
       */
  	 public void moveStep() {
  		//英雄机切换
  		hero.step();
  		
  		//飞行物走一步
  		for (int i = 0; i < flyings.length; i++) {
  			flyings[i].step();
  		}
  		//子弹走一步
  		for (int i = 0; i < bullets.length; i++) {
  			bullets[i].step();
  		}
  	}
  	/**
       * 随机生成敌机和小蜜蜂
       */
      public  FlyingObject createFlyings() {
  		Random random = new Random();
  		int n = random.nextInt(20);
  		if (n>=16) { //百分之30生成小蜜蜂，百分之70生成敌机
  			return new Bee();//生成小蜜蜂对象
  		}else {
  			return new Airplane();//生成敌机对象
  		}
  		
  	}

      /**
       * 子弹入场
       */
      int indexshoot = 0; //子弹入场计数
      public void shootAction() {
    	  indexshoot++;
    	  if (indexshoot%30==0) {  //300毫秒发射一颗子弹
			Bullet[] bs = hero.shootBullet(); //创建一个子弹数组来存发射的子弹
			bullets = Arrays.copyOf(bullets, bullets.length+bs.length);
			//数组的追加
			System.arraycopy(bs, 0, bullets, bullets.length-bs.length, bs.length);
		}
      }
      
    
      /**
       * 删除越界的飞行物
       */
      public void outofBound() {
  		int index = 0;//不越界敌人的个数
  		//不越界敌人数组
  		FlyingObject[] flyingsLives = new FlyingObject[flyings.length];
  		//遍历所有敌人，查看是否越界
  		for (int i = 0; i < flyingsLives.length; i++) {
			FlyingObject f = flyings[i]; //获得每一个敌人对象
			if (!f.outofbound()) {
				flyingsLives[index] = f;//没有越界就存放到flyingsLives数组中
				index++;
			}
		}
  		//新的敌人数组
  		flyings = Arrays.copyOf(flyingsLives, index);
  		
  		index=0;//index归零
  		//没有越界的子弹数组
  		Bullet[] bulletsLives = new Bullet[bullets.length];
  		//遍历所有子弹，查看是否越界
  		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];  //获取子弹对象
			if (!b.outofbound()) {
				bulletsLives[index] = b;  //没有越界的子弹放到bulletsLives数组中
				index++;
			}
		}
  		bullets = Arrays.copyOf(bulletsLives, index);
  		
  	}
      
      
      /**
       * 所有子弹与所有敌人的碰撞
       */
      public void bangAction() {
  		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			bang(b);
			System.out.println("*****");
		}
  		
  	}
      
      
      
      /**
       * 一颗子弹与所有敌人的碰撞
       */
      int score = 0;  //得分
      public void bang(Bullet b) {
    	  int index = -1;//判断是否撞上问题
    	  //遍历所有敌人对象
    	  for (int i = 0; i < flyings.length; i++) { //未越界的敌人数组
    		  FlyingObject f = flyings[i];//获取每一个敌人对象
    		  if (f.shootBy(b)) {
				index = i;
				break;  //如果碰上了就退出循环
			}
		}
    	  
    	  /**
    	   * 碰上了，如果是敌机则得分，
    	   * 如果是小蜜蜂则得奖励(还要判断是得命还是得分数)
    	   * 
    	   */
    	  if (index!=-1) {  //碰到了
			FlyingObject one = flyings[index]; //获得碰到的敌人对象
			if (one instanceof Enemy) {
				score += 5;  //打掉一个敌人得5分
			}
			
			if (one instanceof Award) {
				Award a = (Award)one; //将one强转为Award类型
				int type = a.getType(); //返回0或1
				switch (type) {
				case Award.DOUBLE_FIRE: //若为火力，0代表奖励火力值
					hero.addFire();
					break;
				case Award.LIFE: //若为命，1代表奖励一条命
					hero.addLife();  //英雄机加命
					break;
				default:
					break;
				}
				
			}
			
			//交换被撞敌人与数组的最后一个元素，通过缩容来去除被碰撞的敌人
	    	  FlyingObject t = flyings[index];
	    	  flyings[index] = flyings[flyings.length-1];
	    	  flyings[flyings.length-1] = t;
	    	  flyings = Arrays.copyOf(flyings, flyings.length-1);
			
		}	  
      }
      
      /**
       * 判断游戏是否结束
       */
      public boolean isGameOver() {
  		for (int i = 0; i < flyings.length; i++) { //遍历所有敌人
			FlyingObject f = flyings[i];  //获取每一个敌人
			if (hero.hit(f)) {   //判断英雄机与敌人是否相撞
				hero.abstractLife(); //英雄机减命
				hero.clearFire();//清空火力值
				/*
				 * 删除与英雄机相撞的敌人
				 * 交换被撞敌人与最后一个元素
				 */
				FlyingObject t = flyings[i];
				flyings[i] = flyings[flyings.length-1];
				flyings[flyings.length-1] = t;
				flyings = Arrays.copyOf(flyings, flyings.length-1);
				
				
			}
		
		}
  		//返回英雄机的命数
  		return hero.getLife()<=0;
  		
  	}
      
      /**
       * 检查游戏是否结束
       */
     public void checkGameOver() {
    	 if (isGameOver()) { //如果游戏结束
			state = GAMEOVER;
		}
     }
      
      
      
    public void action() {
    	/**
    	 * 创建鼠标侦听器
    	 * MouseAdapter是一个抽象类，提供了
    	 * 鼠标的相关方法
    	 */
    	MouseAdapter mouse = new MouseAdapter() {		
    		//重写鼠标移动事件  		
    		 public void mouseMoved(MouseEvent e){
    			 if (state==RUNNING) {
    				 //英雄机随着鼠标的移动而移动
    	    			int x = e.getX();//获得鼠标的x坐标
    	    			int y = e.getY();//获得鼠标的y坐标
    	    			 hero.move(x, y);   
				}	 
    		 }
    		 
    		 //鼠标单击事件
    		 public void mouseClicked(MouseEvent e) {
    			 switch (state) {
				case START:
					state = RUNNING;
					break;
				case GAMEOVER:
					//游戏结束，所有数据归零
					hero = new Hero();
					flyings = new FlyingObject[0];
					bullets = new Bullet[0];
					state = START; //改为启动状态
					break;
				default:
					break;
				}
    		 }
    		 
    		 //鼠标移入事件
    		 public void mouseEntered(MouseEvent e) {
    			 if (state==PAUSE ) {
					state=RUNNING;
				}
    		 }
    		 
    		 //鼠标移出事件
    		 public void mouseExited(MouseEvent e) {
    			 if (state==RUNNING) {
					state = PAUSE;
				}
    		 }
    		
    	};
    	
		
    	this.addMouseListener(mouse);//处理鼠标操作事件
    	this.addMouseMotionListener(mouse);//处理鼠标滑动事件
	
    	 /**
         * 创建一个定时器
         * 每20个毫秒
         */
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {		
			@Override
			public void run() {
				if (state==RUNNING) {  
					createFlyings();//产生敌机和小蜜蜂
					flyingsEnter();//小蜜蜂和敌机进场
					moveStep();//飞行物走一步
					shootAction();//子弹入场
					outofBound();//删除越界的飞行物
					bangAction();//子弹与敌人的碰撞
					checkGameOver();//检查游戏是否结束
				}
		
				repaint();//每10个毫秒重画一次
			}	
		}, 10,10);
              
    }

	/**重写paint()方法 g:画笔
     * 用来画出英雄机，子弹和飞行物
     */
    public void paint(Graphics g) {
        g.drawImage(background, 0, 0, null);
        paintHero(g);     //画英雄机
        paintBullets(g);  //画子弹
        paintFlyingObjects(g);  //画飞行物
        paintScoreAndLife(g);//画分和命
        paintStage(g);//画状态
    }
     
	/**画英雄机*/
      public void paintHero(Graphics g) {
    	  g.drawImage(hero.image, hero.x, hero.y, null);
      }
      /**画子弹*/
      public void paintBullets(Graphics g) {
    	 for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			g.drawImage(b.image,b.x,b.y,null);
		}
		
      }
      /**画敌人(敌机+小蜜蜂)*/
      public void paintFlyingObjects(Graphics g) {
    	 for (int i = 0; i < flyings.length; i++) {
			FlyingObject f = flyings[i];
			g.drawImage(f.image, f.x, f.y, null	);
		}
		
      }
      
      /**
       * 画分和命
       */
      public void paintScoreAndLife(Graphics g) {
    	  g.setColor(Color.GREEN);  //设置画笔为绿色
    	  g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));//设置字体样式
    	  g.drawString("SCORE:"+score, 10, 30);
    	  g.drawString("LIFE:"+hero.getLife(), 10, 50);
      }
      
      /**
       * 画状态(4种状态)
       * @param g
       */
      public void paintStage(Graphics g) {
  		switch (state) {
		case START:
			g.drawImage(start,0,0,null);
			break;
		case PAUSE:
			g.drawImage(pause,0,0,null);
			break;
		case GAMEOVER:
			g.drawImage(gameover,0,0,null);
			break;
		default:
			break;
		}
  		
  	}
     
      
      
      public static void main(String[]args) {
    	  JFrame frame = new JFrame("Fly");  //窗口
    	  
  		 ShootGame game = new ShootGame();  //面板
  		 
  		 frame.add(game);  //将面板添加到窗口上
  		
  		frame.setSize(WIDTH, HEIGHT);
  		frame.setTitle("飞机大战");
  		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  		frame.setLocationRelativeTo(null);
  		frame.setVisible(true);
		
          game.action();
      }
}


	


