package testsocket.AS;

import testsocket.Server;
import testsocket.User_more;
import testsocket.bean.User;
import testsocket.bean.message.AS_C;
import testsocket.bean.message.C_AS;
import testsocket.bean.message.TicketTGS;
import testsocket.dao.impl.DaoImpl;

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
import java.beans.PropertyVetoException;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class ASserver {


    void receive(String ip, int port) {
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            Socket socket = server.accept();

            System.out.println("等待client发送消息...");
            ASrunnable task = new ASrunnable(socket);

            new Thread(task).start();
            Thread.sleep(10000);


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


    public static void main(String[] args) throws PropertyVetoException, SQLException {


        ASserver aSserver = new ASserver();
        aSserver.receive("127.0.0.1", 9999);

    }
}
