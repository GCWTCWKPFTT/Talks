package testsocket;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JToolBar;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class Server {

	private JFrame frame;
	private JTextField textField;
	private JTextField port;
	private ServerSocket serverSocket;
	private ServerThread serverThread;
	private boolean isStart = false;
	private ArrayList<ClientThread> clients;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Server window = new Server();
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
	public Server() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("\u670D\u52A1\u5668");
		frame.setBounds(100, 100, 600, 430);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("\u804A\u5929\u5BA4\u4EBA\u6570");
		label.setBounds(15, 16, 85, 20);
		frame.getContentPane().add(label);
		
		textField = new JTextField();
		textField.setBounds(110, 13, 70, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel label_1 = new JLabel("\u7AEF\u53E3\u53F7");
		label_1.setBounds(216, 19, 48, 20);
		frame.getContentPane().add(label_1);
		
		port = new JTextField();
		port.setBounds(282, 13, 70, 26);
		frame.getContentPane().add(port);
		port.setColumns(10);
		
		JButton button_1 = new JButton("\u505C\u6B62");
		button_1.setBounds(474, 12, 70, 29);
		frame.getContentPane().add(button_1);
		
		JButton button = new JButton("\u542F\u52A8");
		button.setBounds(383, 12, 70, 29);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int port1;
				try {						
		            	port1 = Integer.parseInt(port.getText());
		            	try {
		        			clients = new ArrayList<ClientThread>();//��һ���ͻ�������
		        			serverSocket = new ServerSocket(port1);//����serversocket��ָ���˿ڼ���
		        			serverThread = new ServerThread(serverSocket);//ָ���߳������
		        			serverThread.start();//�������߳�ͨ��
		        			isStart = true;
		        		} catch (BindException e1) {
		        			isStart = false;
		        			throw new BindException("�˿ں��ѱ�ռ�ã��뻻һ����");
		        		} catch (Exception e1) {
		        			e1.printStackTrace();
		        			isStart = false;
		        			throw new BindException("�����������쳣��");
		        		}
					JOptionPane.showMessageDialog(frame, "�������ɹ�����!");
					button.setEnabled(false);				
				 	port.setEnabled(false);
					button_1.setEnabled(true);
				} catch (Exception exc) {
					JOptionPane.showMessageDialog(frame, exc.getMessage(),
							"����", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		frame.getContentPane().add(button);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 47, 100, 205);
		frame.getContentPane().add(scrollPane);
		
		JList list = new JList();
		scrollPane.setViewportView(list);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(98, 47, 480, 205);
		frame.getContentPane().add(scrollPane_1);
		
		JTextArea textArea = new JTextArea();
		scrollPane_1.setViewportView(textArea);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(0, 250, 578, 124);
		frame.getContentPane().add(scrollPane_2);
	}
	class ServerThread extends Thread {
		private ServerSocket serverSocket;
		private int max;// ��������

		// �������̵߳Ĺ��췽��
		public ServerThread(ServerSocket serverSocket) {
			this.serverSocket = serverSocket;
		}

		public void run() {
			while (true) {// ��ͣ�ĵȴ��ͻ��˵�����
				try {
					Socket socket = serverSocket.accept();
					/*if (clients.size() == max) {// ����Ѵ���������
						BufferedReader r = new BufferedReader(
								new InputStreamReader(socket.getInputStream()));
						PrintWriter w = new PrintWriter(socket
								.getOutputStream());
						// ���տͻ��˵Ļ����û���Ϣ
						String inf = r.readLine();
						StringTokenizer st = new StringTokenizer(inf, "@");
						User_more user = new User_more(st.nextToken(), st.nextToken());
						// �������ӳɹ���Ϣ
						w.println("MAX@���������Բ���" + user.getName()
								+ user.getIp() + "�����������������Ѵ����ޣ����Ժ������ӣ�");
						w.flush();
						// �ͷ���Դ
						r.close();
						w.close();
						socket.close();
						continue;
					}*/
					ClientThread client = new ClientThread(socket);
					client.start();// �����Դ˿ͻ��˷�����߳�
					clients.add(client);
					//listModel.addElement(client.getUser().getName());// ���������б�
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	// Ϊһ���ͻ��˷�����߳�
		class ClientThread extends Thread {
			private Socket socket;
			private BufferedReader reader;
			private PrintWriter writer;
			private User_more user;

			public BufferedReader getReader() {
				return reader;
			}

			public PrintWriter getWriter() {
				return writer;
			}

			public User_more getUser() {
				return user;
			}

			// �ͻ����̵߳Ĺ��췽��
			public ClientThread(Socket socket) {
				try {
					this.socket = socket;
					reader = new BufferedReader(new InputStreamReader(socket
							.getInputStream()));
					writer = new PrintWriter(socket.getOutputStream());
					// ���տͻ��˵Ļ����û���Ϣ
					String inf = reader.readLine();
					StringTokenizer st = new StringTokenizer(inf, "@");
					user = new User_more(st.nextToken(), st.nextToken());
					// �������ӳɹ���Ϣ
					writer.println(user.getName() + user.getIp() + "����������ӳɹ�!");
					writer.flush();
					// ������ǰ�����û���Ϣ
					if (clients.size() > 0) {
						String temp = "";
						for (int i = clients.size() - 1; i >= 0; i--) {
							temp += (clients.get(i).getUser().getName() + "/" + clients
									.get(i).getUser().getIp())
									+ "@";
						}
						writer.println("USERLIST@" + clients.size() + "@" + temp);
						writer.flush();
					}
					// �����������û����͸��û���������
					for (int i = clients.size() - 1; i >= 0; i--) {
						clients.get(i).getWriter().println(
								"ADD@" + user.getName() + user.getIp());
						clients.get(i).getWriter().flush();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@SuppressWarnings("deprecation")
			public void run() {// ���Ͻ��տͻ��˵���Ϣ�����д���
				String message = null;
				while (true) {
					try {
						message = reader.readLine();// ���տͻ�����Ϣ
						if (message.equals("CLOSE"))// ��������
						{
							// �Ͽ������ͷ���Դ
							reader.close();
							writer.close();
							socket.close();

							// �����������û����͸��û�����������
							for (int i = clients.size() - 1; i >= 0; i--) {
								clients.get(i).getWriter().println(
										"DELETE@" + user.getName());
								clients.get(i).getWriter().flush();
							}

							//listModel.removeElement(user.getName());// ���������б�

							// ɾ�������ͻ��˷����߳�
							for (int i = clients.size() - 1; i >= 0; i--) {
								if (clients.get(i).getUser() == user) {
									ClientThread temp = clients.get(i);
									clients.remove(i);// ɾ�����û��ķ����߳�
									temp.stop();// ֹͣ���������߳�
									return;
								}
							}
						} else {
							dispatcherMessage(message);// ת����Ϣ
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			public void dispatcherMessage(String message) {
				StringTokenizer stringTokenizer = new StringTokenizer(message, "@");
				String source = stringTokenizer.nextToken();
				String owner = stringTokenizer.nextToken();
				String content = stringTokenizer.nextToken();
				message = source + "˵��" + content;
				if (owner.equals("ALL")) {// Ⱥ��
					for (int i = clients.size() - 1; i >= 0; i--) {
						clients.get(i).getWriter().println(message + "(���˷���)");
						clients.get(i).getWriter().flush();
					}
				}
			}
		}
}
