package myGame01;

import java.util.Random;

/**�л���---����*/
public class Airplane extends FlyingObject implements Enemy{ //ʵ�ַ����࣬�̳ел���
      private static int speed = 2;  //�л�(�������е�����)���ƶ��ٶ�
     
      /**���췽��
       * ��ʼ����Ա����
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
       * �л���һ��
       */
     public void step() {
    	 y += speed;
     }
     
     /**
      * �л�Խ��
      */
     public boolean outofbound() {
    	 return y>ShootGame.HEIGHT; //����true��ʾԽ��
     }

	@Override
	public int getScore() {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
