  
public class Des_Test {  
      
    //十六进制符号表  
    static final char[] hex = new char[] {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};  
    //默认密钥  
    private String encryptKey = "12345678";  
      
    //密钥缩位映射表1，共56位  
    //左边28位  
    static final int []keyMapC1 =new int[]{57,49,41,33,25,17,9,   
        1,58,50,42,34,26,18,10,2,59,51,43,35,27, 19,11,3,60,52,44,36          
    };  
    //右边28位  
    static final int []keyMapD1 = new int[]{63,55,47,39,31,23,15,   
        7,62,54,46,38,30,22,14,6,61,53,45,37,29,21,13,5,28,20,12,4    
    };  
      
    //密钥缩位表2，共48位  
    static final int []keyMap2 = new int[]{14,17,11,24,1,5,   
        3,28,15,6,21,10,23,19,12,4,26,8,16,7,27,20,13,2,   
        41,52,31,37,47,55,30,40,51,45,33,48,44,49,39,56,34,53,   
        46,42,50,36,29,32         
    };  
      
    //密钥变换循环移位表,16位  
    static final int []ccmovebit = new int[]{1,1,2,2,2,2,2,2,1,2,2,2,2,2,2,1};  
      
    //密钥产生过程中使用的临时数组  
    private int [][]C = new int[17][28];  
    private int [][]D = new int[17][28];  
    private int [][]keyMap = new int[17][48];  
      
    //开始S盒加密时换位表1,64位  
    static final int ip[] = {58,50,42,34,26,18,10,2,60,52,44,36,28,20,12,4,   
            62,54,46,38,30,22,14,6,64,56,48,40,32,24,16,8,   
            57,49,41,33,25,17,9,1,59,51,43,35,27,19,11,3,   
            61,53,45,37,29,21,13,5, 63,55,47,39,31,23,15,7   
        };  
      
    //结束S盒加密时换位表2,64位  
    static final  int _ip[] = {40,8,48,16,56,24,64,32,39,7,47,15,55,23,63,31,   
        38,6,46,14,54,22,62,30,37,5,45,13,53,21,61,29,   
        36,4,44,12,52,20,60,28,35,3,43,11,51,19,59,27,   
        34,2,42,10,50,18,58,26,33,1,41,9,49,17,57,25   
        };  
      
     //放大换位表: 32bit->48bit  
    static final int e[] = {32,1, 2, 3, 4, 5,4, 5, 6, 7, 8, 9,   
            8, 9, 10,11,12,13,12,13,14,15,16,17,   
            16,17,18,19,20,21,20,21,22,23,24,25,   
            24,25,26,27,28,29,28,29,30,31,32,1   
            };  
      
    //缩小换位表：48bit->32bit  
    static final int e2[] = {16,7,20,21,29,12,28,17,1,15,23,26,   
        5,18,31,10,2,8,24,14,32,27,3,9,19,13,30,6,22,11,4,25  
        };  
      
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
      
    /** 
     * 描述： 构造方法，使用默认密钥--abcd1234 
     */  
    Des_Test(){  
        genSubKey();  
    }  
      
    /** 
     * 描述： 构造方法 
     * @param key  指定加密密钥 
     */  
    Des_Test(String key){  
        this.encryptKey = key;  
        genSubKey();  
    }  
      
    /** 
     * 描述：将Byte数组存储Bit型数据转换为Byte数组存储的Byte型数据 
     * 作者：李海伟 
     * 时间：2012-3-16 上午11:38:40 
     * @param a  存储Bit型数据的数组 
     * @param b  存储Byte型数据的数组 
     */  
    private void bit2Byte(byte[] a,byte[] b){  
        int i=0,value=0,base=0;  
        for(i=0;i<b.length;i++){  
            base = i*8;  
            value = a[base+0]*128 + a[base+1]*64 + a[base+2]*32 + a[base+3]*16 + a[base+4]*8 + a[base+5]*4 + a[base+6]*2 +a[base+7]*1;  
            b[i] = (byte)value;  
        }  
    }  
      
    /** 
     * 描述：将ASCII码字符串转换为二进制数组 
     * 作者：李海伟 
     * 时间：2012-3-7 下午03:44:44 
     * @param bit  8字节的源字符串 
     * @param des  转换后的二进制串（位码由高到低排列） 
     * @return 
     */  
    private  void ASCII2Bit( byte bit[],byte des[])  
    {  
          
        for(int i=0;i<8;i++){  
            for(int j=0;j<8;j++){  
                des[i*8+j] = (byte)((bit[i] >> (7-j))&0x01);  
            }  
        }  
    }  
  
    /** 
     * 描述：将二进制数组转化为十六进制字符串 
     * 作者：李海伟 
     * 时间：2012-3-14 下午04:42:31 
     * @param a   以byte数组存储的二进制数据 
     * @return    十六进制的字符串 
     */  
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
  
    /** 
     * 描述：把十六进制的字符串转换为比特串 
     * 作者：李海伟 
     * 时间：2012-3-16 上午10:18:41 
     * @param hex 
     * @param bit 
     */  
    private void hex2Bit(String hex,byte []bit){  
        String strTmp ="";  
        int i = 0;  
        for(i=0;i<hex.length();i++){ 
        	System.out.println("charAt:"+hex.charAt(i));
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
        }  
    }  
    /** 
     * 描述：根据给定的字符串生成加密秘钥 
     * 作者：李海伟 
     * 时间：2012-3-14 下午01:53:16 
     * @param oldKey 
     * @param newKey 
     */  
    private void genSubKey(){  
          int i, k, rol = 0;           
          int []tmp = new int[56];  
          //16次循环左移对应的左移位数   
          byte []oldKey = this.encryptKey.getBytes();   
          byte[] oldkey_byte = new byte[64];  
          ASCII2Bit(oldKey, oldkey_byte);   
        //IP变换,分为C,D两部分   
          for(i=0;i<28;i++){  
              C[0][i] = oldkey_byte[keyMapC1[i]-1];  
          }  
          for(i=0;i<28;i++){  
              D[0][i] = oldkey_byte[keyMapD1[i]-1];  
          }       
          //分别生成16个子密钥   
          for(i = 1; i < 17; i++)   
          {   
            //循环左移   
            rol = ccmovebit[i-1];   
            //合并左移后的结果   
            for(k = 0; k < 28; k++){  
                C[i][k] = C[i-1][(k+rol)%28];  
                D[i][k] = D[i-1][(k+rol)%28];  
            }  
            //合并C和D  
            for(k=0;k<28;k++){  
                tmp[k] = C[i][k];  
                tmp[k+28] = D[i][k];  
            }  
            //位变换   
            for(k = 0; k < 48; k++)   
                keyMap[i][k] = tmp[keyMap2[k] - 1];   
          }   
    }  
    /** 
     * 描述：进行S盒变换 
     * 作者：李海伟 
     * 时间：2012-3-16 上午09:36:26 
     * @param n   第n轮加密 
     * @param ll  加密前左部分 
     * @param rr  加密前右部分 
     * @param LL  加密后左部分 
     * @param RR  加密后右部分 
     */  
    private void F_Sbox(int n, byte[] ll, byte[] rr, byte[] LL, byte[] RR){  
        int i, j,y,z,k,m;  
        byte[] buffer = new byte[64], tmp = new byte[32],rtnByte = new byte[8] ;  
          
        for (i = 0; i < 48; i++)  //明文32比特变48比特  
                buffer[i] = rr[e[i] - 1];  
          
        //Ri与Ki进行不进位加法  
        for (i = 0; i < 48; i++)  
            buffer[i] = (byte) ((buffer[i] + (byte)(keyMap[n][i]))& 0x01);  
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
    /** 
     * 描述：分块对明文加密，每块包含8个字节 
     * 作者：李海伟 
     * 时间：2012-3-14 下午04:59:20 
     * @param m_byte 
     * @return 
     */  
    private String encryptByBlock(byte[]m_byte){  
          
          byte [] t_bit = new byte [64];  
          byte[] l_byte  = new byte[32];   
          byte[] r_byte = new byte[32];   
          byte[][] l_bit = new byte[17][32];   
          byte[][] r_bit = new byte[17][32];  
          byte []c_bit = new byte[64];  
          byte[] e_bit = new byte[64];  
          //数组初始化  
          int i, j;   
          //将待加密字串变换成01串   
          ASCII2Bit(m_byte, t_bit); 
          System.out.println("我是二进制位数"+t_bit.length); 
          for( i=0;i<t_bit.length;i++)
          {
        	 System.out.println(t_bit[i]); 
          }
          //按照ip表对待加密字串进行位变换,分为左右两部分   
          for(i = 0; i < 32; i++) {  
              l_byte[i] = t_bit[ip[i]-1];  
              r_byte[i] = t_bit[ip[i+32]-1];  
          }  
            
          //16轮加密  
          for(i=1;i<17;i++){  
              F_Sbox(i, l_byte, r_byte, l_bit[i], r_bit[i]);  
                
              for (j = 0; j < 32; j++) {  
                  l_byte[j] = l_bit[i][j];  
                  r_byte[j] = r_bit[i][j];  
                    }  
          }  
          
          //组合最终迭代结果并第二次IP变换   
          for(i=0;i<32;i++){  
              c_bit[i] = r_bit[16][i];  
              c_bit[i+32] = l_bit[16][i];  
          }  
          for(i = 0; i < 64; i++) {  
              e_bit[i] = c_bit[_ip[i]-1];  
          }  
            
          String encryptText = bit2Hex(e_bit);  
          System.out.println("我是16进制数："+encryptText);
          return encryptText;  
          
    }  
      
    /** 
     * 描述：分块解密密文数据 
     * 作者：李海伟 
     * 时间：2012-3-16 上午11:34:42 
     * @param enTxt 
     * @param b 
     */  
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
          for(i = 0; i < 32; i++) {  
              l_byte[i] = enTxt[ip[i]-1];  
              r_byte[i] = enTxt[ip[i+32]-1];  
          }  
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
              e_bit[i] = c_bit[_ip[i]-1];  
          }  
          for(i=0;i<64;i++){  
              b[i]=e_bit[i];  
          }  
    }  
  
    /** 
     * 描述：对传入的明文字符串进行加密 
     * 作者：李海伟 
     * 时间：2012-3-15 上午09:51:19 
     * @param txt  待加密明文字符串 
     * @return  加密后的密文字符串 
     */  
    public String encrypt(String txt)  
    {  
          
         int dataLen = txt.length();//待加密数据的长度  
         System.out.println("字符串长度："+dataLen);
         byte []in = txt.getBytes();  
         System.out.println("字节数："+in.length);//获得字节数
         for(int i=0;i<in.length;i++)
         {
        	 System.out.println("输出编码字节："+in[i]);
         }
        String s=new String(in);
        System.out.println("我是原文："+s);
        byte te8bit[]=new byte[]{0,0,0,0,0,0,0,0};   
           
        // 这是待加密字串的调整长度   
        // 如果原始长度是8的整数倍，则调整长度的值和原来的长度一样   
        // 如果原始长度不是8的整数倍，则调整长度的值是能被8整除且不大于原来长度的最大整数。   
        //也就是不需要补齐的块的总长度。   
        int te_fixlen = dataLen - (dataLen % 8);  
          
        // 将待加密密文以8为单位分段，把最后长度不足8的一段存储到te8bit中。   
        for(int i = 0; i < (dataLen % 8); i++)   
            te8bit[i] = in[te_fixlen + i];   
       String strRtn = "";  
            // 将待加密字串分以8字节为单位分段加密   
        byte src[]= new byte[8];  
        for(int i = 0; i < te_fixlen; i += 8)  
        {  
            for(int k=0;k<8;k++)  
            {  
                src[k] = in[i+k];  
            }  
            strRtn += encryptByBlock(src);//传进去8个字节  
        }  
        System.out.println("我是16进制数："+strRtn);       
        // 如果待加密字串不是8的整数倍，则将最后一段补齐（补0）后加密   
        if(dataLen % 8 != 0)   
            strRtn += encryptByBlock(te8bit);  
        return strRtn;  
    }  
    /** 
     * 描述：对传入密文进行解密操作，并返回原文 
     * 作者：李海伟 
     * 时间：2012-3-19 上午10:11:31 
     * @param m_txt 密文字符串 
     * @param len 原文长度 
     * @return 
     */  
    public String dencrypt(String m_txt,int len)  
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
        return new String(c_byte);  
    }  
    /** 
     * 描述：以字符串形式返回当前使用的密钥 
     * 作者：李海伟 
     * 时间：2012-3-15 上午11:25:52 
     */  
    public String getKey(){  
          
        return this.encryptKey;  
    }  
      
    public void test (){  
        byte []a= new byte[32*4];  
        String srcStr = "12345678";//002032174  
        String str=encrypt(srcStr);  
        System.out.println("原文：["+srcStr+"]\n加密后的密文：["+str+"]");  
        String srcStr2 = dencrypt(str,srcStr.getBytes().length);  
        System.out.println("解密后的原文："+srcStr2);  
    }  
    public static void main(String []args){  
        Des_Test des = new Des_Test("abcdabcd");  
        des.test();  
        System.out.println("excute success!");  
    }  
} 