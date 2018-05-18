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
    private Map<String, User_more> onLineUsers = new HashMap<String, User_more>();// 所有在线用户
    private MessageThread messageThread;// 负责接收消息的线程
    private boolean isConnected = false;
    private String hostIp="127.0.0.1";
    private String name="高超";
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
                    JOptionPane.showMessageDialog(frame, "已处于连接上状态，不要重复连接!",
                            "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    try {
                        port1 = Integer.parseInt(port.getText().trim());
                    } catch (NumberFormatException e2) {
                        throw new Exception("端口号不符合要求!端口为整数!");
                    }
                    if (name.equals("") || hostIp.equals("")) {
                        throw new Exception("姓名、服务器IP不能为空!");
                    }
                    boolean flag = connectServer(port1, hostIp, name);
                    if (flag == false) {
                        throw new Exception("与服务器连接失败!");
                    }
                    frame.setTitle(name);
                    JOptionPane.showMessageDialog(frame, "成功连接!");
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(frame, exc.getMessage(),
                            "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnNewButton.setBounds(221, 24, 115, 29);
        frame.getContentPane().add(btnNewButton);

        JButton btnNewButton_1 = new JButton("\u65AD\u5F00\u8FDE\u63A5");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!isConnected) {
                    JOptionPane.showMessageDialog(frame, "已处于断开状态，不要重复断开!",
                            "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    boolean flag = closeConnection();// 断开连接
                    if (flag == false) {
                        throw new Exception("断开连接发生异常！");
                    }
                    JOptionPane.showMessageDialog(frame, "成功断开!");
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(frame, exc.getMessage(),
                            "错误", JOptionPane.ERROR_MESSAGE);
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
                    closeConnection();// 关闭连接
                }
                System.exit(0);// 退出程序
            }
        });
    }
    // 执行发送
    public void sendmessage() {
        if (!isConnected) {
            JOptionPane.showMessageDialog(frame, "还没有连接服务器，无法发送消息！", "错误",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        String message = textField_1.getText().trim();
        if (message == null || message.equals("")) {
            JOptionPane.showMessageDialog(frame, "消息不能为空！", "错误",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        sendMessage(frame.getTitle() + "@" + "ALL" + "@" + message);
        textField_1.setText(null);
    }

    /**
     * 连接服务器
     *
     * @param port
     * @param hostIp
     * @param name
     */
    public boolean connectServer(int port, String hostIp, String name) {
        // 连接服务器
        try {
            socket = new Socket(hostIp, port);// 根据端口号和服务器ip建立连接
            writer = new PrintWriter(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(socket
                    .getInputStream()));
            sendMessage(name + "@" + socket.getLocalAddress().toString());
            // 开启接收消息的线程
            messageThread = new MessageThread(reader,textArea);
            messageThread.start();
            isConnected = true;// 已经连接上了
            return true;
        } catch (Exception e) {
            textArea.append("与端口号为：" + port + "    IP地址为：" + hostIp
                    + "   的服务器连接失败!" + "\n");
            isConnected = false;// 未连接上
            return false;
        }
    }
    private void Socket(int port) throws UnknownHostException, IOException {
        Socket socket = new Socket("localhost", port);
        OutputStream os = socket.getOutputStream();
        writer = new PrintWriter(os);// 将输出流打包成打印流
        isConnected = true;

    }
    /**
     * 客户端主动关闭连接
     */
    @SuppressWarnings("deprecation")
    public synchronized boolean closeConnection() {
        try {
            sendMessage("CLOSE");// 发送断开连接命令给服务器
            messageThread.stop();// 停止接受消息线程
            // 释放资源
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

        // 接收消息线程的构造方法
        public MessageThread(BufferedReader reader, JTextArea textArea) {
            this.reader = reader;
            this.textArea = textArea;
        }

        // 被动的关闭连接
        public synchronized void closeCon() throws Exception {
            // 清空用户列表
            //listModel.removeAllElements();
            // 被动的关闭连接释放资源
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
            }
            if (socket != null) {
                socket.close();
            }
            isConnected = false;// 修改状态为断开
        }

        public void run() {
            String message = "";
            while (true) {
                try {
                    message = reader.readLine();
                    StringTokenizer stringTokenizer = new StringTokenizer(
                            message, "/@");
                    String command = stringTokenizer.nextToken();// 命令
                    if (command.equals("CLOSE"))// 服务器已关闭命令
                    {
                        textArea.append("服务器已关闭!\n");
                        closeCon();// 被动的关闭连接
                        return;// 结束线程
                    } else if (command.equals("ADD")) {// 有用户上线更新在线列表
                        String username = "";
                        String userIp = "";
                        if ((username = stringTokenizer.nextToken()) != null
                                && (userIp = stringTokenizer.nextToken()) != null) {
                            User_more user = new User_more(username, userIp);
                            onLineUsers.put(username, user);
                            listModel.addElement(username);
                        }
                    } else if (command.equals("DELETE")) {// 有用户下线更新在线列表
                        String username = stringTokenizer.nextToken();
                        User_more user = (User_more) onLineUsers.get(username);
                        onLineUsers.remove(user);
                        listModel.removeElement(username);
                    } else if (command.equals("USERLIST")) {// 加载在线用户列表
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
                    } else if (command.equals("MAX")) {// 人数已达上限
                        textArea.append(stringTokenizer.nextToken()
                                + stringTokenizer.nextToken() + "\n");
                        closeCon();// 被动的关闭连接
                        JOptionPane.showMessageDialog(frame, "服务器缓冲区已满！", "错误",
                                JOptionPane.ERROR_MESSAGE);
                        return;// 结束线程
                    } else {// 普通消息
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
