package myGame;
import java.util.Random;//����random��
/** �л�(����) */
public class Airplane extends FlyingObject implements Enemy{ //�̳з����ࡢʵ�ֵл���
	private int speed = 2;     //���������е�����(�ƶ��ٶ�)
	 
	/**���췽��  */
	public Airplane() {
	   image = ShootGame.airplane;
	   width = image.getWidth();  //getWidth()�����������ǻ��ͼƬ�Ŀ�
	   height = image.getHeight(); //getHeight()�ķ����ǻ��ͼƬ�ĸ�
	   Random rand = new Random();
	   x = rand.nextInt(ShootGame.WIDTH-this.width);
	   y = -this.height;
	   
	}
	
	/**��дgetScore���� */
	public int getScore() {
		return 5;   //���һ���л���5��
	}
	
	/**��дstep()����*/
	public void step() {
		y+=speed;  //y+(����)
	}
	
	/** ��дoutOfBounds����*/
	public boolean outOfBounds() {
		return this.y>=ShootGame.HEIGHT;//�л�Խ��
	}

	
    
}
