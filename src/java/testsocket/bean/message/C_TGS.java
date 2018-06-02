package testsocket.bean.message;

import testsocket.common.Const;

import java.io.UnsupportedEncodingException;

public class C_TGS {
	private static final String head = "03"; //包头的判断在处理之前。
	
	private String IDv;
    private TicketTGS ticketTGS;
    private Authenticator authenticator;
    
    public C_TGS() {
    	
    }
    public C_TGS(String message) {
    	
    }
    public boolean DealwithMessage(String messageC_TGS) throws UnsupportedEncodingException{
    	//C_TGS 处理函数 处理之前要判断包头，及包长度
        String message=messageC_TGS.substring(3);

        String [] temp=message.split(" ");
        if(temp.length!=3) return false;

        IDv=temp[0];
        String string_TicketTGS = temp[1];
        String string_Authenticator=temp[2];
        
//        String string_TicketTGS_M = DES.des(string_TicketTGS, Const.ktgs, 2);
        ticketTGS = new TicketTGS();
        ticketTGS.DealwithMessage(string_TicketTGS,Const.ktgs);
        
        String Kc_tgs = ticketTGS.getKctgs();
        authenticator = new Authenticator();
        authenticator.DealwithMessage(string_Authenticator, Kc_tgs);
        
        return true;
    }
	public static String getHead() {
		return head;
	}
	public String getIDv() {
		return IDv;
	}
	public TicketTGS getTicketTGS() {
		return ticketTGS;
	}
	public Authenticator getAuthenticator() {
		return authenticator;
	}
    
    
    
}
