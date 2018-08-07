package myGame01;
import java.awt.image.BufferedImage;
/**英雄机类---子类*/
public class Hero extends FlyingObject{
	  private static final int INTERVAL = 30;//子弹距离英雄机的高度
	  
      private int life;         //英雄机的命
      private int doubleFire;   //英雄机的火力值
      private BufferedImage[] images;  //图片数组
      private int index;      //切换图片
      
      /**英雄机的构造方法*/
      public Hero() {
		image = ShootGame.hero0;     //英雄机的图片
		width = image.getWidth();   //英雄机的宽
		height = image.getHeight(); //英雄机的高
		x = 150;        //英雄机的x坐标
		y = 400;		//英雄机的y坐标  		
		life = 3;   //游戏机的命数(默认为3)
		doubleFire = 0;  //英雄机的火力值(0:代表为单发;)
		images = new BufferedImage[]{ShootGame.hero0,ShootGame.hero1};//创建images数组		
		int index = 0;//协助切换图片
	}
      
      
      /**
       * 英雄机图片切换
       */
      public void step() {
    	  image = images[index++/10%images.length];//每10毫秒切换一次
      }
      
      
      /**
       * 英雄机的移动方法(坐标随着鼠标而改变)
       * 随着鼠标而移动
       */
      public void move(int x,int y) { //传入的鼠标坐标
    	  this.x = x-this.width/2;
    	  this.y = y-this.height/2;
      }
      
      /**
       * 英雄机发射子弹
       * 
       */
      public Bullet[] shootBullet() {
    	  //如果为双倍火力
    	  /**
    	   * xb位子弹的x坐标
    	   * 子弹距离英雄机上面为30
    	   */
    	  int xb = this.width/4;
    	  int yb = this.y - INTERVAL;  
    	  if (doubleFire>0) {  //代表双倍火力
    		  Bullet[] bt = new Bullet[2];
    		  bt[0] = new Bullet(this.x+xb, yb);
    		  bt[1] = new Bullet(this.x+xb*3, yb);
    		  return bt;
		}else {
			  Bullet[] bb = new Bullet[1];
			  bb[0] = new Bullet(this.x+xb*2, yb);
			  return bb;
		}
    	 
    	  
      }
      /**
       * 英雄机越界
       */
      public boolean outofbound() {
    	  return false; 
      }
      
      /**
       * 英雄机加火力值
       */
      public void addFire() {
    	  doubleFire += 40;
      }
      
      /**
       * 英雄机加命
       */
      public void addLife() {
    	  life++;
      }
      /**
       * 英雄机减命
       */
      public void abstractLife() {
    	  life--;
      }
      
      /**
       * 清空火力值
       */
      public void clearFire() {
    	  doubleFire = 0;
      }
      /**
       * 英雄机的命数
       */
      public int getLife() {
    	  return life;
      }
      
      /**
       * 判断敌人是否与英雄机相撞
       */
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
