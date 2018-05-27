package testsocket.TGS; /*
 * create by weikunpeng
 * 2018/5/27 15:08
 */





import java.beans.PropertyVetoException;

import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;


public class TGSserver {


    void receive(String ip, int port) {
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            Socket socket = server.accept();

            System.out.println("等待client发送消息...");
            TGSrunnable task = new TGSrunnable(socket);

            new Thread(task).start();
            Thread.sleep(10000);


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
        tgSserver.receive("127.0.0.1", 9998);

    }
}
