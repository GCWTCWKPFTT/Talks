package testsocket.bean.message;

import java.io.UnsupportedEncodingException;

public class AS_C {
	private static final String head = "02";// 包头

	private String Kc_tgs;
	private String IDtgs;
	private long TS2;
	private long LifeTime2;
	private String ticketTGS;

	public AS_C() {

	}
	public AS_C(String kc_tgs, String IDtgs, long TS2, long lifeTime2, String ticketTGS) {
        this.Kc_tgs = kc_tgs;
        this.IDtgs = IDtgs;
        this.TS2 = TS2;
        this.LifeTime2 = lifeTime2;
        this.ticketTGS = ticketTGS;
    }
	public boolean DealwithMessage(String messageC_AS) throws UnsupportedEncodingException {
		String[] temp = messageC_AS.split(" ");
		if (temp.length != 5) {
			System.out.println("deal c_tgs wrong");
			return false;
		}
		Kc_tgs = temp[0];
		IDtgs = temp[1];
		TS2 = Long.parseLong(temp[2]);
		LifeTime2 = Long.parseLong(temp[3]);
		ticketTGS = temp[4];
//		ticketTGS = new TicketTGS();
//		ticketTGS.DealwithMessage(temp[4], Const.ktgs);
		return true;
	}
	public String getAS_C(){
        return Kc_tgs+" "+IDtgs+" "+TS2+" "+LifeTime2+" "+ticketTGS;
    }

	public String getKc_tgs() {
		return Kc_tgs;
	}

	public String getIDtgs() {
		return IDtgs;
	}

	public long getTS2() {
		return TS2;
	}

	public long getLifeTime2() {
		return LifeTime2;
	}

	public String getTicketTGS() {
		return ticketTGS;
	}
	
	
}
