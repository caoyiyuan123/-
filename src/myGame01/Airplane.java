package myGame01;

import java.util.Random;

/**敌机类---子类*/
public class Airplane extends FlyingObject implements Enemy{ //实现飞行类，继承敌机类
      private static int speed = 2;  //敌机(子类特有的属性)的移动速度
     
      /**构造方法
       * 初始化成员变量
       */ 
      public Airplane() {
    	  image = ShootGame.airplane;
    	  width = image.getWidth();
    	  height = image.getHeight();
    	  Random rand = new Random();
    	  x = rand.nextInt(ShootGame.WIDTH-this.width);
    	  y = -this.height;
      }
      
      /**
       * 敌机走一步
       */
     public void step() {
    	 y += speed;
     }
     
     /**
      * 敌机越界
      */
     public boolean outofbound() {
    	 return y>ShootGame.HEIGHT; //返回true表示越界
     }

	@Override
	public int getScore() {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
