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



/**�����Ϸ---����*/
public class ShootGame extends JPanel{
	    /**����*/
      public static final int WIDTH = 400;   //���ڵĿ�
      public static final int HEIGHT = 654;  //���ڵĸ�
      	/** ����Ϸ�ĸ���״̬(��ʼ����ͣ����������Ϸ��)*/
      public static final int START = 0;  //��ʼ״̬
      public static final int RUNNING = 1; //��Ϸ����״̬
      public static final int PAUSE = 2;  //��ͣ״̬
      public static final int GAMEOVER = 3; //��Ϸ״̬
      public int state = START;    //Ĭ�ϵ�ǰ״̬
      
      
       /**static��*/
      public static BufferedImage background; //����
      public static BufferedImage start;      //��ʼ
      public static BufferedImage pause;      //��ͣ
      public static BufferedImage gameover;   //����
      public static BufferedImage airplane;   //�л�
      public static BufferedImage bee;        //С�۷�
      public static BufferedImage bullet;     //�ӵ�
      public static BufferedImage hero0;       //Ӣ�ۻ�
      public static BufferedImage hero1;      //Ӣ�ۻ�1
      
      /**����Ӣ�ۻ����ӵ����飬���������*/
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
       * С�۷�͵л�����
       */
      	int count = 0;   //��������ʱ
      public void flyingsEnter() {
  			count++;
      	if (count%20==0) {  //ÿ400����������һ������
      		FlyingObject one = createFlyings();
      		flyings = Arrays.copyOf(flyings, flyings.length+1);//��������
      		flyings[flyings.length-1] = one;//�������ɵĶ�����뵽flyings��������һ��λ��
  		}		
  	}

      /**
       * ��������һ��(����Ӣ�ۻ���С�۷�͵л�)
       * 
       */
  	 public void moveStep() {
  		//Ӣ�ۻ��л�
  		hero.step();
  		
  		//��������һ��
  		for (int i = 0; i < flyings.length; i++) {
  			flyings[i].step();
  		}
  		//�ӵ���һ��
  		for (int i = 0; i < bullets.length; i++) {
  			bullets[i].step();
  		}
  	}
  	/**
       * ������ɵл���С�۷�
       */
      public  FlyingObject createFlyings() {
  		Random random = new Random();
  		int n = random.nextInt(20);
  		if (n>=16) { //�ٷ�֮30����С�۷䣬�ٷ�֮70���ɵл�
  			return new Bee();//����С�۷����
  		}else {
  			return new Airplane();//���ɵл�����
  		}
  		
  	}

      /**
       * �ӵ��볡
       */
      int indexshoot = 0; //�ӵ��볡����
      public void shootAction() {
    	  indexshoot++;
    	  if (indexshoot%30==0) {  //300���뷢��һ���ӵ�
			Bullet[] bs = hero.shootBullet(); //����һ���ӵ��������淢����ӵ�
			bullets = Arrays.copyOf(bullets, bullets.length+bs.length);
			//�����׷��
			System.arraycopy(bs, 0, bullets, bullets.length-bs.length, bs.length);
		}
      }
      
    
      /**
       * ɾ��Խ��ķ�����
       */
      public void outofBound() {
  		int index = 0;//��Խ����˵ĸ���
  		//��Խ���������
  		FlyingObject[] flyingsLives = new FlyingObject[flyings.length];
  		//�������е��ˣ��鿴�Ƿ�Խ��
  		for (int i = 0; i < flyingsLives.length; i++) {
			FlyingObject f = flyings[i]; //���ÿһ�����˶���
			if (!f.outofbound()) {
				flyingsLives[index] = f;//û��Խ��ʹ�ŵ�flyingsLives������
				index++;
			}
		}
  		//�µĵ�������
  		flyings = Arrays.copyOf(flyingsLives, index);
  		
  		index=0;//index����
  		//û��Խ����ӵ�����
  		Bullet[] bulletsLives = new Bullet[bullets.length];
  		//���������ӵ����鿴�Ƿ�Խ��
  		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];  //��ȡ�ӵ�����
			if (!b.outofbound()) {
				bulletsLives[index] = b;  //û��Խ����ӵ��ŵ�bulletsLives������
				index++;
			}
		}
  		bullets = Arrays.copyOf(bulletsLives, index);
  		
  	}
      
      
      /**
       * �����ӵ������е��˵���ײ
       */
      public void bangAction() {
  		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			bang(b);
			System.out.println("*****");
		}
  		
  	}
      
      
      
      /**
       * һ���ӵ������е��˵���ײ
       */
      int score = 0;  //�÷�
      public void bang(Bullet b) {
    	  int index = -1;//�ж��Ƿ�ײ������
    	  //�������е��˶���
    	  for (int i = 0; i < flyings.length; i++) { //δԽ��ĵ�������
    		  FlyingObject f = flyings[i];//��ȡÿһ�����˶���
    		  if (f.shootBy(b)) {
				index = i;
				break;  //��������˾��˳�ѭ��
			}
		}
    	  
    	  /**
    	   * �����ˣ�����ǵл���÷֣�
    	   * �����С�۷���ý���(��Ҫ�ж��ǵ������ǵ÷���)
    	   * 
    	   */
    	  if (index!=-1) {  //������
			FlyingObject one = flyings[index]; //��������ĵ��˶���
			if (one instanceof Enemy) {
				score += 5;  //���һ�����˵�5��
			}
			
			if (one instanceof Award) {
				Award a = (Award)one; //��oneǿתΪAward����
				int type = a.getType(); //����0��1
				switch (type) {
				case Award.DOUBLE_FIRE: //��Ϊ������0����������ֵ
					hero.addFire();
					break;
				case Award.LIFE: //��Ϊ����1������һ����
					hero.addLife();  //Ӣ�ۻ�����
					break;
				default:
					break;
				}
				
			}
			
			//������ײ��������������һ��Ԫ�أ�ͨ��������ȥ������ײ�ĵ���
	    	  FlyingObject t = flyings[index];
	    	  flyings[index] = flyings[flyings.length-1];
	    	  flyings[flyings.length-1] = t;
	    	  flyings = Arrays.copyOf(flyings, flyings.length-1);
			
		}	  
      }
      
      /**
       * �ж���Ϸ�Ƿ����
       */
      public boolean isGameOver() {
  		for (int i = 0; i < flyings.length; i++) { //�������е���
			FlyingObject f = flyings[i];  //��ȡÿһ������
			if (hero.hit(f)) {   //�ж�Ӣ�ۻ�������Ƿ���ײ
				hero.abstractLife(); //Ӣ�ۻ�����
				hero.clearFire();//��ջ���ֵ
				/*
				 * ɾ����Ӣ�ۻ���ײ�ĵ���
				 * ������ײ���������һ��Ԫ��
				 */
				FlyingObject t = flyings[i];
				flyings[i] = flyings[flyings.length-1];
				flyings[flyings.length-1] = t;
				flyings = Arrays.copyOf(flyings, flyings.length-1);
				
				
			}
		
		}
  		//����Ӣ�ۻ�������
  		return hero.getLife()<=0;
  		
  	}
      
      /**
       * �����Ϸ�Ƿ����
       */
     public void checkGameOver() {
    	 if (isGameOver()) { //�����Ϸ����
			state = GAMEOVER;
		}
     }
      
      
      
    public void action() {
    	/**
    	 * �������������
    	 * MouseAdapter��һ�������࣬�ṩ��
    	 * ������ط���
    	 */
    	MouseAdapter mouse = new MouseAdapter() {		
    		//��д����ƶ��¼�  		
    		 public void mouseMoved(MouseEvent e){
    			 if (state==RUNNING) {
    				 //Ӣ�ۻ����������ƶ����ƶ�
    	    			int x = e.getX();//�������x����
    	    			int y = e.getY();//�������y����
    	    			 hero.move(x, y);   
				}	 
    		 }
    		 
    		 //��굥���¼�
    		 public void mouseClicked(MouseEvent e) {
    			 switch (state) {
				case START:
					state = RUNNING;
					break;
				case GAMEOVER:
					//��Ϸ�������������ݹ���
					hero = new Hero();
					flyings = new FlyingObject[0];
					bullets = new Bullet[0];
					state = START; //��Ϊ����״̬
					break;
				default:
					break;
				}
    		 }
    		 
    		 //��������¼�
    		 public void mouseEntered(MouseEvent e) {
    			 if (state==PAUSE ) {
					state=RUNNING;
				}
    		 }
    		 
    		 //����Ƴ��¼�
    		 public void mouseExited(MouseEvent e) {
    			 if (state==RUNNING) {
					state = PAUSE;
				}
    		 }
    		
    	};
    	
		
    	this.addMouseListener(mouse);//�����������¼�
    	this.addMouseMotionListener(mouse);//������껬���¼�
	
    	 /**
         * ����һ����ʱ��
         * ÿ20������
         */
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {		
			@Override
			public void run() {
				if (state==RUNNING) {  
					createFlyings();//�����л���С�۷�
					flyingsEnter();//С�۷�͵л�����
					moveStep();//��������һ��
					shootAction();//�ӵ��볡
					outofBound();//ɾ��Խ��ķ�����
					bangAction();//�ӵ�����˵���ײ
					checkGameOver();//�����Ϸ�Ƿ����
				}
		
				repaint();//ÿ10�������ػ�һ��
			}	
		}, 10,10);
              
    }

	/**��дpaint()���� g:����
     * ��������Ӣ�ۻ����ӵ��ͷ�����
     */
    public void paint(Graphics g) {
        g.drawImage(background, 0, 0, null);
        paintHero(g);     //��Ӣ�ۻ�
        paintBullets(g);  //���ӵ�
        paintFlyingObjects(g);  //��������
        paintScoreAndLife(g);//���ֺ���
        paintStage(g);//��״̬
    }
     
	/**��Ӣ�ۻ�*/
      public void paintHero(Graphics g) {
    	  g.drawImage(hero.image, hero.x, hero.y, null);
      }
      /**���ӵ�*/
      public void paintBullets(Graphics g) {
    	 for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			g.drawImage(b.image,b.x,b.y,null);
		}
		
      }
      /**������(�л�+С�۷�)*/
      public void paintFlyingObjects(Graphics g) {
    	 for (int i = 0; i < flyings.length; i++) {
			FlyingObject f = flyings[i];
			g.drawImage(f.image, f.x, f.y, null	);
		}
		
      }
      
      /**
       * ���ֺ���
       */
      public void paintScoreAndLife(Graphics g) {
    	  g.setColor(Color.GREEN);  //���û���Ϊ��ɫ
    	  g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));//����������ʽ
    	  g.drawString("SCORE:"+score, 10, 30);
    	  g.drawString("LIFE:"+hero.getLife(), 10, 50);
      }
      
      /**
       * ��״̬(4��״̬)
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
    	  JFrame frame = new JFrame("Fly");  //����
    	  
  		 ShootGame game = new ShootGame();  //���
  		 
  		 frame.add(game);  //�������ӵ�������
  		
  		frame.setSize(WIDTH, HEIGHT);
  		frame.setTitle("�ɻ���ս");
  		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  		frame.setLocationRelativeTo(null);
  		frame.setVisible(true);
		
          game.action();
      }
}


	


