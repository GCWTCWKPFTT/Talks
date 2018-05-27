package testsocket.clientTest; /*
 * create by weikunpeng
 * 2018/5/19 19:07
 */

import testsocket.Talk;
import testsocket.bean.message.*;
import testsocket.common.Const;

import java.io.*;
import java.net.Socket;

public class clientTest {

    public static void main(String[] args) throws IOException {

        String head="11";
        String IDc="fang";
        String IDtgs=Const.ID_TGS;
        long TS1=System.currentTimeMillis();

        C_AS c_as=new C_AS(head,IDc,IDtgs,TS1);

        AS_C as_c =SendToAs.SendToAS(c_as.getC_AS(),"127.0.0.1",9999);
        System.out.println(as_c.getAS_C());
        System.out.println("AS认证成功！！！！！！！！！！！！");



        //Todo 处理AS返回的消息 并向TGS验证

        Long TS3=System.currentTimeMillis();

        Authenticator  authenticator=new Authenticator("IDc","ADc",TS3);

        C_TGS c_tgs=new C_TGS("11",Const.ID_V,as_c.getTicketTGS(),authenticator);


        TGS_C tgs_c=SendToTGS.SendToTGS(c_tgs.getC_TGS(),"127.0.0.1",9998);

        System.out.println(tgs_c.getTGS_C());
        System.out.println("TGS验证成功！！！！！！！！！");
        



    }
}
