package testsocket.TGS;

import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;

import testsocket.bean.message.C_TGS;
import testsocket.bean.message.TGS_C;
import testsocket.bean.message.TicketTGS;
import testsocket.bean.message.TicketV;
import testsocket.common.Const;
import testsocket.util.DES;

class TGSrunnable implements Runnable {
	private Socket socket;

	public TGSrunnable(Socket socket) {
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
			System.out.println("ip :" + ip);
			System.out.println("TGS服务器就绪...");
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pw = new PrintWriter(socket.getOutputStream(), true);
			String messageC_TGS = br.readLine();
System.out.println("TGS服务器收到报文: "+messageC_TGS);
			if (messageC_TGS != null) {
				C_TGS c_tgs = new C_TGS();
				//  c->TGS 消息解密
				if (messageC_TGS.substring(0, 2).equals("03")) {
					c_tgs.DealwithMessage(messageC_TGS);

					// TGS验证过程
					
					TicketTGS ticketTGS = c_tgs.getTicketTGS();
System.out.println("TGS服务器处理获得ticketTGS: "+ticketTGS.getTicketTGS());
System.out.println("TGS服务器处理获得authenticato: "+c_tgs.getAuthenticator().getAuthenticator());
					// 验证lifetime
					long TS2 = ticketTGS.getTS2();
					long lifeTime2 = ticketTGS.getLifeTime2();
					long curTime = System.currentTimeMillis();
					
					String messageTGS_C;
					if (curTime > TS2 + lifeTime2) {
						// 发送错误信息包
						messageTGS_C = "08 01 票据超时";
					}else {
						
						//TODO 比较AD

						String Kcv = DES.KeyCreate();
						String IDc = ticketTGS.getIDc();
						String ADc = ticketTGS.getADc();
						String IDv = c_tgs.getIDv();
						long TS4 = System.currentTimeMillis();
						long lifeTime4 = 30 * 60 * 1000;
						TicketV ticketV = new TicketV(Kcv, IDc, ADc, IDv, TS4, lifeTime4);

						String string_ticketV_C = DES.des(ticketV.getTicketV(), Const.kv, 1);
System.out.println("TGS服务器生成ticketV并加密: "+ticketV.getTicketV());
System.out.println("                         : "+string_ticketV_C);						
						String messageTGS_C_M = Kcv+" " +IDv+" "+TS4+" "+string_ticketV_C;
						String Kctgs = ticketTGS.getKctgs();
						messageTGS_C = "04 "+DES.des(messageTGS_C_M, Kctgs, 1);
System.out.println("TGS服务器生成TGS_C报文并加密: "+messageTGS_C_M);

						
						
						
					}

					;
System.out.println("TGS服务器发送报文: "+messageTGS_C);
					PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
					printWriter.println(messageTGS_C);
					printWriter.flush();

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
}
