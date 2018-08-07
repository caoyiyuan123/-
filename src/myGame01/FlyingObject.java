package myGame01;

import java.awt.image.BufferedImage;

/**��������---����*/
public abstract class FlyingObject {
	    protected BufferedImage image;  //ͼƬ
        protected int x;      //x����
        protected int y;      //y����
        protected int width;  //���
        protected int height; //�߶�
	
		public abstract void step();
		
		//Խ��
		public abstract boolean outofbound();
		
		/**
		 * �����Ƿ��ӵ�ײ��
		 */
		public boolean shootBy(Bullet bullet) {
			int x = bullet.x;  //�ӵ���x����
			int y = bullet.y;  //�ӵ���y����
			int x1 = this.x;   //���˵�x
			int x2 = x1+this.width;
			int y1 = this.y;
			int y2 = this.y+this.height;
		
			return x>x1-bullet.width && x<x2
	    			  &&
	    			  y>y1-bullet.height && y<y2;
		}


}
