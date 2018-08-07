package myGame01;

import java.util.Random;

import myGame.ShootGame;

/**�۷���---����*/
public class Bee extends FlyingObject implements Award {
        private int xspeed = 1;  //�˴��ı���Ϊʲô���ܸ�Ϊ��̬����
        private int yspeed = 2;
        private int awardType;
        
        /**���췽��*/
         public Bee() {
			image = ShootGame.bee;      //С�۷��ͼƬ
			width = image.getWidth();   //С�۷�Ŀ�
			height = image.getHeight();	//С�۷��
			Random rand = new Random(); 
			x = rand.nextInt(ShootGame.WIDTH-this.width);//С�۷��x����
			y = -this.height;  //y���������
			
		
			awardType = rand.nextInt(2);//0��1�������
		}
		@Override
		public int getType() {
			
			return awardType;
		}
		
		/**
		 * С�۷���һ��
		 * ʵ������С�۷�����ĸı�
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
		  * С�۷�Խ��
		  */
		 public boolean outofbound() {
	    	 return y>ShootGame.HEIGHT;
	     }
		 
		
		
}
