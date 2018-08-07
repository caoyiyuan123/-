package myGame01;

import java.awt.image.BufferedImage;

/**飞行物类---父类*/
public abstract class FlyingObject {
	    protected BufferedImage image;  //图片
        protected int x;      //x坐标
        protected int y;      //y坐标
        protected int width;  //宽度
        protected int height; //高度
	
		public abstract void step();
		
		//越界
		public abstract boolean outofbound();
		
		/**
		 * 敌人是否被子弹撞上
		 */
		public boolean shootBy(Bullet bullet) {
			int x = bullet.x;  //子弹的x坐标
			int y = bullet.y;  //子弹的y坐标
			int x1 = this.x;   //敌人的x
			int x2 = x1+this.width;
			int y1 = this.y;
			int y2 = this.y+this.height;
		
			return x>x1-bullet.width && x<x2
	    			  &&
	    			  y>y1-bullet.height && y<y2;
		}


}
