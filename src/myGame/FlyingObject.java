package myGame;
import java.awt.image.BufferedImage;

/**飞行物类 (父类)*/

public abstract class FlyingObject {  // 父类
      protected BufferedImage image;   //图片
      protected int width;             //宽度
      protected int height;            //高度
      protected int x;                 //x坐标
      protected int y;                 //y坐标
      
      /** 飞行物走一步 */
      public abstract void step();
    	  
      /**检查是否出界 返回true表示已越界*/
      public abstract boolean outOfBounds();
    	  
      /**敌人被子弹射击*/
      public boolean shootBy(Bullet bullet) {
    	  int x1 = this.x;  //敌人的x
    	  int x2 = this.x+this.width; //敌人的x+敌人的宽
    	  int y1 = this.y;  //敌人的y
    	  int y2 = this.y+this.height;//敌人的y+敌人的高
    	  int x = bullet.x; //x:子弹的x
    	  int y = bullet.y; //y：找点的y
    	  
    	  return x>x1-bullet.width && x<x2
    			  	&&
		  y>y1-bullet.height && y<y2;
    	  
    	  
      }
      
    
      
      
      
      
      
}
