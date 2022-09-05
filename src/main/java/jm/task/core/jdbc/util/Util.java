package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    private static SessionFactory sessionFactory;
    private static final Environment environment = null;

    public static SessionFactory buildSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration config = new Configuration();
                Properties prop = new Properties();
                prop.put(environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                prop.put(environment.URL, "jdbc:mysql://localhost:3306/pp_1_1_4_hibernate");
                prop.put(environment.USER, "root");
                prop.put(environment.PASS, "Iqprimer100@");
                prop.put(environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
                prop.put(environment.SHOW_SQL, "true");
                prop.put(environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                config.setProperties(prop);
                config.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(config.getProperties()).build();
                sessionFactory = config.buildSessionFactory(serviceRegistry);

            } catch (Throwable ex) {
                System.err.println("Initial SessionFactory creation failed." + ex);
                throw new ExceptionInInitializerError(ex);
            }
        }
        return sessionFactory;
    }

}
