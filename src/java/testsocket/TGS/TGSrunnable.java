package testsocket.TGS; /*
 * create by weikunpeng
 * 2018/5/27 15:08
 */


import testsocket.bean.User;
import testsocket.bean.message.*;
import testsocket.dao.impl.DaoImpl;
import testsocket.util.MD5Util;

import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Date;

class TGSrunnable implements Runnable {
    private Socket socket ;

    public TGSrunnable (Socket socket) {
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
            System.out.println("TGS服务器就绪...");
            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw=new PrintWriter(socket.getOutputStream(),true);
            String  messageC_TGS=br.readLine();
            if (messageC_TGS!=null) {
                //String messageC_AS="11 wei 11 22";//from C

                C_TGS c_tgs=new C_TGS();

                c_tgs.DealWithMessageC_TGS(messageC_TGS);

                System.out.println(messageC_TGS);

                if(c_tgs.getHead().equals("11")){



                    //TODo

                    //TGS验证过程



                    //处理返回数据包的过程 下面的是我用最简单的模拟


                    long TS4=System.currentTimeMillis();
                    long lifeTime4 = System.currentTimeMillis() + 30 * 60 * 1000;

                    TicketV ticketV=new TicketV("k_cv","IDc","ADc","IDv",TS4,lifeTime4);

                    TGS_C tgs_c=new TGS_C("11","kc_v","IDv",TS4,ticketV);



                    String messageTGS_C=tgs_c.getTGS_C();

                    System.out.println(messageTGS_C);










                    //String messageAS_C=as_c.getAS_C();

                    //System.out.println(messageAS_C);


                    PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                   printWriter.println(messageTGS_C);
                    printWriter.flush();



                    //ToDo
                    //发送消息到客户端


                }

                if(c_tgs.getHead().equals("12")){

                }

                if( c_tgs.getHead().equals("00")){

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