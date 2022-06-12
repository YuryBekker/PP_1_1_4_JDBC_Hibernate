package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserDao userDao = new UserDaoHibernateImpl();
//        UserDao userDao = new UserDaoJDBCImpl();
        userDao.createUsersTable();
        userDao.saveUser("Masha", "Ma", (byte) 20);
        userDao.saveUser("Dasha", "Ma", (byte) 20);
        userDao.saveUser("Sasha", "Ma", (byte) 20);
        userDao.saveUser("Glasha", "Ma", (byte) 20);
        userDao.removeUserById(1);
        List<User> userList = userDao.getAllUsers();
        for (User user : userList
        ) {
            System.out.println(user);
        }
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}
