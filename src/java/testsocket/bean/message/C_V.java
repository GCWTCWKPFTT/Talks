package testsocket.bean.message;

import testsocket.common.Const;

import java.io.UnsupportedEncodingException;

public class C_V {
	private static final String head = "05"; //包头的判断在处理之前。
	
	private TicketV ticketV;
	private Authenticator authenticator;
	
	public C_V() {
		
	}
	
	public boolean DealwithMessage(String messageC_V) throws UnsupportedEncodingException{
    	//C_TGS 处理函数 处理之前要判断包头，及包长度
        String message=messageC_V.substring(3);

        String [] temp=message.split(" ");
        if(temp.length!=2) return false;

        String string_TicketV = temp[0];
        String string_Authenticator=temp[1];
        
        ticketV = new TicketV();
        ticketV.DealwithMessage(string_TicketV, Const.kv);
        
        String Kcv = ticketV.getKc_v();
        
        authenticator = new Authenticator();
        authenticator.DealwithMessage(string_Authenticator, Kcv);
        
        return true;
    }

	public static String getHead() {
		return head;
	}

	public TicketV getTicketV() {
		return ticketV;
	}

	public Authenticator getAuthenticator() {
		return authenticator;
	}
	
	
	
}
