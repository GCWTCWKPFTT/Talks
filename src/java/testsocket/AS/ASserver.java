package testsocket.AS;

import testsocket.common.Const;

import java.beans.PropertyVetoException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class ASserver {


    void receive(String ip, int port) {
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("等待client发送消息...");

            while(true){
            Socket socket = server.accept();
            ASrunnable task = new ASrunnable(socket);

            new Thread(task).start();
           // Thread.sleep(10000);
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


        ASserver aSserver = new ASserver();
        aSserver.receive(Const.IP_AS, Const.PORT_AS);

    }
}
