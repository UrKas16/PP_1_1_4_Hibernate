package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.buildSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS user_data (id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                            "name VARCHAR(20), lastName VARCHAR(20), age TINYINT)")
                    .executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            System.out.printf("%s - ошибка создания таблицы user_data", e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.buildSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS user_data")
                    .executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            System.out.printf("%s - ошибка удаления таблицы user_data", e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = Util.buildSessionFactory().openSession()) {
            boolean a = session.isOpen();
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.printf("User с именем – %s добавлен в базу данных\n", name);
        } catch (HibernateException e) {
            System.out.printf("%s - ошибка сохранения пользователя с именем - %s", e.getMessage(), name);
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.buildSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
        } catch (HibernateException e) {
            System.out.printf("%s - ошибка удаления пользователя с Id - %s", e.getMessage(), id);
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        Transaction transaction = null;
        try (Session session = Util.buildSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            users = session.createQuery("from User ").list();
            transaction.commit();
        } catch (HibernateException e) {
            System.out.printf("%s - ошибка получения всех пользователей из таблицы user_data", e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.buildSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE user_data")
                    .executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            System.out.printf("%s - ошибка удаления всех пользователей из таблицы user_data", e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
