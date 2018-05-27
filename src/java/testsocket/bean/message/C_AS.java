package testsocket.bean.message; /*
 * create by weikunpeng
 * 2018/5/20 18:57
 */

import com.sun.xml.internal.bind.v2.model.core.ID;

public class C_AS {


    private  String head;
    private String IDc;
    private String IDtgs;
    private long TS1;

    public C_AS() {

    }

    public C_AS(String head, String IDc, String IDtgs, long TS1) {
        this.head = head;
        this.IDc = IDc;
        this.IDtgs = IDtgs;
        this.TS1 = TS1;
    }

    public boolean DealwithMessage(String messageC_AS){

        head=messageC_AS.substring(0,2);
        String message=messageC_AS.substring(3);
        //System.out.println(message);

        String [] temp=message.split(" ");
        if(temp.length!=3) return false;

        IDc=temp[0];

        IDtgs=temp[1];

        TS1=Long.parseLong(temp[2]);

        return true;




    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getC_AS(){
        return head+" "+IDc+" "+IDtgs+" "+TS1;
    }

    public String getIDc() {
        return IDc;
    }

    public void setIDc(String IDc) {
        this.IDc = IDc;
    }

    public String getIDtgs() {
        return IDtgs;
    }

    public void setIDtgs(String IDtgs) {
        this.IDtgs = IDtgs;
    }

    public long getTS1() {
        return TS1;
    }

    public void setTS1(long TS1) {
        this.TS1 = TS1;
    }
}
