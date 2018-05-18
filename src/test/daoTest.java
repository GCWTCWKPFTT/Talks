import org.junit.Test;
import testsocket.bean.User;
import testsocket.dao.DaoInterface;
import testsocket.dao.impl.DaoImpl;

import java.beans.PropertyVetoException;
import java.sql.SQLException;

public class daoTest {


    DaoImpl dao=new DaoImpl();

    @Test
    public void f() throws PropertyVetoException, SQLException {

        //System.out.println(dao.Login("wei","123"));

        User user=new User("汪天赐","111");

        System.out.println(dao.register(user));



    }
}
