package myGame;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Hero extends FlyingObject{
	private int life;
	private int doubleFire;
	private BufferedImage[] images;//ͼƬ����
	private int index;  //Э��ͼƬ�л�
	
	/**���췽�� */
	public Hero() {
		   image = ShootGame.hero0;
		   width = image.getWidth();  //getWidth()�����������ǻ��ͼƬ�Ŀ�
		   height = image.getHeight(); //getHeight()�ķ����ǻ��ͼƬ�ĸ�
		   Random rand = new Random();
		   x = 150;  //x:�̶���ֵ
		   y = 400;  //y:�̶���ֵ
		   life = 3; //Ĭ��3����
		   doubleFire = 0; //����ֵΪ0����Ϊ��������
		   //����ͼƬhero0��hero1
		   images = new BufferedImage[]{ShootGame.hero0,ShootGame.hero1};
		   index = 0;  //Э���л�
	}
	/**��дstep()����*/
	public void step() {  //10������һ��
		image = images[index++/10%images.length];//ÿ100�����л�һ��
	}
	
	/**Ӣ�ۻ������ӵ�*/
	public Bullet[] shoot() {
		int xStep = this.width/4;//1/4Ӣ�ۻ��ÿ�
		int yStep = 20;
		if (doubleFire>0) {
			Bullet[] bs = new Bullet[2];  //˫������
			bs[0] = new Bullet(this.x+1*xStep, this.y-yStep);
			bs[1] = new Bullet(this.x+3*xStep, this.y-yStep);
			doubleFire -= 2; //����һ�λ���ֵ��2
			return bs;
		} else {
            Bullet[] bs = new Bullet[1];  //��������
            bs[0] = new Bullet(this.x+2*xStep, this.y-yStep);
            return bs;
		}
	}
	
	/**Ӣ�ۻ���������ƶ� x:x������ y:y������*/
	public void moveTo(int x,int y) {
		this.x = x - this.width/2;
		this.y = y - this.height/2;
		
	}
	
	/** ��дoutOfBounds����*/
	public boolean outOfBounds() {
		return false; //����Խ��
	}
	
	/**Ӣ�ۻ�������*/
	public void addLife() {
		life++;
	}
	
	public int getLife() {
		return life;  //��������
	}
	
	/**����*/
	public void subtractLife() {
		life--;
	}
	
	/**��ջ���*/
	public void clearDoubleFire() {
		doubleFire = 0; //����ֵ����
	}
	
	/** Ӣ�ۻ����ӻ���ֵ*/
	public void addDoubleFire() {
		doubleFire+=40;
	}
	
	/**Ӣ�ۻ�ײ���� this:Ӣ�ۻ� obj:����*/
	public boolean hit(FlyingObject obj) {
		int x1 = obj.x-this.width/2; //x1:���˵�x-1/2Ӣ�ۻ��Ŀ�
		int x2 = obj.x+obj.width+this.width/2;//x2:���˵�x+���˵Ŀ�+1/2Ӣ�ۻ��Ŀ�
		int y1 = obj.y-this.height/2;//y1:���˵�y-1/2Ӣ�ۻ��ĸ�
		int y2 = obj.y+obj.height+this.height/2;//y2:���˵�y+���˵ĸ�+1/2Ӣ�ۻ��ĸ�
		int x = this.x+this.width/2;
		int y = this.y+this.height/2;
		return x>x1 &&x<x2
				&&
				y>y1 && y<y2;
	}
}

