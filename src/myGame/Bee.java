package myGame;

import java.util.Random;

/**�۷�(����) */
public class Bee extends FlyingObject implements Award{
	private int xSpeed = 1;   //�۷���ƶ��ٶ�(x����)
	private int ySpeed = 2;   //�۷���ƶ��ٶ�(y����)
	private int awardType;    //����۷��õĽ���
	
	/**���췽�� */
	public Bee() {
		 image = ShootGame.bee;
		   width = image.getWidth();  //getWidth()�����������ǻ��ͼƬ�Ŀ�
		   height = image.getHeight(); //getHeight()�ķ����ǻ��ͼƬ�ĸ�
		   Random rand = new Random();
		   x = rand.nextInt(ShootGame.WIDTH-this.width); //x:0��(���ڿ�-�۷��)֮�ڵ������
		   y = -this.height;  //y:�����۷�ĸ�
		  
		   awardType = rand.nextInt(2);//0����1�������
	}
	
	/**����۷��ý����ķ��� */
 	public int getType() {
		return awardType;
	}
 	
 	/**��дstep()����*/
    public void step() {
		x += xSpeed;
		y += ySpeed;
		if (x>=ShootGame.WIDTH-this.width) { //x-
			xSpeed = -1;
		}
		if (x<=0) {    //x+
			xSpeed = 1;
		}
	}
    
    /** ��дoutOfBounds����*/
	public boolean outOfBounds() {
		return this.y>=ShootGame.HEIGHT;//С�۷�Խ��
	}

	
}
