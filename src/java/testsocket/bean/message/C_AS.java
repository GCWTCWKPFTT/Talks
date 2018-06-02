package testsocket.bean.message;
/*
 * C_AS类包括以下部分：
 * 1、C向AS发送报文时报文的生成
 * 输入参数private String IDc;
    private String IDtgs;
    private long TS1;
    2、AS解析该
 */
public class C_AS {
	private static final String head = "01"; //包头的判断在处理之前。
	
	private String IDc;
    private String IDtgs;
    private long TS1;
    
    public C_AS() {

    }
    public C_AS(String IDc,String IDtgs,long TS1) {
    	this.IDc = IDc;
        this.IDtgs = IDtgs;
        this.TS1 = TS1;
    }
    public C_AS(String message) {
    	
    }
    public boolean DealwithMessage(String messageC_AS){
    	//C_AS 处理函数 处理之前要判断包头，及包长度
    	
//        head=messageC_AS.substring(0,2);
    	
        String message=messageC_AS.substring(3);
        //System.out.println(message);

        String [] temp=message.split(" ");
        if(temp.length!=3) return false;

        IDc=temp[0];

        IDtgs=temp[1];

        TS1=Long.parseLong(temp[2]);

        return true;

    }
    
    public String getC_AS() {
    	//返回完整报文
    	return IDc+" "+IDtgs+" "+TS1;
    }
    
	public static String getHead() {
		return head;
	}
	public String getIDc() {
		return IDc;
	}

	public String getIDtgs() {
		return IDtgs;
	}

	public long getTS1() {
		return TS1;
	}
    

}
