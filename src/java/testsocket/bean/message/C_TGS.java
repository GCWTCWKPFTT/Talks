package testsocket.bean.message; /*
 * create by weikunpeng
 * 2018/5/27 15:24
 */

public class C_TGS {

    private String head;
    private String IDv;
    private TicketTGS ticketTGS;
    private Authenticator authenticator;

    public C_TGS() {

    }

    public C_TGS(String head, String IDv, TicketTGS TicketTGS, Authenticator a) {
        this.head = head;
        this.IDv = IDv;
        this.ticketTGS = TicketTGS;
        this.authenticator = a;
    }

    public boolean DealWithMessageC_TGS(String messageC_TGS){
        head=messageC_TGS.substring(0,2);
        String message=messageC_TGS.substring(3);
        //System.out.println(message);

        String [] temp=message.split(" ");
        if(temp.length!=10) return false;

        IDv=temp[0];

        ticketTGS=new TicketTGS(temp[1],temp[2],temp[3],temp[4],Long.parseLong(temp[5]),Long.parseLong(temp[6]));
        authenticator=new Authenticator(temp[7],temp[8],Long.parseLong(temp[9]));

        return true;
    }

    public String getC_TGS(){

        return head+" "+IDv+" "+ticketTGS.getTicketTGS()+" "+authenticator.getAuthenticator();

    }


    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getIDv() {
        return IDv;
    }

    public void setIDv(String IDv) {
        this.IDv = IDv;
    }

    public TicketTGS getTicketTGS() {
        return ticketTGS;
    }

    public void setTicketTGS(TicketTGS ticketTGS) {
        ticketTGS = ticketTGS;
    }

    public Authenticator getAuthenticator() {
        return authenticator;
    }

    public void setAuthenticator(Authenticator authenticator) {
        authenticator = authenticator;
    }
}
