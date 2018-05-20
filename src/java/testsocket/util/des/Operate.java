package testsocket.util.des;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class Operate {
    private int [][]key = new int[17][48];
    static final char[] hex = new char[] {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
    private static final int[] IP = { // OK
            58, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36, 28, 20, 12, 4, 62, 54, 46,
            38, 30, 22, 14, 6, 64, 56, 48, 40, 32, 24, 16, 8, 57, 49, 41, 33,
            25, 17, 9, 1, 59, 51, 43, 35, 27, 19, 11, 3, 61, 53, 45, 37, 29,
            21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7 }; // 64
    /**
     * 经过16次迭代运算后。得到L16、R16，将此作為输入，进行逆置换，即得到密文输出。
     * 逆置换正好是初始置的逆运算，例如，第1位经过初始置换后，处於第40位，
     * 而通过逆置换，又将第40位换回到第1位，其逆置换规则如下表所示：
     */
    private static final int[] IP_1 = {  //OK
            40, 8, 48, 16, 56, 24, 64, 32,
            39, 7, 47, 15, 55, 23, 63, 31,
            38, 6, 46, 14, 54, 22, 62, 30,
            37, 5, 45, 13, 53, 21, 61, 29,
            36, 4, 44, 12, 52, 20, 60, 28,
            35, 3, 43, 11, 51, 19, 59, 27,
            34, 2, 42, 10, 50, 18, 58, 26,
            33, 1, 41, 9,  49, 17, 57, 25
    }; // 64
    /**
     * 放大换位表
     */
    private static final int[] E = { // OK
            32, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 9, 8, 9, 10, 11, 12, 13, 12, 13, 14, 15,
            16, 17, 16, 17, 18, 19, 20, 21, 20, 21, 22, 23, 24, 25, 24, 25, 26,
            27, 28, 29, 28, 29, 30, 31, 32, 1 }; // 48
    //缩小换位表：48bit->32bit
    static final int e2[] = {16,7,20,21,29,12,28,17,1,15,23,26,
            5,18,31,10,2,8,24,14,32,27,3,9,19,13,30,6,22,11,4,25
    };
    /**
     * PC1置换
     */
    private static final int[] PC_1 = { 57, 49, 41, 33, 25, 17, 9, 1, 58, 50,
            42, 34, 26, 18, 10, 2, 59, 51, 43, 35, 27, 19, 11, 3, 60, 52, 44,
            36, 63, 55, 47, 39, 31, 23, 15, 7, 62, 54, 46, 38, 30, 22, 14, 6,
            61, 53, 45, 37, 29, 21, 13, 5, 28, 20, 12, 4 }; // 56
    /**
     * 单纯换位表
     */
    private static final int[] P = { //OK
            16,  7, 20, 21,
            29, 12, 28, 17,
            1, 15, 23, 26,
            5, 18, 31, 10,
            2,  8, 24, 14,
            32, 27, 3,  9,
            19, 13, 30, 6,
            22, 11,  4, 25
    }; // 32
    /**
     * 循环左移位数 1,1,2,2,2,2,2,2,1,2,2,2,2,2,2,1
     */
    private static final int[] LeftMove = {
            1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1
    }; // 左移位置列表
    /**
     * PC2置换
     */
    private static final int[] PC_2 = {
            14, 17, 11, 24,  1,  5,
            3, 28, 15,  6, 21, 10,
            23, 19, 12,  4, 26,  8,
            16,  7, 27, 20, 13,  2,
            41, 52, 31, 37, 47, 55,
            30, 40, 51, 45, 33, 48,
            44, 49, 39, 56, 34, 53,
            46, 42, 50, 36, 29, 32
    }; // 48
    //S盒矩阵
    static final int sBoxMetrix[][][]= new int[][][]{
            {
                    {14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7},
                    {0,15,7,4,14,2,13,1,10,6,12,11,9,5,3,8},
                    {4,1,14,8,13,6,2,11,15,12,9,7,3,10,5,0},
                    {15,12,8,2,4,9,1,7,5,11,3,14,10,0,6,13}
            },
            {
                    {15,1,8,14,6,11,3,4,9,7,2,13,12,0,5,10},
                    {3,13,4,7,15,2,8,14,12,0,1,10,6,9,11,5},
                    {0,14,7,11,10,4,13,1,5,8,12,6,9,3,2,15},
                    {13,8,10,1,3,15,4,2,11,6,7,12,0,5,14,9}
            },
            {
                    {10,0,9,14,6,3,15,5,1,13,12,7,11,4,2,8},
                    {13,7,0,9,3,4,6,10,2,8,5,14,12,11,15,1},
                    {13,6,4,9,8,15,3,0,11,1,2,12,5,10,14,7},
                    {1,10,13,0,6,9,8,7,4,15,14,3,11,5,2,12}
            },
            {
                    {7,13,14,3,0,6,9,10,1,2,8,5,11,12,4,15},
                    {13,8,11,5,6,15,0,3,4,7,2,12,1,10,14,9},
                    {10,6,9,0,12,11,7,13,15,1,3,14,5,2,8,4},
                    {3,15,0,6,10,1,13,8,9,4,5,11,12,7,2,14}
            },
            {
                    {2,12,4,1,7,10,11,6,8,5,3,15,13,0,14,9},
                    {14,11,2,12,4,7,13,1,5,0,15,10,3,9,8,6},
                    {4,2,1,11,10,13,7,8,15,9,12,5,6,3,0,14},
                    {11,8,12,7,1,14,2,13,6,15,0,9,10,4,5,3},
            },
            {
                    {12,1,10,15,9,2,6,8,0,13,3,4,14,7,5,11},
                    {10,15,4,2,7,12,9,5,6,1,13,14,0,11,3,8},
                    {9,14,15,5,2,8,12,3,7,0,4,10,1,13,11,6},
                    {4,3,2,12,9,5,15,10,11,14,1,7,6,0,8,13}
            },
            {
                    {4,11,2,14,15,0,8,13,3,12,9,7,5,10,6,1},
                    {13,0,11,7,4,9,1,10,14,3,5,12,2,15,8,6},
                    {1,4,11,13,12,3,7,14,10,15,6,8,0,5,9,2},
                    {6,11,13,8,1,4,10,7,9,5,0,15,14,2,3,12}
            },
            {
                    {13,2,8,4,6,15,11,1,10,9,3,14,5,0,12,7},
                    {1,15,13,8,10,3,7,4,12,5,6,11,0,14,9,2},
                    {7,11,4,1,9,12,14,2,0,6,10,13,15,3,5,8},
                    {2,1,14,7,4,10,8,13,15,12,9,0,3,5,6,11}
            }
    };
    public byte[] re_byte(String str) throws UnsupportedEncodingException {
        byte[] in = str.getBytes();
        //String is=new String(str.getBytes("ISO8859-1"),"ISO8859-1");
        //	System.out.println(is);
        return in;
    }

    public void ASCII2Bit(byte bit[], byte des[]) {// 转换为二进制

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                des[i * 8 + j] = (byte) ((bit[i] >> (7 - j)) & 0x01);
            }
        }
    }
    private String bit2Hex(byte[]a){
        String strRtn = "";
        int length = a.length;
        int i=0,value=0;
        for(i=0;i<length;i+=4){
            int baseIndex = i;
            value = a[baseIndex+0]*8+a[baseIndex+1]*4+a[baseIndex+2]*2+a[baseIndex+3]*1;
            strRtn += hex[value];
        }
        return strRtn;
    }
    private void bit2Byte(byte[] a,byte[] b){
        int i=0,value=0,base=0;
        for(i=0;i<b.length;i++){
            base = i*8;
            value = a[base+0]*128 + a[base+1]*64 + a[base+2]*32 + a[base+3]*16 + a[base+4]*8 + a[base+5]*4 + a[base+6]*2 +a[base+7]*1;
            b[i] = (byte)value;
        }
    }
    private void hex2Bit(String hex,byte []bit){
        String strTmp ="";
        int i = 0;
        for(i=0;i<hex.length();i++){
            //System.out.println("charAt:"+hex.charAt(i));
            switch(hex.charAt(i))
            {
                case '0':
                    strTmp += "0000";
                    break;
                case '1':
                    strTmp += "0001";
                    break;
                case '2':
                    strTmp += "0010";
                    break;
                case '3':
                    strTmp += "0011";
                    break;
                case '4':
                    strTmp += "0100";
                    break;
                case '5':
                    strTmp += "0101";
                    break;
                case '6':
                    strTmp += "0110";
                    break;
                case '7':
                    strTmp += "0111";
                    break;
                case '8':
                    strTmp += "1000";
                    break;
                case '9':
                    strTmp += "1001";
                    break;
                case 'A':
                    strTmp += "1010";
                    break;
                case 'B':
                    strTmp += "1011";
                    break;
                case 'C':
                    strTmp += "1100";
                    break;
                case 'D':
                    strTmp += "1101";
                    break;
                case 'E':
                    strTmp += "1110";
                    break;
                case 'F':
                    strTmp += "1111";
                    break;
            }
        }
        for(i=0;i<strTmp.length();i++){
            bit[i]=(byte)(strTmp.charAt(i)-48);
           // System.out.println(bit[i]);
        }
    }
    public byte[] permute_ip(byte[] b) {// 置换ip
        byte a[] = new byte[64];
        for (int i = 0; i < b.length; i++) {
            a[i] = b[IP[i]-1];
        }
        return a;
    }
    private void F_Sbox(int n, byte[] ll, byte[] rr, byte[] LL, byte[] RR){
        int i, j,y,z,k,m;
        byte[] buffer = new byte[64], tmp = new byte[32],rtnByte = new byte[8] ;

        for (i = 0; i < 48; i++)  //明文32比特变48比特
            buffer[i] = rr[E[i] - 1];

        //Ri与Ki进行不进位加法
        for (i = 0; i < 48; i++)
            buffer[i] = (byte) ((buffer[i] + (byte)(key[n][i]))& 0x01);
        m=0;
        for(i=0;i<8;i++){
            j = 6 * i;
            y = (int)buffer[j]*2 + (int)buffer[j+5];
            z = (int)(buffer[j + 1] * 8 + buffer[j + 2] * 4 + buffer[j + 3] * 2+ buffer[j + 4]);
            rtnByte[i] = (byte)(sBoxMetrix[i][y][z]);
            y = 3;
            for (k = 0; k < 4; k++) {
                tmp[m++] = (byte)((rtnByte[i] >>> y) & 0x01);
                y--;
            }
        }
        for (k = 0; k < 32; k++)
            buffer[k] = tmp[e2[k] - 1];
        for (k = 0; k < 32; k++)
            RR[k] = (byte)((buffer[k] + ll[k]) & 1);
        for (k = 0; k < 32; k++)
            LL[k] = rr[k];
    }
    public byte[] conversion_e(byte[] b) {// E扩展
        byte a[] = new byte[48];
        for (int i = 0; i < E.length; i++) {
            a[i] = b[E[i]-1];
        }
        return a;
    }
    public byte[] partL_bite(byte[] b,int j) {// 获得左半边
        byte a[] = new byte[j];
        for (int i = 0; i<j ; i++) {
            a[i] = b[i];
        }
        return a;
    }
    public byte[] partR_bite(byte[] b,int j) {// 获得右半边
        int m=0;
        byte a[] = new byte[j];
        for (int i =0; i <j ; i++) {
            a[i] = b[i+j];
        }
        return a;
    }
    public byte[] permute_pc1(byte[] b) {// PC1置换
        byte a[] = new byte[56];
        for (int i =0; i <PC_1.length ; i++) {
            a[i] = b[PC_1[i]-1];
        }
        return a;
    }
    public String re_miwen(byte[] b) {//
        int i=0;
        byte[] inm = b;
        byte[] mdes = new byte[64];//64位的明文二进制
        byte[] L0_mdes=new byte[32];//明文初始左半边
        byte[] R0_mdes=new byte[32];//明文初始右半边
        byte[][] L0_mdes_bit=new byte[17][32];//明文初始左半边
        byte[][] R0_mdes_bit=new byte[17][32];//明文初始右半边
        byte []c_bit = new byte[64];
        byte[] e_bit = new byte[64];
        ASCII2Bit(inm, mdes);//转为二进制
        mdes=permute_ip(mdes);//ip置换
        L0_mdes=partL_bite(mdes,32);
        R0_mdes=partR_bite(mdes,32);
        for(i=1;i<17;i++){
            F_Sbox(i, L0_mdes, R0_mdes,L0_mdes_bit[i], R0_mdes_bit[i]);

            for (int j = 0; j < 32; j++) {
                L0_mdes[j] = L0_mdes_bit[i][j];
                R0_mdes[j] = R0_mdes_bit[i][j];
            }
        }
        //组合最终迭代结果并第二次IP变换
        for(i=0;i<32;i++){
            c_bit[i] = R0_mdes_bit[16][i];
            c_bit[i+32] = L0_mdes_bit[16][i];
        }
        for(i = 0; i < 64; i++) {
            e_bit[i] = c_bit[IP_1[i]-1];
        }

        String encryptText = bit2Hex(e_bit);
        //   System.out.println("我是16进制数："+encryptText);
        return encryptText;
    }
    private void dencryptByBlock(String txt,byte []b,int k){
        int i, j;
        byte[] l_byte  = new byte[32];
        byte[] r_byte = new byte[32];
        byte[][] l_bit = new byte[17][32];
        byte[][] r_bit = new byte[17][32];
        byte []c_bit = new byte[64];
        byte[] e_bit = new byte[64];
        byte []enTxt = new byte[64];
        //按照ip表对待解密字串进行位变换,分为左右两部分
        hex2Bit(txt,enTxt);
        enTxt=permute_ip(enTxt);//ip置换
        l_byte=partL_bite(enTxt,32);
        r_byte=partR_bite(enTxt,32);
        //16轮解密
        for(i=16;i>0;i--){
            F_Sbox(i, l_byte, r_byte, l_bit[i], r_bit[i]);

            for (j = 0; j < 32; j++) {
                l_byte[j] = l_bit[i][j];
                r_byte[j] = r_bit[i][j];
            }
        }
        //组合最终迭代结果并第二次IP变换
        for(i=0;i<32;i++){
            c_bit[i] = r_bit[1][i];
            c_bit[i+32] = l_bit[1][i];
        }
        for(i = 0; i < 64; i++) {
            e_bit[i] = c_bit[IP_1[i]-1];
        }
        for(i=0;i<64;i++){
            b[i]=e_bit[i];
        }
    }
    public byte[] re_ming(String m_txt,int len) throws UnsupportedEncodingException  //解密
    {
        byte b_bit[] = new byte[64],a_byte[]= new byte[8],c_byte[] = new byte[len];
        int i=0,j=0,k=0;
        for(i=0;i<m_txt.length();i=i+16)
        {
            dencryptByBlock(m_txt.substring(i, i+16), b_bit,1);
            bit2Byte(b_bit,a_byte);
            k=0;
            while((j<len)&&(k<a_byte.length)){
                c_byte[j++] = a_byte[k++];
            }
            if(!(j<len))
            {
                break;
            }
        }
        return c_byte;
    }
    public byte[] re_key(String kstr) throws UnsupportedEncodingException{//得到16次秘钥，64经过pc1变成56位，分成两部分，对两部分分别
        int i, k, rol = 0;            //进行左移操作，然后经过pc2变成48位
        int []tmp = new int[56];
        byte[] ink = re_byte(kstr);//得到8字节
        byte[] kdes = new byte[64];//64位的密文二进制
        ASCII2Bit(ink, kdes);//转为二进制
        //System.out.println("我是秘钥64："+Arrays.toString(kdes));
        byte[] kdes_pc1 = new byte[56];//56位的明文二进制
        kdes_pc1=permute_pc1(kdes);//ip置换
        //System.out.println("我是秘钥56："+Arrays.toString(kdes_pc1));
        byte[][] L0_kdes=new byte[17][28];//明文初始左半边
        byte[][] R0_kdes=new byte[17][28];//明文初始右半边
        L0_kdes[0]=partL_bite(kdes_pc1,28);
        R0_kdes[0]=partR_bite(kdes_pc1,28);
        //System.out.println("我是秘钥的左半边："+Arrays.toString(L0_kdes[0]));
        //分别生成16个子密钥
        for(i = 1; i < 17; i++)
        {
            //循环左移
            rol = LeftMove[i-1];
            //合并左移后的结果
            for(k = 0; k < 28; k++){
                L0_kdes[i][k] = L0_kdes[i-1][(k+rol)%28];
                R0_kdes[i][k] = R0_kdes[i-1][(k+rol)%28];
            }
            //合并C和D
            for(k=0;k<28;k++){
                tmp[k] = L0_kdes[i][k];
                tmp[k+28] = R0_kdes[i][k];
            }
            //位变换
            for(k = 0; k < 48; k++)
                key[i][k] = tmp[PC_2[k] - 1];
        }
        return null;
    }
    public String group(String str) throws UnsupportedEncodingException{
        String miwen="";
        String mingwen="";
        int length=0;
        int index=0;
        int index1=0;
        byte[] by_ming=new byte[]{0,0,0,0,0,0,0,0};
        byte[] s_by_ming=new byte[]{0,0,0,0,0,0,0,0};
        byte[] by_all=re_byte(str);
        byte[] by_all1=new byte[re_byte(str).length];
        length=by_all.length;
        length=(length-length%8)/8;
        System.out.println("我是分组个数："+length);
        for(int i=0;i<length;i++)
        {

            for(int j=0;j<8;j++){
                by_ming[j]=by_all[i*8+j];

            }
            miwen+=re_miwen(by_ming);
           // System.out.println(miwen);
            by_ming=re_ming(re_miwen(by_ming),8);
            for(int j=0;j<8;j++){
                by_all1[i*8+j]=by_ming[j];
            }
            //	mingwen+=re_ming(re_miwen(by_ming),8);
        }
        if((by_all.length%8)!=0)
        {
            index=by_all.length%8;
            index1=(by_all.length-by_all.length%8)/8	;
            for(int i=0;i<index;i++)
            {
                s_by_ming[i]=by_all[index1*8+i];//获得剩余未分组字符
            }
            miwen+=re_miwen(s_by_ming);//获得密文
            s_by_ming=re_ming(re_miwen(s_by_ming),8);
            for(int j=0;j<index;j++){
                by_all1[index1*8+j]=s_by_ming[j];
            }
            System.out.println("我是剩余密文:"+re_miwen(s_by_ming));
        }
        System.out.println("我是字节长度:"+by_all.length);
        System.out.println("我是密文:"+miwen);
//        My_DES_GUI.mi.setText(miwen);
        String jie_mi=new String(by_all1);
        System.out.println("我是解密后明文：:"+jie_mi);
        return jie_mi;
    }
  /* public String jiemi(String str) throws UnsupportedEncodingException{
        byte[] by_all=group(str);
        String jie_mi=new String(by_all);
        return jie_mi;
        //My_DES_GUI.mi.setText(jie_mi);
    }*/
    public Operate(String mstr,String kstr) throws UnsupportedEncodingException {
        group(mstr);
		/*re_key(kstr);
		re_miwen(mstr);
		String srcStr2 = re_ming(re_miwen(mstr),mstr.getBytes().length);
		System.out.println("我是密文:"+re_miwen(mstr));
		System.out.println("我是解密后:"+srcStr2);*/
    }
}
