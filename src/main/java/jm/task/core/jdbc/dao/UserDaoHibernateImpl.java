package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        String query = "Create table if not exists users " +
                "(id Int Primary key AUTO_INCREMENT, name varchar(255), lastname varchar(255), age int)";
        executeDDL(query);
    }

    @Override
    public void dropUsersTable() {
        String query = "Drop table if exists users";
        executeDDL(query);
    }

    private void executeDDL(String query) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(query).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        //    Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            //        transaction = session.beginTransaction();
            userList = session.createQuery("from User",User.class).getResultList();
            //        transaction.commit();
        } catch (Exception e) {
            //        if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return userList;
    }




    @Override
    public void cleanUsersTable() {
        String query = "Delete from users";
        executeDDL(query);
    }
}
