package testsocket.bean.message;

import java.io.UnsupportedEncodingException;

import testsocket.util.DES;

public class V_C {
	private static final String head = "06"; //包头的判断在处理之前。
	
	private long TS;
	
	public V_C() {
		
	}
	public boolean DealwithMessage(String messageV_C,String Kcv) throws UnsupportedEncodingException{
		messageV_C = messageV_C.substring(3);
		TS = Long.parseLong(DES.des(messageV_C, Kcv, 2));
		return true;
	}
	
	public static String getHead() {
		return head;
	}
	public long getTS() {
		return TS;
	}
	
}
