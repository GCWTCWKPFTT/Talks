package testsocket.Vserver;

import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.sql.SQLException;

import testsocket.bean.message.AS_C;
import testsocket.bean.message.Authenticator;
import testsocket.bean.message.C_AS;
import testsocket.bean.message.C_V;
import testsocket.bean.message.TicketTGS;
import testsocket.bean.message.TicketV;
import testsocket.common.Const;
import testsocket.util.DES;

public class Vrunnable implements Runnable {
	private Socket socket;

	public Vrunnable(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			handleSocket(socket);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void handleSocket(Socket socket) throws PropertyVetoException, SQLException, IOException {
		BufferedReader br = null;
		PrintWriter pw = null;
		try {
			String ip = socket.getInetAddress().toString();
System.out.println("C_ip :" + ip);
System.out.println("V服务器就绪...");
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pw = new PrintWriter(socket.getOutputStream(), true);
			String messageC_V = br.readLine();
			
			
	
			
			
			
			
			
System.out.println("V服务器收到报文: "+messageC_V);
			if (messageC_V != null) {
				C_V c_v = new C_V();
				if ((messageC_V.substring(0, 2)).equals("05")) {
					c_v.DealwithMessage(messageC_V);
					
					TicketV ticketV = c_v.getTicketV();
					Authenticator authentorV = c_v.getAuthenticator();
					
					String messageV_C= messagetoC(ticketV,authentorV);
					


System.out.println("V服务器发送V_C报文为："+messageV_C);










					PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
					printWriter.println(messageV_C);
					printWriter.flush();

					// ToDo
					// 发送消息到客户端
				}
				
			}

		} catch (Exception e1) {
			System.out.println("e3" + e1);
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (pw != null) {
					pw.close();
				}
				if (socket != null) {
					socket.close();
				}
			} catch (Exception e2) {
				System.out.println("e4" + e2);
			}
		}
	}
	private String messagetoC(TicketV ticketV,Authenticator authentorV) throws UnsupportedEncodingException {
		String messageVC = null;
		
		String Kcv = ticketV.getKc_v();
		String IDc_t = ticketV.getIDc();
		String ADc_t = ticketV.getADc();
		String IDv_t = ticketV.getIDv();
		long TS4_t = ticketV.getTS4();
		long lifeTime4_t = ticketV.getLifetime4();
		
		String IDc_a = authentorV.getIDc();
		String ADc_a = authentorV.getADc();
		long TS5 = authentorV.getTS();
		
		//TODO 比较 IDc_t\IDc_a   ADc_t\ADc_a  IDv_t\本机IDconst.IDv    TS4+lifetime4
		if(!IDc_t.equals(IDc_a))
		{
			return "09 01 ID不符";
		}
		//TODO 单机测试 AD不同
//		if(!ADc_t.equals(ADc_a))
//		{
//			return "09 02 AD不符";
//		}
		if(!IDv_t.equals(Const.ID_V))
		{
			return "09 03 非本机IDv";
		}
		if(System.currentTimeMillis()>(TS4_t+lifeTime4_t)) {
			return "09 04 票据超时";
		}
		String TS = (TS5+1)+"";
		return "06 "+DES.des(TS, Kcv, 1);
	}
}
