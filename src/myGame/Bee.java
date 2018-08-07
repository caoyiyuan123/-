package myGame;

import java.util.Random;

/**蜜蜂(子类) */
public class Bee extends FlyingObject implements Award{
	private int xSpeed = 1;   //蜜蜂的移动速度(x方向)
	private int ySpeed = 2;   //蜜蜂的移动速度(y方向)
	private int awardType;    //打掉蜜蜂获得的奖励
	
	/**构造方法 */
	public Bee() {
		 image = ShootGame.bee;
		   width = image.getWidth();  //getWidth()方法的作用是获得图片的宽
		   height = image.getHeight(); //getHeight()的方法是获得图片的高
		   Random rand = new Random();
		   x = rand.nextInt(ShootGame.WIDTH-this.width); //x:0到(窗口宽-蜜蜂宽)之内的随机数
		   y = -this.height;  //y:负的蜜蜂的高
		  
		   awardType = rand.nextInt(2);//0或者1的随机数
	}
	
	/**打掉蜜蜂获得奖励的方法 */
 	public int getType() {
		return awardType;
	}
 	
 	/**重写step()方法*/
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
    
    /** 重写outOfBounds方法*/
	public boolean outOfBounds() {
		return this.y>=ShootGame.HEIGHT;//小蜜蜂越界
	}

	
}
