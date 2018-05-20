package Des_Test;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class My_DES_GUI {
	  private JFrame jframe=new JFrame("DES加密解密");
	  private JPanel jpa1,jpa2,jpa3,jpa4;
	  private JButton button1=new JButton("加密");
	  private JButton button2=new JButton("解密");
	//  private JButton button3=new JButton("暴力解密");
	  private JLabel jla1=new JLabel("明文");
	  private JLabel jla2=new JLabel("秘钥");
	  private static JLabel jla3=new JLabel("密文");	
	  private JTextField ming=new JTextField("12345678",20);
	  private JPasswordField key=new JPasswordField("12345678",5);
	  static JTextField mi=new JTextField(30);
	  public  My_DES_GUI(){	 
    	  //jpa1.setLayout(new GridLayout());
    	  jpa1 = new JPanel();
    	  jpa2 = new JPanel();
    	  jpa3 = new JPanel();
    	  jpa4 = new JPanel();
    	  jpa1.add(jla1);
    	  jpa1.add(ming);
    	  jpa2.add(jla2);
    	  jpa2.add(key);
    	  jpa3.add(getJla3());
    	  jpa3.add(mi);
    	  jpa4.add(button1);
    	  jpa4.add(button2);
    	//  jpa4.add(button3);
    	  jframe.add(jpa1);
    	  jframe.add(jpa2);
    	  jframe.add(jpa3);
    	  jframe.add(jpa4);
    	  jframe.setSize(300,200); 
    	  jframe.setLocationRelativeTo(null);
    	  jframe.setLayout(new GridLayout(4,1));
    	  jframe.setVisible(true);
    	  jframe.setResizable(true);
    	  button1.addActionListener(new ActionListener() {
  			public void actionPerformed(ActionEvent e) {
  				try{
  				  Operate operate=new Operate(ming.getText(),key.getText());
  					
  				} catch (Exception exc) {
  					exc.printStackTrace();
  				}
  			}
  		});
    	  button2.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				try{
    					Operate operate=new Operate(ming.getText(),key.getText());
    					operate.jiemi(ming.getText());
    				
    				} catch (Exception exc) {
    					exc.printStackTrace();
    				}
    			}
    		});
    	/*  button3.addActionListener(new ActionListener() {
  			public void actionPerformed(ActionEvent e) {
  				try{
  					
  				} catch (Exception exc) {
  					exc.printStackTrace();
  				}
  			}
  		});*/
    	  myEvent();
	  }
	  private void myEvent(){  
          jframe.addWindowListener(new WindowAdapter(){  
              @Override  
              public void windowClosing(WindowEvent e) {  
                  // TODO Auto-generated method stub  
                  //super.windowClosing(e);  
                  System.exit(0);  
              }  
          });  
      }
	  public static void main(String args[])
		{
		     My_DES_GUI gui=new My_DES_GUI();
			
		}
	static JLabel getJla3() {
		return jla3;
	}
	public static void setJla3(JLabel jla3) {
		My_DES_GUI.jla3 = jla3;
	}
	
	  
}