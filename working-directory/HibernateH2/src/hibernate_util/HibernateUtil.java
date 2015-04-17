package hibernate_util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
       public static Session getSession(){
           Session session = null;
    	   Configuration configuration = new Configuration();
    	   configuration.configure();
    	   ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
    	   SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    	   session = sessionFactory.openSession();
    	   return session;
       }
      
       public static void closeSession(Session sess){
              if(sess!=null){
                     try{
                           sess.close();
                     }
                     catch(HibernateException e){
                           //do nothing just silence this exception
                     }
              }
       }
}