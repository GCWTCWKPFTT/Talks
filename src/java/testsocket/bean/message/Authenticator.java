package testsocket.bean.message;

import java.io.UnsupportedEncodingException;

import testsocket.util.DES;

public class Authenticator {
	private String IDc;
    private String ADc;
    private	long TS;    	//注意c向TGS和V发的TS不一样
    
    public Authenticator(){

    }
    public Authenticator(String IDc,String ADc,long TS){
        this.IDc=IDc;
        this.ADc=ADc;
        this.TS=TS;
    }
    
    //处理解密后的message
    public boolean DealwithMessage(String authenticator_C,String key) throws UnsupportedEncodingException {
    	String authenticator_M = DES.des(authenticator_C, key, 2);
    	String temp[] = authenticator_M.split(" ");
    	if(temp.length != 3) {
    		System.out.println("auth c_tgs 长度错误");
    		return false;
    	}
        this.IDc=temp[0];
        this.ADc=temp[1];
        this.TS=Long.parseLong(temp[2]);
    	return true;
    }
    
    //返回加密前String
    public String getAuthenticator() {
    	return IDc+" "+ADc+" "+TS;
    }
    
    
	public String getIDc() {
		return IDc;
	}
	public String getADc() {
		return ADc;
	}
	public long getTS() {
		return TS;
	}
    
    
}
