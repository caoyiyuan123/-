package myGame01;
import java.awt.image.BufferedImage;
/**Ӣ�ۻ���---����*/
public class Hero extends FlyingObject{
	  private static final int INTERVAL = 30;//�ӵ�����Ӣ�ۻ��ĸ߶�
	  
      private int life;         //Ӣ�ۻ�����
      private int doubleFire;   //Ӣ�ۻ��Ļ���ֵ
      private BufferedImage[] images;  //ͼƬ����
      private int index;      //�л�ͼƬ
      
      /**Ӣ�ۻ��Ĺ��췽��*/
      public Hero() {
		image = ShootGame.hero0;     //Ӣ�ۻ���ͼƬ
		width = image.getWidth();   //Ӣ�ۻ��Ŀ�
		height = image.getHeight(); //Ӣ�ۻ��ĸ�
		x = 150;        //Ӣ�ۻ���x����
		y = 400;		//Ӣ�ۻ���y����  		
		life = 3;   //��Ϸ��������(Ĭ��Ϊ3)
		doubleFire = 0;  //Ӣ�ۻ��Ļ���ֵ(0:����Ϊ����;)
		images = new BufferedImage[]{ShootGame.hero0,ShootGame.hero1};//����images����		
		int index = 0;//Э���л�ͼƬ
	}
      
      
      /**
       * Ӣ�ۻ�ͼƬ�л�
       */
      public void step() {
    	  image = images[index++/10%images.length];//ÿ10�����л�һ��
      }
      
      
      /**
       * Ӣ�ۻ����ƶ�����(�������������ı�)
       * ���������ƶ�
       */
      public void move(int x,int y) { //������������
    	  this.x = x-this.width/2;
    	  this.y = y-this.height/2;
      }
      
      /**
       * Ӣ�ۻ������ӵ�
       * 
       */
      public Bullet[] shootBullet() {
    	  //���Ϊ˫������
    	  /**
    	   * xbλ�ӵ���x����
    	   * �ӵ�����Ӣ�ۻ�����Ϊ30
    	   */
    	  int xb = this.width/4;
    	  int yb = this.y - INTERVAL;  
    	  if (doubleFire>0) {  //����˫������
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
       * Ӣ�ۻ�Խ��
       */
      public boolean outofbound() {
    	  return false; 
      }
      
      /**
       * Ӣ�ۻ��ӻ���ֵ
       */
      public void addFire() {
    	  doubleFire += 40;
      }
      
      /**
       * Ӣ�ۻ�����
       */
      public void addLife() {
    	  life++;
      }
      /**
       * Ӣ�ۻ�����
       */
      public void abstractLife() {
    	  life--;
      }
      
      /**
       * ��ջ���ֵ
       */
      public void clearFire() {
    	  doubleFire = 0;
      }
      /**
       * Ӣ�ۻ�������
       */
      public int getLife() {
    	  return life;
      }
      
      /**
       * �жϵ����Ƿ���Ӣ�ۻ���ײ
       */
      public boolean hit(FlyingObject obj) {
    	int x1 = obj.x-this.width/2; //x1:���˵�x-1/2Ӣ�ۻ��Ŀ�
  		int x2 = obj.x+obj.width+this.width/2;//x2:���˵�x+���˵Ŀ�+1/2Ӣ�ۻ��Ŀ�
  		int y1 = obj.y-this.height/2;//y1:���˵�y-1/2Ӣ�ۻ��ĸ�
  		int y2 = obj.y+obj.height+this.height/2;//y2:���˵�y+���˵ĸ�+1/2Ӣ�ۻ��ĸ�
  		int x = this.x+this.width/2;
  		int y = this.y+this.height/2;
  		
  		return x>x1 &&x<x2
  				&&
  				y>y1 && y<y2;
    	  
    			  
      }


	
}
