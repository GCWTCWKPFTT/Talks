package testsocket.Vserver;

import testsocket.common.Const;

import java.awt.EventQueue;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.awt.event.ActionEvent;

public class Server implements Runnable{

    private JFrame frame;
    private JTextField textField;
    private DefaultListModel listModel;
    private JTextField port;
    private ServerSocket serverSocket;
    private ServerThread serverThread;
    private boolean isStart = false;
    private ArrayList<ClientThread> clients;

    @Override
    public void run() {

        receive(Const.IP_V,Const.PORT_V);



    }

    /**
     * Launch the application.
     */



    static void receive(String ip, int port) {
        ServerSocket server = null;
        try {


            server = new ServerSocket(port);
            while (true){
            Socket socket = server.accept();

            System.out.println("等待client发送消息...");
            Vrunnable task = new Vrunnable(socket);

            new Thread(task).start();
           // Thread.sleep(10000);
                }


        } catch (Exception e) {
            System.out.println("e1" + e);
        } finally {
            try {
                if (server != null) {
                    server.close();
                }
            } catch (Exception e3) {
                System.out.println("e2" + e3);
            }

        }

    }
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                   Server server = new Server();
                    new Thread(server).start();



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
        frame.setVisible(true);

        JLabel label = new JLabel("\u804A\u5929\u5BA4\u4EBA\u6570");
        label.setBounds(15, 16, 85, 20);
        frame.getContentPane().add(label);

        textField = new JTextField();
        textField.setBounds(110, 13, 70, 26);
        textField.setText("0");
        frame.getContentPane().add(textField);
        textField.setColumns(10);

        JLabel label_1 = new JLabel("\u7AEF\u53E3\u53F7");
        label_1.setBounds(216, 19, 48, 20);
        frame.getContentPane().add(label_1);

        port = new JTextField("9997");
        port.setBounds(282, 13, 70, 26);
        frame.getContentPane().add(port);
        port.setColumns(10);

        final JButton button_1 = new JButton("\u505C\u6B62");
        button_1.setBounds(474, 12, 70, 29);
        frame.getContentPane().add(button_1);

        final JButton button = new JButton("\u542F\u52A8");
        button.setBounds(383, 12, 70, 29);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int port1;
                try {
                    port1 = Integer.parseInt(port.getText());
                    try {
                        clients = new ArrayList<ClientThread>();//存一个客户端数组
                        serverSocket = new ServerSocket(port1);//利用serversocket对指定端口监听
                        serverThread = new ServerThread(serverSocket);//指定线程最多数
                        serverThread.start();//开启单线程通信
                        isStart = true;
                    } catch (BindException e1) {
                        isStart = false;
                        throw new BindException("端口号已被占用，请换一个！");
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        isStart = false;
                        throw new BindException("启动服务器异常！");
                    }
                    JOptionPane.showMessageDialog(frame, "服务器成功启动!");
                    button.setEnabled(false);
                    port.setEnabled(false);
                    button_1.setEnabled(true);
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(frame, exc.getMessage(),
                            "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        frame.getContentPane().add(button);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 47, 100, 205);
        frame.getContentPane().add(scrollPane);


        listModel = new DefaultListModel();
        JList list = new JList(listModel);
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
        private int max;// 人数上限

        // 服务器线程的构造方法
        public ServerThread(ServerSocket serverSocket) {
            this.serverSocket = serverSocket;
        }

        public void run() {
            while (true) {// 不停的等待客户端的链接
                try {
                    Socket socket = serverSocket.accept();
					/*if (clients.size() == max) {// 如果已达人数上限
						BufferedReader r = new BufferedReader(
								new InputStreamReader(socket.getInputStream()));
						PrintWriter w = new PrintWriter(socket
								.getOutputStream());
						// 接收客户端的基本用户信息
						String inf = r.readLine();
						StringTokenizer st = new StringTokenizer(inf, "@");
						User_more user = new User_more(st.nextToken(), st.nextToken());
						// 反馈连接成功信息
						w.println("MAX@服务器：对不起，" + user.getName()
								+ user.getIp() + "，服务器在线人数已达上限，请稍后尝试连接！");
						w.flush();
						// 释放资源
						r.close();
						w.close();
						socket.close();
						continue;
					}*/
                    ClientThread client = new ClientThread(socket);
                    client.start();// 开启对此客户端服务的线程
                    clients.add(client);
                    //listModel.addElement(client.getUser().getName());// 更新在线列表
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    // 为一个客户端服务的线程
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

        // 客户端线程的构造方法
        public ClientThread(Socket socket) {
            try {
                this.socket = socket;
                reader = new BufferedReader(new InputStreamReader(socket
                        .getInputStream()));
                writer = new PrintWriter(socket.getOutputStream());
                // 接收客户端的基本用户信息
                String inf = reader.readLine();
                StringTokenizer st = new StringTokenizer(inf, "@");
                user = new User_more(st.nextToken(), st.nextToken());
                listModel.addElement(user.getName());
                // 反馈连接成功信息
                writer.println(user.getName() + user.getIp() + "与服务器连接成功!");
                writer.flush();
                // 反馈当前在线用户信息
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
                // 向所有在线用户发送该用户上线命令
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
        public void run() {// 不断接收客户端的消息，进行处理。
            String message = null;
            while (true) {
                try {
                    message = reader.readLine();// 接收客户端消息
                    if (message.equals("CLOSE"))// 下线命令
                    {
                        // 断开连接释放资源
                        reader.close();
                        writer.close();
                        socket.close();

                        // 向所有在线用户发送该用户的下线命令
                        for (int i = clients.size() - 1; i >= 0; i--) {
                            clients.get(i).getWriter().println(
                                    "DELETE@" + user.getName());
                            clients.get(i).getWriter().flush();
                        }

                       // listModel.removeElement(user.getName());// 更新在线列表

                        // 删除此条客户端服务线程
                        for (int i = clients.size() - 1; i >= 0; i--) {
                            if (clients.get(i).getUser() == user) {
                                ClientThread temp = clients.get(i);
                                clients.remove(i);// 删除此用户的服务线程
                                temp.stop();// 停止这条服务线程
                                return;
                            }
                        }
                    } else {
                        dispatcherMessage(message);// 转发消息
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
            message = source + "说：" + content;
            if (owner.equals("ALL")) {// 群发
                for (int i = clients.size() - 1; i >= 0; i--) {
                    clients.get(i).getWriter().println(message + "(多人发送)");
                    clients.get(i).getWriter().flush();
                }
            }
        }
    }
}
