package myGame01;

import java.util.Random;

import myGame.ShootGame;

/**蜜蜂类---子类*/
public class Bee extends FlyingObject implements Award {
        private int xspeed = 1;  //此处的变量为什么不能改为静态变量
        private int yspeed = 2;
        private int awardType;
        
        /**构造方法*/
         public Bee() {
			image = ShootGame.bee;      //小蜜蜂的图片
			width = image.getWidth();   //小蜜蜂的宽
			height = image.getHeight();	//小蜜蜂的
			Random rand = new Random(); 
			x = rand.nextInt(ShootGame.WIDTH-this.width);//小蜜蜂的x坐标
			y = -this.height;  //y坐标的坐标
			
		
			awardType = rand.nextInt(2);//0和1的随机数
		}
		@Override
		public int getType() {
			
			return awardType;
		}
		
		/**
		 * 小蜜蜂走一步
		 * 实际上是小蜜蜂坐标的改变
		 */
		 public void step() {
	    	 x += xspeed;
	    	 y += yspeed;
	    	 if (x>=ShootGame.WIDTH-this.width) {
				xspeed = -1;
			}
	    	 if (x<=0) {
				xspeed = 1;			
			}
	      }
		 
		 /**
		  * 小蜜蜂越界
		  */
		 public boolean outofbound() {
	    	 return y>ShootGame.HEIGHT;
	     }
		 
		
		
}
