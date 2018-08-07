package myGame;
import java.util.Random;//引入random类
/** 敌机(子类) */
public class Airplane extends FlyingObject implements Enemy{ //继承飞行类、实现敌机类
	private int speed = 2;     //子类所特有的属性(移动速度)
	 
	/**构造方法  */
	public Airplane() {
	   image = ShootGame.airplane;
	   width = image.getWidth();  //getWidth()方法的作用是获得图片的宽
	   height = image.getHeight(); //getHeight()的方法是获得图片的高
	   Random rand = new Random();
	   x = rand.nextInt(ShootGame.WIDTH-this.width);
	   y = -this.height;
	   
	}
	
	/**重写getScore方法 */
	public int getScore() {
		return 5;   //打掉一个敌机得5分
	}
	
	/**重写step()方法*/
	public void step() {
		y+=speed;  //y+(向下)
	}
	
	/** 重写outOfBounds方法*/
	public boolean outOfBounds() {
		return this.y>=ShootGame.HEIGHT;//敌机越界
	}

	
    
}
