package myGame;
import java.awt.image.BufferedImage;

/**�������� (����)*/

public abstract class FlyingObject {  // ����
      protected BufferedImage image;   //ͼƬ
      protected int width;             //���
      protected int height;            //�߶�
      protected int x;                 //x����
      protected int y;                 //y����
      
      /** ��������һ�� */
      public abstract void step();
    	  
      /**����Ƿ���� ����true��ʾ��Խ��*/
      public abstract boolean outOfBounds();
    	  
      /**���˱��ӵ����*/
      public boolean shootBy(Bullet bullet) {
    	  int x1 = this.x;  //���˵�x
    	  int x2 = this.x+this.width; //���˵�x+���˵Ŀ�
    	  int y1 = this.y;  //���˵�y
    	  int y2 = this.y+this.height;//���˵�y+���˵ĸ�
    	  int x = bullet.x; //x:�ӵ���x
    	  int y = bullet.y; //y���ҵ��y
    	  
    	  return x>x1-bullet.width && x<x2
    			  	&&
		  y>y1-bullet.height && y<y2;
    	  
    	  
      }
      
    
      
      
      
      
      
}
