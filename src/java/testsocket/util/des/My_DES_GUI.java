package testsocket.util.des; /*
 * create by weikunpeng
 * 2018/5/19 19:39
 */



import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.UnsupportedEncodingException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
public class My_DES_GUI{


    public static void main(String[] args) throws UnsupportedEncodingException {
        Operate operate=new Operate("123456781","12345678");
        System.out.println();
        //operate.jiemi("12345678","12345678");

       // operate.jiemi();
    }

}