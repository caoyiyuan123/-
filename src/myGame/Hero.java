package myGame;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Hero extends FlyingObject{
	private int life;
	private int doubleFire;
	private BufferedImage[] images;//图片数组
	private int index;  //协助图片切换
	
	/**构造方法 */
	public Hero() {
		   image = ShootGame.hero0;
		   width = image.getWidth();  //getWidth()方法的作用是获得图片的宽
		   height = image.getHeight(); //getHeight()的方法是获得图片的高
		   Random rand = new Random();
		   x = 150;  //x:固定的值
		   y = 400;  //y:固定的值
		   life = 3; //默认3条名
		   doubleFire = 0; //火力值为0，即为单倍火力
		   //两张图片hero0和hero1
		   images = new BufferedImage[]{ShootGame.hero0,ShootGame.hero1};
		   index = 0;  //协助切换
	}
	/**重写step()方法*/
	public void step() {  //10毫秒走一次
		image = images[index++/10%images.length];//每100毫秒切换一次
	}
	
	/**英雄机发射子弹*/
	public Bullet[] shoot() {
		int xStep = this.width/4;//1/4英雄机得宽
		int yStep = 20;
		if (doubleFire>0) {
			Bullet[] bs = new Bullet[2];  //双倍火力
			bs[0] = new Bullet(this.x+1*xStep, this.y-yStep);
			bs[1] = new Bullet(this.x+3*xStep, this.y-yStep);
			doubleFire -= 2; //发射一次火力值减2
			return bs;
		} else {
            Bullet[] bs = new Bullet[1];  //单倍火力
            bs[0] = new Bullet(this.x+2*xStep, this.y-yStep);
            return bs;
		}
	}
	
	/**英雄机随着鼠标移动 x:x的坐标 y:y的坐标*/
	public void moveTo(int x,int y) {
		this.x = x - this.width/2;
		this.y = y - this.height/2;
		
	}
	
	/** 重写outOfBounds方法*/
	public boolean outOfBounds() {
		return false; //永不越界
	}
	
	/**英雄机增加命*/
	public void addLife() {
		life++;
	}
	
	public int getLife() {
		return life;  //返回命数
	}
	
	/**减命*/
	public void subtractLife() {
		life--;
	}
	
	/**清空火力*/
	public void clearDoubleFire() {
		doubleFire = 0; //火力值归零
	}
	
	/** 英雄机增加火力值*/
	public void addDoubleFire() {
		doubleFire+=40;
	}
	
	/**英雄机撞敌人 this:英雄机 obj:敌人*/
	public boolean hit(FlyingObject obj) {
		int x1 = obj.x-this.width/2; //x1:敌人的x-1/2英雄机的宽
		int x2 = obj.x+obj.width+this.width/2;//x2:敌人的x+敌人的宽+1/2英雄机的宽
		int y1 = obj.y-this.height/2;//y1:敌人的y-1/2英雄机的高
		int y2 = obj.y+obj.height+this.height/2;//y2:敌人的y+敌人的高+1/2英雄机的高
		int x = this.x+this.width/2;
		int y = this.y+this.height/2;
		return x>x1 &&x<x2
				&&
				y>y1 && y<y2;
	}
}

