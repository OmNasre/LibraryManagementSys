package com.hibernate.Library;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class AddAdmin {

    public static void main(String[] args) {
        // Initialize Hibernate configuration
        Configuration cfg = new Configuration()
                .configure()  // hibernate.cfg.xml should be available
                .addAnnotatedClass(admins.class);

        // Create SessionFactory and open a session
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();

        // Create a new admin object
        admins newAdmin = new admins(101, 12345);  // loginId = 101, password = 12345

        // Begin transaction
        Transaction tx = session.beginTransaction();

        // Save the admin object to the database
        session.save(newAdmin);

        // Commit the transaction and close the session
        tx.commit();
        session.close();
        sessionFactory.close();

        System.out.println("Admin added successfully!");
    }
}
