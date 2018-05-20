package testsocket.bean.message;
/*
 * create by weikunpeng
 * 2018/5/20 18:38
 */

public class TicketTGS {




    private String Kc_tgs;
    private String IDc;
    private String ADc;
    private  String IDtgs;
    private  String TS2;
    private  String lifeTime2;


    public TicketTGS() {

    }

    public TicketTGS(String kc_tgs, String IDc, String ADc, String IDtgs, String TS2, String lifeTime2) {
        Kc_tgs = kc_tgs;
        this.IDc = IDc;
        this.ADc = ADc;
        this.IDtgs = IDtgs;
        this.TS2 = TS2;
        this.lifeTime2 = lifeTime2;
    }

    public String getTicketTGS(){

        return Kc_tgs+" "+IDc+" "+ADc+" "+IDtgs+" "+TS2+" "+lifeTime2;

    }


    public String getKc_tgs() {
        return Kc_tgs;
    }

    public void setKc_tgs(String kc_tgs) {
        Kc_tgs = kc_tgs;
    }

    public String getIDc() {
        return IDc;
    }

    public void setIDc(String IDc) {
        this.IDc = IDc;
    }

    public String getADc() {
        return ADc;
    }

    public void setADc(String ADc) {
        this.ADc = ADc;
    }

    public String getIDtgs() {
        return IDtgs;
    }

    public void setIDtgs(String IDtgs) {
        this.IDtgs = IDtgs;
    }

    public String getTS2() {
        return TS2;
    }

    public void setTS2(String TS2) {
        this.TS2 = TS2;
    }

    public String getLifeTime2() {
        return lifeTime2;
    }

    public void setLifeTime2(String lifeTime2) {
        this.lifeTime2 = lifeTime2;
    }



}
