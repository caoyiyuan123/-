package myGame01;
/**子弹类---子类*/
public class Bullet extends FlyingObject{
       private int speed = 3;  //子弹的速度
       
       /**构造方法 x:根据英雄机位置确定 y:根据英雄机位置确定 */
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
	 * 子弹越界
	 */
	public boolean outofbound() {
		return y<-this.height;
	}
	
	
}
