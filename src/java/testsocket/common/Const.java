package testsocket.common; 

/* head;
 *
 * 1-30;服务数据包
 * 1c-AS
 * 2AS-c
 * 3c-tgs
 * 4tgs-c
 * 5c-v
 * 6v-c
 * 7as_c 错误信息
 * 8tgs_c 错误信息
 * 9v_c 错误信息
 * 
 * 31-99；应用数据包
 * 
 */
//message 信息 不包括报头

/*
weikunpeng 123
wtc 456
fangtiantian 789
gaochao 123
 */

public class Const {
//默认 固定数据

    public static final String ID_TGS="tgs";
    public static final String ID_V="v";
    
    public static final String IP_AS = "127.0.0.1";
    public static final String IP_TGS = "127.0.0.1";
    public static final String IP_V = "127.0.0.1";
    
    public static final int PORT_AS = 9991;
    public static final int PORT_TGS = 9992;
    public static final int PORT_V = 9993;
    
    public static final String kv = "12345678";
	public static final String ktgs = "12345678";

    
}
