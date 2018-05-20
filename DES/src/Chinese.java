import java.util.Arrays;
public class Chinese {
    /**
     * 将byte转换为一个长度为8的boolean数组（每bit代表一个boolean值）
     * 
     * @param b byte
     * @return boolean数组
     */
    public static int[] getBooleanArray(byte b) {
        int[] array = new int[8];
        for (int i = 7; i >= 0; i--) { //对于byte的每bit进行判定
           if((b & 1) == 1){
        	   
        	 array[i]=1;  
        	             //判定byte的最后一位是否为1，若为1，则是true；否则是false
           }
            b = (byte) (b >> 1);       //将byte右移一位
        }
        return array;
    }
    public static void main(String[] args) {
        byte b = '1'; // 0011 0101
        System.out.println(Arrays.toString(getBooleanArray(b)));
    }
}