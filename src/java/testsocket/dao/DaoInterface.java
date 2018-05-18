package testsocket.dao; /*
 * create by weikunpeng
 * 2018/5/18 20:00
 */

import testsocket.bean.User;

import java.beans.PropertyVetoException;
import java.sql.SQLException;

public interface DaoInterface {


    /**
     * 判断密码和用户名是否正确
     * @param name
     * @param password
     * @return
     */
    boolean Login(String name,String password) throws PropertyVetoException, SQLException;


    /**
     * 注册功能
     * @param user
     * @return
     */
    boolean register(User user) throws PropertyVetoException, SQLException;



}
