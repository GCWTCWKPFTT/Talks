package testsocket.bean.message; /*
 * create by weikunpeng
 * 2018/5/20 18:35
 */

import sun.security.krb5.internal.Ticket;

public class AS_C {

    private String head;//包头

    private String Kc_tgs;
    private String IDtgs;
    private long TS2;
    private long LifeTime2;

    private TicketTGS ticketTGS;


    public AS_C() {

    }


    public boolean DealwithMessage(String messageC_AS){

        head=messageC_AS.substring(0,2);
        String message=messageC_AS.substring(3);
        //System.out.println(message);

        String [] temp=message.split(" ");
        if(temp.length!=10) {
            System.out.println("处理消息失败！");
            return false;
        }
        Kc_tgs=temp[0];
        IDtgs=temp[1];
        TS2=Long.parseLong(temp[2]);
        LifeTime2=Long.parseLong(temp[3]);
        ticketTGS=new TicketTGS(temp[4],temp[5],temp[6],temp[7],Long.parseLong(temp[8]),Long.parseLong(temp[9]));

        return true;






    }

    public AS_C(String head, String kc_tgs, String IDtgs, long TS2, long lifeTime2, TicketTGS ticketTGS) {
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

    public long getTS2() {
        return TS2;
    }

    public void setTS2(long TS2) {
        this.TS2 = TS2;
    }

    public long getLifeTime2() {
        return LifeTime2;
    }

    public void setLifeTime2(long lifeTime2) {
        LifeTime2 = lifeTime2;
    }

    public TicketTGS getTicketTGS() {
        return ticketTGS;
    }

    public void setTicketTGS(TicketTGS ticketTGS) {
        this.ticketTGS = ticketTGS;
    }
}
