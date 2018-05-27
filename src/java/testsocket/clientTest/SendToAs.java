package testsocket.clientTest; /*
 * create by weikunpeng
 * 2018/5/20 23:16
 */

import testsocket.bean.message.AS_C;
import testsocket.bean.message.C_AS;
import testsocket.common.Const;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SendToAs {

    public static AS_C SendToAS(String message,String ip,int port) throws IOException {

        Socket socket=new Socket(ip,port);

        PrintWriter pw = new PrintWriter(socket.getOutputStream());

        BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));




        pw.println(message);
        pw.flush();

        String messageAS_c = is.readLine();


        AS_C as_c=new AS_C();
        as_c.DealwithMessage(messageAS_c);

        //if(as_c!=null)
       // System.out.println(as_c.getTicketTGS().getTicketTGS());







        pw.close();
        is.close();
        socket.close();

        return as_c;

    }
}
