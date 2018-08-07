package myGame;
/** 奖励接口 */
public interface Award {      //接口(父类)-----部分子类实现
    public int DOUBLE_FIRE = 0;  // 默认单发子弹，大于0位双发子弹
    public int LIFE = 1;         //可以奖励一条命
    
    public int getType();       //获得奖励的方式
}
