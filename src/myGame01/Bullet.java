package myGame01;
/**�ӵ���---����*/
public class Bullet extends FlyingObject{
       private int speed = 3;  //�ӵ����ٶ�
       
       /**���췽�� x:����Ӣ�ۻ�λ��ȷ�� y:����Ӣ�ۻ�λ��ȷ�� */
       public Bullet(int x,int y) {
		image = ShootGame.bullet;
		width = image.getWidth();
		height = image.getHeight();
		this.x = x;
		this.y = y;
		
	}

	@Override
	public void step() {
		y -= speed;
		
	}
	
	/**
	 * �ӵ�Խ��
	 */
	public boolean outofbound() {
		return y<-this.height;
	}
	
	
}
