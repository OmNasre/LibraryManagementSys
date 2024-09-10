//package com.hibernate.Library;
//
//import javax.swing.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.hibernate.cfg.Configuration;
//
//public class AddBook extends JFrame {
//
//    private JTextField bookIdField, bookNameField, authorNameField, publicationYearField;
//    private JCheckBox isBorrowedCheckBox;
//    private JButton addButton;
//    private JTextArea outputArea;
//
//    private SessionFactory sessionFactory;
//
//    public AddBook() {
//        // Initialize Hibernate configuration
//        Configuration cfg = new Configuration()
//                .configure()  // hibernate.cfg.xml should be available
//                .addAnnotatedClass(books.class);
//
//        sessionFactory = cfg.buildSessionFactory();
//
//        // Set up the GUI
//        setTitle("Add Book");
//        setSize(400, 300);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(null);
//
//        JLabel bookIdLabel = new JLabel("Book ID:");
//        bookIdLabel.setBounds(10, 20, 80, 25);
//        add(bookIdLabel);
//
//        bookIdField = new JTextField();
//        bookIdField.setBounds(100, 20, 150, 25);
//        add(bookIdField);
//
//        JLabel bookNameLabel = new JLabel("Book Name:");
//        bookNameLabel.setBounds(10, 60, 80, 25);
//        add(bookNameLabel);
//
//        bookNameField = new JTextField();
//        bookNameField.setBounds(100, 60, 150, 25);
//        add(bookNameField);
//
//        JLabel authorNameLabel = new JLabel("Author Name:");
//        authorNameLabel.setBounds(10, 100, 100, 25);
//        add(authorNameLabel);
//
//        authorNameField = new JTextField();
//        authorNameField.setBounds(120, 100, 150, 25);
//        add(authorNameField);
//
//        JLabel publicationYearLabel = new JLabel("Publication Year:");
//        publicationYearLabel.setBounds(10, 140, 120, 25);
//        add(publicationYearLabel);
//
//        publicationYearField = new JTextField();
//        publicationYearField.setBounds(140, 140, 100, 25);
//        add(publicationYearField);
//
//        isBorrowedCheckBox = new JCheckBox("Is Borrowed");
//        isBorrowedCheckBox.setBounds(10, 180, 120, 25);
//        add(isBorrowedCheckBox);
//
//        addButton = new JButton("Add Book");
//        addButton.setBounds(100, 220, 150, 25);
//        add(addButton);
//
//        outputArea = new JTextArea();
//        outputArea.setBounds(10, 250, 360, 50);
//        outputArea.setEditable(false);
//        add(outputArea);
//
//        addButton.addActionListener(new AddBookActionListener());
//
//        setVisible(true);
//    }
//
//    private class AddBookActionListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            int bookId = Integer.parseInt(bookIdField.getText());
//            String bookName = bookNameField.getText();
//            String authorName = authorNameField.getText();
//            int publicationYear = Integer.parseInt(publicationYearField.getText());
//            boolean isBorrowed = isBorrowedCheckBox.isSelected();
//
//            Session session = sessionFactory.openSession();
//            Transaction tx = session.beginTransaction();
//
//            books newBook = new books(bookId, bookName, authorName, publicationYear, isBorrowed);
//            session.save(newBook);
//
//            tx.commit();
//            session.close();
//            outputArea.setText("Book added successfully!");
//        }
//    }
//
//    public static void main(String[] args) {
//        new AddBook();
//    }
//}
