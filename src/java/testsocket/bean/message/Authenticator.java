package testsocket.bean.message; /*
 * create by weikunpeng
 * 2018/5/27 15:25
 */

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
    public String getAuthenticator(){
        return IDc+" "+ADc+" "+Long.toString(TS);
    }
    public String getIDc() {
        return IDc;
    }
    public void setIDc(String iDc) {
        IDc = iDc;
    }
    public String getADc() {
        return ADc;
    }
    public void setADc(String aDc) {
        ADc = aDc;
    }
    public long getTS() {
        return TS;
    }
    public void setTS(long tS) {
        TS = tS;
    }
}
