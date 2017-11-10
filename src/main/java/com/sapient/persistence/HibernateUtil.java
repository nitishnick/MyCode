package com.sapient.persistence;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
           Configuration cfg =  new AnnotationConfiguration().configure();
           SessionFactory sf = cfg.buildSessionFactory();
           
           return sf;
        }
        catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    /**
     * for closing the connection pool.
     */
    public static void shutdown() {
    	// Close caches and connection pools
    	getSessionFactory().close();
    }

}