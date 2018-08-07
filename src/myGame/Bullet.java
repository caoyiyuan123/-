package myGame;

import java.util.Random;

/**子弹(子类) */ 
public class Bullet extends FlyingObject {
     private int speed = 2;   //子类的移动速度
     
     /**构造方法 x:根据英雄机位置确定 y:根据英雄机位置确定 */
     public Bullet(int x,int y) { //参数可以使方法更灵活
       image = ShootGame.bullet;
  	   width = image.getWidth();  //getWidth()方法的作用是获得图片的宽
  	   height = image.getHeight(); //getHeight()的方法是获得图片的高
  	   this.x = x; //子弹的x
  	   this.y = y; //子弹的y
	}
     /**子弹走一步*/
 	public void step() {
 		y -= speed;  //y-(向上)
 	}
 	
 	/**检查是否出界，返回true表示已越界*/
	public boolean outOfBounds() {
		return this.y<=-this.height; //子弹越界
	}
}
