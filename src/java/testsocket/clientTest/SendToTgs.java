package testsocket.clientTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SendToTgs {
	public static String SendToTGS(String message, String ip, int port) throws IOException {

        Socket socket=new Socket(ip,port);

        PrintWriter pw = new PrintWriter(socket.getOutputStream());

        BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));




        pw.println(message);
        pw.flush();

        String messageAS_c = is.readLine();


        


        //System.out.println(tgs_c.getTGS_C());

        //if(as_c!=null)
        // System.out.println(as_c.getTicketTGS().getTicketTGS());







        pw.close();
        is.close();
        socket.close();

        return messageAS_c;

    }

}
