package testsocket.bean.message; /*
 * create by weikunpeng
 * 2018/5/20 18:35
 */

import sun.security.krb5.internal.Ticket;

public class AS_C {

    private String head;//包头

    private String Kc_tgs;
    private String IDtgs;
    private String TS2;
    private String LifeTime2;

    private TicketTGS ticketTGS;


    public AS_C() {

    }


   /* public boolean DealwithMessage(String messageC_AS){

        head=messageC_AS.substring(0,2);
        String message=messageC_AS.substring(2);

        String [] temp=message.split(" ");




    }*/

    public AS_C(String head, String kc_tgs, String IDtgs, String TS2, String lifeTime2, TicketTGS ticketTGS) {
        this.head = head;
        Kc_tgs = kc_tgs;
        this.IDtgs = IDtgs;
        this.TS2 = TS2;
        LifeTime2 = lifeTime2;
        this.ticketTGS = ticketTGS;
    }

    public String getAS_C(){
        return head+" "+Kc_tgs+" "+IDtgs+" "+TS2+" "+LifeTime2+" "+ticketTGS.getTicketTGS();
    }


    public String getKc_tgs() {
        return Kc_tgs;
    }

    public void setKc_tgs(String kc_tgs) {
        Kc_tgs = kc_tgs;
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
        return LifeTime2;
    }

    public void setLifeTime2(String lifeTime2) {
        LifeTime2 = lifeTime2;
    }

    public TicketTGS getTicketTGS() {
        return ticketTGS;
    }

    public void setTicketTGS(TicketTGS ticketTGS) {
        this.ticketTGS = ticketTGS;
    }
}
