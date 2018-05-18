package testsocket;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.awt.event.ActionEvent;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Talk {

	private JFrame frame;
	private JTextField port;
	private JTextField textField_1;
	private DefaultListModel listModel;
	private JTextArea textArea;
	private String[] str = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Staturday", "Sunday" };	
	private Socket socket;
	private PrintWriter writer;
	private BufferedReader reader;
	private Map<String, User_more> onLineUsers = new HashMap<String, User_more>();// ���������û�
	private MessageThread messageThread;// ���������Ϣ���߳�
	private boolean isConnected = false;
	private String hostIp="127.0.0.1";
	private String name="�߳�";
	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { Talk window = new Talk();
	 * window.frame.setVisible(true); } catch (Exception e) {
	 * e.printStackTrace(); } } }); }
	 */

	/**
	 * Create the application.
	 */
	public Talk() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("\u804A\u5929\u754C\u9762");
		frame.setBounds(100, 100, 517, 600);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(0, 64, 60, 236);
		frame.getContentPane().add(scrollPane_2);
		listModel = new DefaultListModel();
		JList list = new JList(listModel);
		scrollPane_2.setViewportView(list);
         
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(75, 65, 417, 235);
		frame.getContentPane().add(scrollPane);

	    textArea = new JTextArea();
		scrollPane.setViewportView(textArea);

		JLabel label = new JLabel("\u7AEF\u53E3\u53F7");
		label.setBounds(12, 28, 69, 20);
		frame.getContentPane().add(label);

		port = new JTextField();
		port.setBounds(96, 25, 110, 26);
		frame.getContentPane().add(port);
		port.setColumns(10);

		JButton btnNewButton = new JButton("\u8FDE\u63A5");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int port1;
				if (isConnected) {
					JOptionPane.showMessageDialog(frame, "�Ѵ���������״̬����Ҫ�ظ�����!",
							"����", JOptionPane.ERROR_MESSAGE);
					return;
				}
				try {
					try {
						port1 = Integer.parseInt(port.getText().trim());
					} catch (NumberFormatException e2) {
						throw new Exception("�˿ںŲ�����Ҫ��!�˿�Ϊ����!");
					}					
					if (name.equals("") || hostIp.equals("")) {
						throw new Exception("������������IP����Ϊ��!");
					}
					boolean flag = connectServer(port1, hostIp, name);
					if (flag == false) {
						throw new Exception("�����������ʧ��!");
					}
					frame.setTitle(name);
					JOptionPane.showMessageDialog(frame, "�ɹ�����!");
				} catch (Exception exc) {
					JOptionPane.showMessageDialog(frame, exc.getMessage(),
							"����", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnNewButton.setBounds(221, 24, 115, 29);
		frame.getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("\u65AD\u5F00\u8FDE\u63A5");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!isConnected) {
					JOptionPane.showMessageDialog(frame, "�Ѵ��ڶϿ�״̬����Ҫ�ظ��Ͽ�!",
							"����", JOptionPane.ERROR_MESSAGE);
					return;
				}
				try {
					boolean flag = closeConnection();// �Ͽ�����
					if (flag == false) {
						throw new Exception("�Ͽ����ӷ����쳣��");
					}
					JOptionPane.showMessageDialog(frame, "�ɹ��Ͽ�!");
				} catch (Exception exc) {
					JOptionPane.showMessageDialog(frame, exc.getMessage(),
							"����", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnNewButton_1.setBounds(351, 24, 115, 29);
		frame.getContentPane().add(btnNewButton_1);

		JLabel label_1 = new JLabel("\u6D88\u606F\u533A");
		label_1.setBounds(12, 338, 69, 20);
		frame.getContentPane().add(label_1);

		textField_1 = new JTextField();
		textField_1.setBounds(96, 335, 225, 26);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		textField_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendmessage();
			}
		}); 
		JButton button = new JButton("\u53D1\u9001");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendmessage();
			}
		});
		button.setBounds(351, 334, 115, 29);
		frame.getContentPane().add(button);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 391, 492, 160);
		frame.getContentPane().add(scrollPane_1);

		JTextArea textArea_1 = new JTextArea();
		scrollPane_1.setViewportView(textArea_1);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (isConnected) {
					closeConnection();// �ر�����
				}
				System.exit(0);// �˳�����
			}
		});
	}
	// ִ�з���
		public void sendmessage() {
			if (!isConnected) {
				JOptionPane.showMessageDialog(frame, "��û�����ӷ��������޷�������Ϣ��", "����",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			String message = textField_1.getText().trim();
			if (message == null || message.equals("")) {
				JOptionPane.showMessageDialog(frame, "��Ϣ����Ϊ�գ�", "����",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			sendMessage(frame.getTitle() + "@" + "ALL" + "@" + message);
			textField_1.setText(null);
		}

	/**
	 * ���ӷ�����
	 * 
	 * @param port
	 * @param hostIp
	 * @param name
	 */
	public boolean connectServer(int port, String hostIp, String name) {
		// ���ӷ�����
		try {
			socket = new Socket(hostIp, port);// ���ݶ˿ںźͷ�����ip��������
			writer = new PrintWriter(socket.getOutputStream());
			reader = new BufferedReader(new InputStreamReader(socket
					.getInputStream()));
			sendMessage(name + "@" + socket.getLocalAddress().toString());
			// ����������Ϣ���߳�
			messageThread = new MessageThread(reader,textArea);
			messageThread.start();
			isConnected = true;// �Ѿ���������
			return true;
		} catch (Exception e) {
			textArea.append("��˿ں�Ϊ��" + port + "    IP��ַΪ��" + hostIp
					+ "   �ķ���������ʧ��!" + "\n");
			isConnected = false;// δ������
			return false;
		}
	}
	private void Socket(int port) throws UnknownHostException, IOException {
		Socket socket = new Socket("localhost", port);
		OutputStream os = socket.getOutputStream();
		writer = new PrintWriter(os);// �����������ɴ�ӡ��
	    isConnected = true;
		
	}
	/**
	 * �ͻ��������ر�����
	 */
	@SuppressWarnings("deprecation")
	public synchronized boolean closeConnection() {
		try {
			sendMessage("CLOSE");// ���ͶϿ����������������
			messageThread.stop();// ֹͣ������Ϣ�߳�
			// �ͷ���Դ
			if (reader != null) {
				reader.close();
			}
			if (writer != null) {
				writer.close();
			}
			if (socket != null) {
				socket.close();
			}
			isConnected = false;
			return true;
		} catch (IOException e1) {
			e1.printStackTrace();
			isConnected = true;
			return false;
		}
	}
	class MessageThread extends Thread {
		private BufferedReader reader;
		private JTextArea textArea;

		// ������Ϣ�̵߳Ĺ��췽��
		public MessageThread(BufferedReader reader, JTextArea textArea) {
			this.reader = reader;
			this.textArea = textArea;
		}

		// �����Ĺر�����
		public synchronized void closeCon() throws Exception {
			// ����û��б�
			//listModel.removeAllElements();
			// �����Ĺر������ͷ���Դ
			if (reader != null) {
				reader.close();
			}
			if (writer != null) {
				writer.close();
			}
			if (socket != null) {
				socket.close();
			}
			isConnected = false;// �޸�״̬Ϊ�Ͽ�
		}

		public void run() {
			String message = "";
			while (true) {
				try {
					message = reader.readLine();
					StringTokenizer stringTokenizer = new StringTokenizer(
							message, "/@");
					String command = stringTokenizer.nextToken();// ����
					if (command.equals("CLOSE"))// �������ѹر�����
					{
						textArea.append("�������ѹر�!\n");
						closeCon();// �����Ĺر�����
						return;// �����߳�
					} else if (command.equals("ADD")) {// ���û����߸��������б�
						String username = "";
						String userIp = "";
						if ((username = stringTokenizer.nextToken()) != null
								&& (userIp = stringTokenizer.nextToken()) != null) {
							User_more user = new User_more(username, userIp);
							onLineUsers.put(username, user);
							listModel.addElement(username);
						}
					} else if (command.equals("DELETE")) {// ���û����߸��������б�
						String username = stringTokenizer.nextToken();
						User_more user = (User_more) onLineUsers.get(username);
						onLineUsers.remove(user);
						listModel.removeElement(username);
					} else if (command.equals("USERLIST")) {// ���������û��б�
						int size = Integer
								.parseInt(stringTokenizer.nextToken());
						String username = null;
						String userIp = null;
						for (int i = 0; i < size; i++) {
							username = stringTokenizer.nextToken();
							userIp = stringTokenizer.nextToken();
							User_more user = new User_more(username, userIp);
							onLineUsers.put(username, user);
							listModel.addElement(username);
						}
					} else if (command.equals("MAX")) {// �����Ѵ�����
						textArea.append(stringTokenizer.nextToken()
								+ stringTokenizer.nextToken() + "\n");
						closeCon();// �����Ĺر�����
						JOptionPane.showMessageDialog(frame, "������������������", "����",
								JOptionPane.ERROR_MESSAGE);
						return;// �����߳�
					} else {// ��ͨ��Ϣ
						textArea.append(message + "\n");
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	private void sendMessage(String message){
		writer.println(message);
		writer.flush();
	
}
}
