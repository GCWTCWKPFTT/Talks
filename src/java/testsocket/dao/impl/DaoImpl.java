package testsocket.dao.impl;
/*
 * create by weikunpeng
 * 2018/5/18 20:19
 */

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import testsocket.bean.User;
import testsocket.dao.DaoInterface;
import testsocket.util.MD5Util;

import java.beans.PropertyVetoException;
import java.sql.SQLException;

public class DaoImpl implements DaoInterface {
    @Override
    public  boolean Login(String name, String password) throws PropertyVetoException, SQLException {


        /**
         * c3p0数据库连接池
         */
        ComboPooledDataSource comboPooledDataSource=new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass("com.mysql.jdbc.Driver");
        comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/kerberos?useUnicode=true&characterEncoding=utf8");
        comboPooledDataSource.setUser("root");
        comboPooledDataSource.setPassword("1234");



        QueryRunner qr = new QueryRunner(comboPooledDataSource);

        String sql="select * from user where name = ? and password =?";

        User user= (User) qr.query(sql, new BeanHandler(User.class),name,password);

        if(user!=null){
            System.out.println("登陆成功！！！");
            return true;
        }

        return false;

    }



    @Override
    public boolean register(User user) throws PropertyVetoException, SQLException {


        ComboPooledDataSource comboPooledDataSource=new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass("com.mysql.jdbc.Driver");
        comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/kerberos?useUnicode=true&characterEncoding=utf8");
        comboPooledDataSource.setUser("root");
        comboPooledDataSource.setPassword("1234");



        QueryRunner qr = new QueryRunner(comboPooledDataSource);
        String sql="select * from user where name = ?";

        User user1= (User) qr.query(sql, new BeanHandler(User.class),user.getName());

        //System.out.println(user1.getName());
       // System.out.println(user1.getPassword());

        if (user1==null) {
             sql = "insert into user(name,password) values(?,?)";
            qr.update(sql, user.getName(), MD5Util.MD5EncodeUtf8(user.getPassword()));

            return true;
        } else {
            System.out.println("用户名已经存在");
            return false;
        }
    }

    public static void main(String[] args) {

        String temp="1234";
        String temp1=MD5Util.MD5EncodeUtf8(temp);
        System.out.println(temp1);
    }


    @Override
    public User Login1(String name) throws PropertyVetoException, SQLException {
        ComboPooledDataSource comboPooledDataSource=new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass("com.mysql.jdbc.Driver");
        comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/kerberos?useUnicode=true&characterEncoding=utf8");
        comboPooledDataSource.setUser("root");
        comboPooledDataSource.setPassword("1234");



        QueryRunner qr = new QueryRunner(comboPooledDataSource);

        String sql="select * from user where name = ?";

        User user= (User) qr.query(sql, new BeanHandler(User.class),name);

        if(user!=null){
            System.out.println("数据库验证成功！！！");
            return user;
        }

        return user;
    }
}
