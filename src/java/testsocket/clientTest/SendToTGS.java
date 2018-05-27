package testsocket.clientTest; /*
 * create by weikunpeng
 * 2018/5/27 15:58
 */

import testsocket.bean.message.AS_C;
import testsocket.bean.message.TGS_C;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SendToTGS {

    public static TGS_C SendToTGS(String message, String ip, int port) throws IOException {

        Socket socket=new Socket(ip,port);

        PrintWriter pw = new PrintWriter(socket.getOutputStream());

        BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));




        pw.println(message);
        pw.flush();

        String messageAS_c = is.readLine();


        TGS_C tgs_c=new TGS_C();
        tgs_c.DealWithMessageTGS_C(messageAS_c);


        System.out.println(tgs_c.getTGS_C());

        //if(as_c!=null)
        // System.out.println(as_c.getTicketTGS().getTicketTGS());







        pw.close();
        is.close();
        socket.close();

        return tgs_c;

    }
}
