package com.hibernate.Library;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class AddUser {

    public static void main(String[] args) {
        // Initialize Hibernate configuration
        Configuration cfg = new Configuration()
                .configure()  // hibernate.cfg.xml should be available
                .addAnnotatedClass(users.class)
                .addAnnotatedClass(books.class);

        // Create SessionFactory and open a session
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();

        // Create a new user object
        users newUser = new users("John Doe", "john.doe@example.com", "1234567890", new ArrayList<>());

        // Begin transaction
        Transaction tx = session.beginTransaction();

        // Save the user object to the database
        session.save(newUser);

        // Commit the transaction and close the session
        tx.commit();
        session.close();
        sessionFactory.close();

        System.out.println("User added successfully!");
    }
}
