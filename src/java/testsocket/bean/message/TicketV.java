package testsocket.bean.message; /*
 * create by weikunpeng
 * 2018/5/27 15:45
 */

public class TicketV {

    private String Kc_v;
    private String IDc;
    private String ADc;
    private String IDv;
    private long TS4;
    private long Lifetime4;

    public TicketV(){

    }
    public TicketV(String kc_v,String IDc,String ADc,String IDv,long TS4,long Lifetime4){
        this.Kc_v=kc_v;
        this.IDc=IDc;
        this.ADc=ADc;
        this.IDv=IDv;
        this.TS4=TS4;
        this.Lifetime4=Lifetime4;
    }
    public String getTicketV(){
        return Kc_v+" "+IDc+" "+ADc+" "+IDv+" "+Long.toString(TS4)+" "+Long.toString(Lifetime4);
    }

    public String getKc_v() {
        return Kc_v;
    }
    public void setKc_v(String kc_v) {
        Kc_v = kc_v;
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
    public String getIDv() {
        return IDv;
    }
    public void setIDv(String iDv) {
        IDv = iDv;
    }
    public long getTS4() {
        return TS4;
    }
    public void setTS4(long tS4) {
        TS4 = tS4;
    }
    public long getLifetime4() {
        return Lifetime4;
    }
    public void setLifetime4(long lifetime4) {
        Lifetime4 = lifetime4;
    }
}
