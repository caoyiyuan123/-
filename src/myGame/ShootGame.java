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
/** �������� */
public class ShootGame extends JPanel {
	public static final int WIDTH = 400;   //���ڵĿ�
	public static final int HEIGHT = 654;   //���ڵĸ�
	
	public static BufferedImage background; //����
	public static BufferedImage statr;      //��Ϸ��ʼ
	public static BufferedImage pause;      //��Ϸ��ͣ
	public static BufferedImage gameover;   //��Ϸ����
	public static BufferedImage airplane;   //�л�
	public static BufferedImage bee;        //С�۷�
	public static BufferedImage bullet;     //�ӵ�
	public static BufferedImage hero0;      //Ӣ�ۻ�0
	public static BufferedImage hero1;      //Ӣ�ۻ�1
	 
	public static final int START = 0;   //����״̬
	public static final int RUNNING = 1; //����״̬
	public static final int PAUSE = 2;   //��ͣ״̬
	public static final int GAME_OVER = 3; //����״̬
	private int state = START; //��ǰ״̬(Ĭ��״̬)
	
	
	
	
	private Hero hero = new Hero();      //Ӣ�ۻ�����
	private FlyingObject[] flyings = {}; //��������(�����л���С�۷�)
	private Bullet[] bullets = {};       //�ӵ�����	
	
	static { //��ʼ����̬��Դ(ͼƬ)
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
	
	/** ���ɵ���(�л�+С�۷�)����*/
	public FlyingObject nextOne() {
		Random rand = new Random();
		int type = rand.nextInt(20);
		if (type< 3) {
			return new Bee();
		} else {
            return new Airplane();
		}
	}
	
	 
	int flyEnterIndex = 0;  //�����볡����
	/**����(�л�+С�۷�)�볡 */
	public void enterAction() {  //10��������һ��
		flyEnterIndex++;
		if (flyEnterIndex%40==0) {     //ÿ400������һ��
			FlyingObject one = nextOne();//��ȡ���˶���
			flyings = Arrays.copyOf(flyings, flyings.length+1);//����
			flyings[flyings.length-1] = one; //�����ɵĵ�����ӵ���������һ��Ԫ��
		}
	}
	
	/**������(�л���С�۷䡢�ӵ�����Ϸ��)��һ��*/
	public void stepAction() {  //10������һ��
		hero.step();  //Ӣ�ۻ���һ��
		for (int i = 0; i < flyings.length; i++) { //�������е���
			flyings[i].step(); //������һ��
		}
		for (int i = 0; i < bullets.length; i++) {
			bullets[i].step(); //�ӵ���һ��
		}
	}
	
	int shootIndex = 0;  //�ӵ��볡����
	/** �ӵ��볡--Ӣ�ۻ������ӵ�*/
	public void shootAction() { //10������һ��
		shootIndex++;  //û10�����1
		if (shootIndex%30==0) {  //ÿ300���뷢��һ���ӵ�
			Bullet[] bs = hero.shoot();//ÿ�η���һ�Ż������ӵ�
			bullets = Arrays.copyOf(bullets, bullets.length+bs.length);//���ݣ�bs�м���Ԫ�ؾͼӼ�
			System.arraycopy(bs, 0, bullets, bullets.length-bs.length, bs.length); //�����׷��
		}
	}
	
	/**ɾ��Խ��ĵ���(�л�+С�۷�)���ӵ�*/
	public void outOfBoundsAction(){} {
		int index = 0;  //��Խ����˵ĸ�������Խ����˵�����
		FlyingObject[] flyingLives = new FlyingObject[flyings.length]; //��Խ���������
		for (int i = 0; i < flyingLives.length; i++) {  //������������
			FlyingObject f = flyings[i];  //��ȡÿһ������
			if (!f.outOfBounds()) {  
				flyingLives[index] = f;  //����Խ��
				index++;  //1.��Խ��������±��1;2.��Խ������������1
			}
		}
		flyings = Arrays.copyOf(flyingLives, index);//����
		
		index = 0; //����
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
	
	/**�����ӵ������е���(�л�+С�۷�)����ײ*/
	public void bangAction() {
		for (int i = 0; i < bullets.length; i++) { //���������ӵ�
			Bullet b = bullets[i];   //��ȡÿһ���ӵ�
			bang(b);//1���ӵ������е��˵���ײ
		}
	}
	
	int score = 0; //�÷�
	/**1���ӵ������е���(�л�+С�۷�)����ײ*/
	public void bang(Bullet b) {
		int index = -1;  //
		for (int i = 0; i < flyings.length; i++) {
			FlyingObject f = flyings[i];
			if (f.shootBy(b)) {
				index = i;
				break;
			}
		}
		if (index!=-1) {  //ײ����
			FlyingObject one = flyings[index];
			if (one instanceof Enemy) {
				Enemy e = (Enemy)one;//��oneǿתΪ����
				score += e.getScore(); //��ҵ÷�		
			}
			if (one instanceof Award) {
				Award a = (Award)one; //��oneǿתΪ����
				int type = a.getType();//��ȡ����
				switch (type) {
				case Award.DOUBLE_FIRE: //��Ϊ����
					hero.addDoubleFire(); //Ӣ�ۻ�������ֵ
					break;
				case Award.LIFE: //��Ϊ��
					hero.addLife(); //Ӣ�ۻ�����
					break;
				default:
					break;
				}
			}
			//������ײ�������������һ������
			FlyingObject t = flyings[index];
			flyings[index] = flyings[flyings.length-1];
			flyings[flyings.length-1] = t;
			flyings = Arrays.copyOf(flyings, flyings.length-1);//����
		}
	}
	
	/**�����Ϸ����*/
	public void checkGameOverAction(){
		if (isGameOver()) { //��Ϸ����
			state = GAME_OVER;
		}
	}
	
	/**�ж���Ϸ�Ƿ����,����true����Ϸ����*/
	public boolean isGameOver() {
		for (int i = 0; i < flyings.length; i++) {
			FlyingObject f = flyings[i];
			if (hero.hit(f)) { //ײ����
				hero.subtractLife();  //Ӣ�ۻ�����
				hero.clearDoubleFire(); //Ӣ�ۻ���ջ���ֵ
				//����ײ�������������һ��Ԫ�ؽ���
				FlyingObject t = flyings[i];
				flyings[i] = flyings[flyings.length-1];
				flyings[flyings.length-1] = t;
				flyings = Arrays.copyOf(flyings, flyings.length-1);
			}
		}
		return hero.getLife()<=0;
	}
	/** ���������ִ��*/
	
 	public void action() {
 		//��������������
        MouseAdapter l = new MouseAdapter() {
        	/**��д����ƶ��¼�*/
        	public void mouseMoved(MouseEvent e) {
        		if (state==RUNNING) {
        			int x = e.getX();  //��ȡ����x����
            		int y = e.getY();  //��ȡ����y����
            		hero.moveTo(x, y); //Ӣ�ۻ���������ƶ�
				}
        	}
        	/**��д������¼�*/
        	public void mouseClicked(MouseEvent e) {
        		switch (state) {
				case START:  //����״̬��
					state = RUNNING; //��Ϸ����״̬
					break;
				case GAME_OVER:
					score = 0;  //�����ֳ������ݹ���
					hero = new Hero();
					flyings = new FlyingObject[0];
					bullets = new Bullet[0];
					state = START; //��Ϊ����״̬
					break;
				}
        	}
        	/**����Ƴ��¼�*/
        	 public void mouseExited(MouseEvent e) {
             	if (state==RUNNING) {
					state = PAUSE;
				}
             }
        	 /**��������¼�*/
             public void mouseEntered(MouseEvent e) {
             	if (state==PAUSE) {
					state = RUNNING;
				}
             }
        };
        
       
        this.addMouseListener(l); //�����������¼�
        this.addMouseMotionListener(l); //������껬���¼�	
        
        
		Timer timer = new Timer();  //������ʱ������
		int intervel = 10;  //ʱ����(ʱ���Ժ���Ϊ��λ)
		timer.schedule(new TimerTask() {		
			@Override
			public void run() {  //10��������һ��
				if(state==RUNNING) {
				enterAction();  //����(�л�+С�۷�)��һ��
				stepAction();   //��������һ��
				shootAction();  //�ӵ��볡
				outOfBoundsAction(); //ɾ��Խ��
				bangAction();  //�ӵ������(�л�+С�۷�)����ײ
				checkGameOverAction(); //�����Ϸ�Ƿ����
				}
				repaint();    //�ػ�---����paint()����
			}
		},intervel,intervel);
		
		
	}
	/**��дpaint()���� g:����*/
	public void paint(Graphics g) {
		g.drawImage(background,0,0,null);
		paintHero(g);
		paintFlyingObjects(g);
		paintBullets(g);
		paintScoreAndLife(g);
		paintState(g);
	}
	/**��Ӣ�ۻ����� */
	public void paintHero(Graphics g) {
		g.drawImage(hero.image,hero.x,hero.y,null);
	}
	/**������(�л�+С�۷�)���� */
	public void paintFlyingObjects(Graphics g) {
		for (int i = 0; i < flyings.length; i++) { //��������
			FlyingObject f = flyings[i];   //��ȡÿһ������
			g.drawImage(f.image,f.x,f.y,null);
			
		}
	}
	/**���ӵ����� */
	public void paintBullets(Graphics g) {
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			g.drawImage(b.image,b.x,b.y,null);
		}
	}
	/**���ֺͻ���*/
	public void paintScoreAndLife(Graphics g) {
		g.setColor(new Color(0xFF0000));
		g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,24));//����������ʽ
		g.drawString("SCORE:"+score, 10, 25);//����
		g.drawString("LIFE:"+hero.getLife(), 10, 45);
	}
	/**��״̬*/
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
	
	public static void main(String[] args) {  //main����
		JFrame frame = new JFrame("Flying");  //����
		ShootGame game = new ShootGame();  //���
		frame.add(game);  //�������ӵ�������
		
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
        game.action();
	}
    
	
}
