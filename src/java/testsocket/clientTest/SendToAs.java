package testsocket.clientTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SendToAs {

    public static String SendToAS(String message,String ip,int port) throws IOException {

        Socket socket=new Socket(ip,port);

        PrintWriter pw = new PrintWriter(socket.getOutputStream());

        BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));




        pw.println(message);
        pw.flush();

        String messageAS_c = is.readLine();
        

        

        //if(as_c!=null)
       // System.out.println(as_c.getTicketTGS().getTicketTGS());







        pw.close();
        is.close();
        socket.close();

        return messageAS_c;

    }
}
