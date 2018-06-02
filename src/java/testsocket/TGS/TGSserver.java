package testsocket.TGS; 


import java.beans.PropertyVetoException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

import testsocket.common.Const;


public class TGSserver {


    void receive(String ip, int port) {
        ServerSocket server = null;
        try {

                server = new ServerSocket(port);
             while (true) {
                System.out.println("TGS等待client发送消息...");

                Socket socket = server.accept();
                TGSrunnable task = new TGSrunnable(socket);

                new Thread(task).start();
                //Thread.sleep(10000);
            }


        } catch (Exception e) {
            System.out.println("e1" + e);
        } finally {
            try {
                if (server != null) {
                    server.close();
                }
            } catch (Exception e3) {
                System.out.println("e2" + e3);
            }

        }

    }


    public static void main(String[] args) throws PropertyVetoException, SQLException {


        TGSserver tgSserver=new TGSserver();
        tgSserver.receive(Const.IP_TGS, Const.PORT_TGS);

    }
}
