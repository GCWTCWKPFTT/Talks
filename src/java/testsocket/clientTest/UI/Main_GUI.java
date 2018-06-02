package testsocket.clientTest.UI;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import testsocket.bean.message.AS_C;
import testsocket.bean.message.Authenticator;
import testsocket.bean.message.C_AS;
import testsocket.bean.message.TGS_C;
import testsocket.clientTest.SendToAs;
import testsocket.clientTest.SendToTgs;
import testsocket.clientTest.SendToV;
import testsocket.common.Const;
import testsocket.util.DES;
import testsocket.util.MD5Util;

public class Main_GUI {

	private JFrame frame;
	private JTextField textField;// 用户名
	private JPasswordField passwordField;

	private JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main_GUI window = new Main_GUI();
					window.frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main_GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("\u767B\u5F55\u754C\u9762");
		frame.setBounds(100, 100, 500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("\u8D26\u53F7");
		lblNewLabel.setBounds(108, 86, 48, 20);
		frame.getContentPane().add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(201, 83, 188, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("\u5BC6\u7801");
		lblNewLabel_1.setBounds(108, 128, 69, 20);
		frame.getContentPane().add(lblNewLabel_1);

		passwordField = new JPasswordField();
		passwordField.setBounds(201, 125, 188, 23);
		frame.getContentPane().add(passwordField);

		JButton button = new JButton("\u767B\u5F55");

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String head = "11";
				String IDc = textField.getText();
				String password = passwordField.getText();
				String Kc = MD5Util.MD5EncodeUtf8(password);

				boolean Temp1 = false;// 检验验证步骤的顺利进行
				boolean Temp2 = false;
				boolean Temp3 = false;

				String IDtgs = Const.ID_TGS;

				long TS1 = System.currentTimeMillis();

				C_AS c_as = new C_AS(IDc, IDtgs, TS1);
				String c_as_message = "01 " + c_as.getC_AS();
				String as_c_message = null;
				try {
					as_c_message = SendToAs.SendToAS(c_as_message, Const.IP_AS, Const.PORT_AS);

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(as_c_message.substring(0, 2).equals("07")) {
					if(as_c_message.substring(3,5).equals("01")) {
						String Wrong = as_c_message.substring(6);
						textArea.append("AS认证失败:  " + Wrong + "\n");
						
					}
				}
				else if (as_c_message.substring(0, 2).equals("02")) {
					Temp1 = true;
					String as_c_message_C = as_c_message.substring(3);
					String as_c_message_M = null;
					try {
						as_c_message_M = DES.des(as_c_message_C, Kc, 2);
						System.out.println(as_c_message_M);
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					AS_C as_c = new AS_C();
					boolean x = false;
					try {
						x = as_c.DealwithMessage(as_c_message_M);
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (x != true) {
						System.out.println("deal as_c wrong!");
						Temp1 = false;
					}

					String IDv = Const.ID_V;
					String tickeTGS = as_c.getTicketTGS();

					String authenticator;

					String Kctgs = as_c.getKc_tgs();

					// IDc 已经定义了
//					InetAddress addr = InetAddress.getLocalHost();
					String ADc = null;
					try {
						ADc = InetAddress.getLocalHost().getHostAddress();
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					long TS3 = System.currentTimeMillis();
					Authenticator authenticatorTGS_M = new Authenticator(IDc, ADc, TS3);
					String authenticatorTGS_C = null;
					try {
						authenticatorTGS_C = DES.des(authenticatorTGS_M.getAuthenticator(), Kctgs, 1);
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					String c_tgs_message = "03 " + IDv + " " + tickeTGS + " " + authenticatorTGS_C;

					if (Temp1) {
						textArea.append("C->AS:  " + c_as_message + "\n");
						textArea.append("AS->C:  " + as_c_message + "\n");
						textArea.append("     :  " + as_c_message_M + "\n");

						textArea.append("AS认证成功！！！！！！！！！！！！\n");
						String tgs_c_message = null;
						try {
							tgs_c_message = SendToTgs.SendToTGS(c_tgs_message, Const.IP_TGS, Const.PORT_TGS);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						if (tgs_c_message.substring(0, 2).equals("04")) {
							Temp2 = true;
							String tgs_c_message_C = tgs_c_message.substring(3);
							String tgs_c_message_M = null;
							try {
								tgs_c_message_M = DES.des(tgs_c_message_C, Kctgs, 2);
							} catch (UnsupportedEncodingException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							TGS_C tgs_c = new TGS_C();
							try {
								tgs_c.DealwithMessage(tgs_c_message_M);
							} catch (UnsupportedEncodingException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							String Kcv = tgs_c.getKc_v();
							String stringTicketV = tgs_c.getTicketV();
							long TS5 = System.currentTimeMillis();

							Authenticator authenticatorV_M = new Authenticator(IDc, ADc, TS5);
							String authenticatorV_C = null;
							try {
								authenticatorV_C = DES.des(authenticatorV_M.getAuthenticator(), Kcv, 1);
							} catch (UnsupportedEncodingException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							String c_v_message = "05 " + stringTicketV + " " + authenticatorV_C;
							if (Temp2) {
								textArea.append("\n");
								textArea.append("C->TGS:  " + c_tgs_message + "\n");
								textArea.append("TGS->C:  " + tgs_c_message + "\n");
								textArea.append("      :  " + tgs_c_message_M + "\n");
								textArea.append("TGS认证成功！！！！！！！！！！！！\n");
								String v_c_message = null;
								try {
									v_c_message = SendToV.SendTov(c_v_message, Const.IP_V, Const.PORT_V);
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								if (v_c_message.substring(0, 2).equals("06")) {
									Temp3 = true;
									String v_c_message_C = v_c_message.substring(3);
									String v_c_message_M = null;
									try {
										v_c_message_M = DES.des(v_c_message_C, Kcv, 2);
									} catch (UnsupportedEncodingException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									long TS5x = Long.parseLong(v_c_message_M);
									if (TS5x == (TS5 + 1)) {
										Temp3 = true;
									}
									if (Temp3) {
										textArea.append("\n");
										textArea.append("C->V  :" + c_v_message + "\n");
										textArea.append("V->C  :" + v_c_message + "\n");
										textArea.append("      :" + v_c_message_M + "\n");
										textArea.append("服务器认证成功！！！！！！！！！！！！\n");

										new Talk(textField.getText());
									}
									
								}

							}
						}
					}
					
				}

			}
		});
		button.setBounds(95, 196, 115, 29);
		frame.getContentPane().add(button);

		JButton button_1 = new JButton("\u6CE8\u518C");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Register window = new Register();
			}
		});
		button_1.setBounds(268, 196, 115, 29);
		frame.getContentPane().add(button_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 280, 478, 164);
		frame.getContentPane().add(scrollPane);

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
	}
}
