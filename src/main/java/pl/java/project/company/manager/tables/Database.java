package pl.java.project.company.manager.tables;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.spi.ServiceException;
import pl.java.project.company.manager.Dialogs;

public class Database {
  private static Configuration cfg;
  private static SessionFactory sessionFactory;
  private static Session session;

  private Database() {
  }

  public static boolean setUpSession(String login, String password) {
    cfg = new Configuration().configure("/hibernate/hibernate.cfg.xml");
    cfg.getProperties().setProperty("hibernate.connection.password", password);
    cfg.getProperties().setProperty("hibernate.connection.username", login);
    try {
      sessionFactory = cfg.buildSessionFactory();
    }catch (ServiceException e){
      Dialogs.wrongLoginData();
      return false;
    }
    return true;
  }

  public static Session openSession(){
    session=sessionFactory.openSession();
    return session;
  }

  public static void closeSession(){
    session.close();
  }

}
