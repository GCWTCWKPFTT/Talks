package testsocket.bean.message; /*
 * create by weikunpeng
 * 2018/5/27 15:45
 */

public class TGS_C {
    private String head;
    private String Kc_v;
    private String IDv;
    private long TS4;
    private TicketV ticketV;

    public TGS_C(){

    }
    public TGS_C(String head,String kc_v,String IDv,long TS4,TicketV ticketV){
        this.head=head;
        this.Kc_v=kc_v;
        this.IDv=IDv;
        this.TS4=TS4;
        this.ticketV=ticketV;
    }


    public boolean DealWithMessageTGS_C(String messageTGS_C){
        head=messageTGS_C.substring(0,2);
        String message=messageTGS_C.substring(3);
        //System.out.println(message);

        String [] temp=message.split(" ");
        if(temp.length!=9) return false;


        Kc_v=temp[0];
        IDv=temp[1];
        TS4=Long.parseLong(temp[2]);
        ticketV=new TicketV(temp[3],temp[4],temp[5],temp[6],Long.parseLong(temp[7]),Long.parseLong(temp[8]));

        return true;
    }

    public String getTGS_C(){
        return head+" "+Kc_v+" "+IDv+" "+Long.toString(TS4)+" "+ticketV.getTicketV();
    }
    public String getHead() {
        return head;
    }
    public void setHead(String head) {
        this.head = head;
    }
    public String getKc_v() {
        return Kc_v;
    }
    public void setKc_v(String kc_v) {
        Kc_v = kc_v;
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
    public TicketV getticketV() {
        return ticketV;
    }
    public void setTicketV(TicketV ticketV) {
        ticketV = ticketV;
    }
}
