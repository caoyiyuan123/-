package myGame;

import java.util.Random;

/**�ӵ�(����) */ 
public class Bullet extends FlyingObject {
     private int speed = 2;   //������ƶ��ٶ�
     
     /**���췽�� x:����Ӣ�ۻ�λ��ȷ�� y:����Ӣ�ۻ�λ��ȷ�� */
     public Bullet(int x,int y) { //��������ʹ���������
       image = ShootGame.bullet;
  	   width = image.getWidth();  //getWidth()�����������ǻ��ͼƬ�Ŀ�
  	   height = image.getHeight(); //getHeight()�ķ����ǻ��ͼƬ�ĸ�
  	   this.x = x; //�ӵ���x
  	   this.y = y; //�ӵ���y
	}
     /**�ӵ���һ��*/
 	public void step() {
 		y -= speed;  //y-(����)
 	}
 	
 	/**����Ƿ���磬����true��ʾ��Խ��*/
	public boolean outOfBounds() {
		return this.y<=-this.height; //�ӵ�Խ��
	}
}
