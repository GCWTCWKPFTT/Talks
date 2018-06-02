package testsocket.bean.message;

import java.io.UnsupportedEncodingException;

import testsocket.util.DES;

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
    public boolean DealwithMessage(String ticket_V,String key) throws UnsupportedEncodingException {
		String ticket_M = DES.des(ticket_V, key, 2);
		String[] temp = ticket_M.split(" ");
		if(temp.length != 6) {
			System.out.println("tickettgs wrong");
			return false;
		}
		this.Kc_v = temp[0];
        this.IDc = temp[1];
        this.ADc = temp[2];
        this.IDv = temp[3];
        this.TS4 = Long.parseLong(temp[4]);
        this.Lifetime4 = Long.parseLong(temp[5]);
		return true;
	}
    
    public String getTicketV() {
    	return Kc_v+" "+IDc+" "+ADc+" "+IDv+" "+Long.toString(TS4)+" "+Long.toString(Lifetime4);
    }
    
	public String getKc_v() {
		return Kc_v;
	}
	public String getIDc() {
		return IDc;
	}
	public String getADc() {
		return ADc;
	}
	public String getIDv() {
		return IDv;
	}
	public long getTS4() {
		return TS4;
	}
	public long getLifetime4() {
		return Lifetime4;
	}
    
}
