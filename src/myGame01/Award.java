package myGame01;
/**奖励---接口*/
public interface Award {
         public int DOUBLE_FIRE = 0; //默认子弹为单发，大于0位双发
         public int LIFE = 1;       //奖励1条名
         public int getType(); 
        
}
