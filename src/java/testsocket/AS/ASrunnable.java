package testsocket.AS; /*
 * create by weikunpeng
 * 2018/5/20 22:15
 */

import testsocket.bean.User;
import testsocket.bean.message.AS_C;
import testsocket.bean.message.C_AS;
import testsocket.bean.message.TicketTGS;
import testsocket.dao.impl.DaoImpl;

import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Date;

class ASrunnable implements Runnable {
    private Socket socket ;

    public ASrunnable (Socket socket) {
        this.socket=socket;
    }

    public void run () {
        try {
            handleSocket(socket);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    public void handleSocket (Socket socket) throws PropertyVetoException, SQLException, IOException {
        BufferedReader br=null;
        PrintWriter pw=null;
        try {
            String ip = socket.getInetAddress().toString();
            System.out.println("ip :"+ip);
            System.out.println("服务器就绪...");
            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw=new PrintWriter(socket.getOutputStream(),true);
            String  messageC_AS=br.readLine();
            if (messageC_AS!=null) {
                //String messageC_AS="11 wei 11 22";//from C

                C_AS c_as=new C_AS();

                c_as.DealwithMessage(messageC_AS);

                System.out.println(c_as.getC_AS());

                if(c_as.getHead().equals("11")){

                    //Date

                    //TODO  JDBC 连数据库

                    DaoImpl dao=new DaoImpl();
                    User user=dao.Login1(c_as.getIDc());

                    System.out.println(user.getName());



                    if(user==null)  {

                        //ToDo
                        //如果这个用户在数据中没有找到
                    }



                    String kc_tgs="12345678";

                    String IDc=user.getName();

                    String ADc="127.0.0.1";

                    String IDtgs="1";

                    Date TS=new Date();
                    String TS2=TS.toString();

                    long currentTime = System.currentTimeMillis() + 30 * 60 * 1000;

                    Date lifeTime=new Date(currentTime);


                    String lifeTime2=lifeTime.toString();



                    TicketTGS ticketTGS=new TicketTGS(kc_tgs,IDc,ADc,IDtgs,TS2,lifeTime2);

                    AS_C as_c=new AS_C("11",kc_tgs,IDtgs,TS2,lifeTime2,ticketTGS);


                    String messageAS_C=as_c.getAS_C();

                    System.out.println(messageAS_C);


                    PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                    printWriter.println(messageAS_C);
                    printWriter.flush();



                    //ToDo
                    //发送消息到客户端


                }

                if(c_as.getHead().equals("12")){

                }

                if( c_as.getHead().equals("00")){

                }



            }



        }
        catch (Exception e1) {
            System.out.println("e3"+e1);
        }
        finally {
            try {
                if (br!=null)  {br.close(); }
                if (pw!=null)  {pw.close(); }
                if (socket!=null) {socket.close();}
            }
            catch (Exception e2) {
                System.out.println("e4"+e2);
            }
        }
    }
}