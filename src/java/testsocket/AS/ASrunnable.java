package testsocket.AS;

import testsocket.bean.User;
import testsocket.bean.message.AS_C;
import testsocket.bean.message.C_AS;
import testsocket.bean.message.TicketTGS;
import testsocket.common.Const;
import testsocket.dao.impl.DaoImpl;
import testsocket.util.DES;

import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;

class ASrunnable implements Runnable {
    private Socket socket;

    public ASrunnable(Socket socket) {
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
            System.out.println("AS服务器就绪...");
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw = new PrintWriter(socket.getOutputStream(), true);
            String messageC_AS = br.readLine();
            System.out.println("AS服务器收到报文: "+messageC_AS);
            if (messageC_AS != null) {
                C_AS c_as = new C_AS();
                if ((messageC_AS.substring(0, 2)).equals("01")) {
                    c_as.DealwithMessage(messageC_AS);
                    // Date

                    // JDBC 连数据库
                    User user = null;
                    DaoImpl dao = new DaoImpl();
                    // TODO 获取用户名及密码 IDc就是user里的name
                    user = dao.Login1(c_as.getIDc());

                    String Kc = user.getPassword();
//					System.out.println(user.getName());



                    String messageAS_C;
                    //判断用户是否存在
                    //TODO 加上获取用户密码后改为user==null
                    if (user == null) {
//						// 如果这个用户在数据中没有找到
                        messageAS_C = "07 01 不存在用户"+user.getName();
//
                    }else
                    {
                        String kc_tgs = DES.KeyCreate();
                        String IDc = c_as.getIDc();
                        // InetAddress address=socket.getInetAddress();
                        // String ADc = address.getHostAddress();
                        String ADc = socket.getInetAddress().getHostAddress();
                        String IDtgs = c_as.getIDtgs();
                        long TS2 = System.currentTimeMillis();
                        long lifeTime2 = 30 * 60 * 1000;

                        TicketTGS ticketTGS_M = new TicketTGS(kc_tgs, IDc, ADc, IDtgs, TS2, lifeTime2);
                        //_M明文 _C密文
                        String ticketTGS_C = DES.des(ticketTGS_M.getTicketTGS(), Const.ktgs, 1	);
                        System.out.println("AS服务器生成ticketTGS,并用Ktgs加密");
                        System.out.println("    加密前M："+ticketTGS_M.getTicketTGS());
                        System.out.println("    加密后C："+ticketTGS_C);

//						AS_C as_c = new AS_C("02", kc_tgs, IDtgs, TS2, currentTime, ticketTGS);
                        AS_C as_c = new AS_C(kc_tgs, IDtgs, TS2, lifeTime2, ticketTGS_C);
                        System.out.println("AS服务器生成AS_C报文,并用Kc加密");
                        System.out.println("    加密前M："+as_c.getAS_C());

                        messageAS_C = "02 "+DES.des(as_c.getAS_C(), Kc, 1); //加密并添加报头

                    }

                    System.out.println("AS服务器发送AS_C报文为："+messageAS_C);

                    PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                    printWriter.println(messageAS_C);
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
}