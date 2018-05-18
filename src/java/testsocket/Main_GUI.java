package testsocket;

import testsocket.dao.impl.DaoImpl;

import java.awt.*;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.sql.SQLException;


public class Main_GUI {

    private JFrame frame;
    private JTextField textField;//用户名
    private JPasswordField passwordField;

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

                DaoImpl dao=new DaoImpl();

                boolean temp=false;
                try {
                    temp=dao.Login(textField.getText().toString(),passwordField.getText().toString());

                } catch (PropertyVetoException e1) {
                    e1.printStackTrace();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                if(temp) new Talk();
                else {
                    JOptionPane.showMessageDialog(null, "登陆失败", "error", JOptionPane.ERROR_MESSAGE);
                }


                //System.out.println("123");



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

        JTextArea textArea = new JTextArea();
        scrollPane.setViewportView(textArea);
    }
}
