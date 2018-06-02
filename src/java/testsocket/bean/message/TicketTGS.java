package testsocket.bean.message;

import java.io.UnsupportedEncodingException;

import testsocket.util.DES;

public class TicketTGS {
	private String Kctgs;
	private String IDc;
	private String ADc;
	private String IDtgs;
	private long TS2;
	private long lifeTime2;
	
	
	public TicketTGS() {

    }
	public TicketTGS(String kc_tgs, String IDc, String ADc, 
			String IDtgs, long TS2, long lifeTime2) {
		this.Kctgs = kc_tgs;
		this.IDc = IDc;
		this.ADc = ADc;
		this.IDtgs = IDtgs;
		this.TS2 = TS2;
		this.lifeTime2 = lifeTime2;
	}
	public boolean DealwithMessage(String ticket_C,String key) throws UnsupportedEncodingException {
		String ticket_M = DES.des(ticket_C, key, 2);
		String[] temp = ticket_M.split(" ");
		if(temp.length != 6) {
			System.out.println("tickettgs wrong");
			return false;
		}
		this.Kctgs = temp[0];
        this.IDc = temp[1];
        this.ADc = temp[2];
        this.IDtgs = temp[3];
        this.TS2 = Long.parseLong(temp[4]);
        this.lifeTime2 = Long.parseLong(temp[5]);
		return true;
	}
	public String getTicketTGS() {
		return Kctgs+" "+IDc+" "+ADc+" "+IDtgs+" "+TS2+" "+lifeTime2;
	}
	
	
	public String getKctgs() {
		return Kctgs;
	}
	public String getIDc() {
		return IDc;
	}
	public String getADc() {
		return ADc;
	}
	public String getIDtgs() {
		return IDtgs;
	}
	public long getTS2() {
		return TS2;
	}
	public long getLifeTime2() {
		return lifeTime2;
	}
	
}
