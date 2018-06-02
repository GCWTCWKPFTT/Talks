package testsocket.bean.message;

import java.io.UnsupportedEncodingException;

public class TGS_C {
	private static final String head = "04";
	
    private String Kc_v;
    private String IDv;
    private long TS4;
    private String ticketV;
    
    public TGS_C() {
    	
    }
    public TGS_C(String Kc_v,String IDv,long TS4,String ticketV) {
    	this.Kc_v = Kc_v;
    	this.IDv = IDv;
    	this.TS4 = TS4;
    	this.ticketV = ticketV;
    }
    public boolean DealwithMessage(String messageC_AS) throws UnsupportedEncodingException {
    	String[] temp = messageC_AS.split(" ");
		if (temp.length != 4) {
			System.out.println("deal c_tgs wrong");
			return false;
		}
		Kc_v = temp[0];
	    IDv = temp[1];
	    TS4 = Long.parseLong(temp[2]);
	    ticketV = temp[3];
		return true;
    }
    public String getTGS_C() {
    	return Kc_v+" "+IDv+" "+TS4+" "+ticketV;
    }
    
    
	public static String getHead() {
		return head;
	}
	public String getKc_v() {
		return Kc_v;
	}
	public String getIDv() {
		return IDv;
	}
	public long getTS4() {
		return TS4;
	}
	public String getTicketV() {
		return ticketV;
	}


}
