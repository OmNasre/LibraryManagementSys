package com.hibernate.Library;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class App extends JFrame {

    private SessionFactory sessionFactory;
    private Session session;
    private JTextField loginIdField;
    private JPasswordField passwordField;
    private JTextArea outputArea;
    private JButton loginButton, showBooksButton, issueBookButton, returnBookButton, addBookButton, addUserButton;
    private JTable booksTable;
    private DefaultTableModel tableModel;

    public App() {
        // Hibernate configuration
        Configuration cfg = new Configuration().configure().addAnnotatedClass(admins.class)
                .addAnnotatedClass(users.class).addAnnotatedClass(books.class);
        sessionFactory = cfg.buildSessionFactory();
        session = sessionFactory.openSession();

        // Set up the GUI
        setTitle("Library Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Use GridBagLayout for flexible component placement
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add padding between elements

        // Login Panel
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBorder(BorderFactory.createTitledBorder("Admin Login"));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(loginPanel, gbc);

        GridBagConstraints loginGbc = new GridBagConstraints();
        loginGbc.insets = new Insets(5, 5, 5, 5);
        loginGbc.gridx = 0;
        loginGbc.gridy = 0;
        loginPanel.add(new JLabel("Login ID:"), loginGbc);

        loginIdField = new JTextField(15);
        loginGbc.gridx = 1;
        loginPanel.add(loginIdField, loginGbc);

        loginGbc.gridx = 0;
        loginGbc.gridy = 1;
        loginPanel.add(new JLabel("Password:"), loginGbc);

        passwordField = new JPasswordField(15);
        loginGbc.gridx = 1;
        loginPanel.add(passwordField, loginGbc);

        // Login Button
        loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(loginButton, gbc);

        // Show Books Button
        showBooksButton = new JButton("Show All Books");
        showBooksButton.setEnabled(false); // Initially disabled
        gbc.gridx = 1;
        add(showBooksButton, gbc);

        // Issue Book Button
        issueBookButton = new JButton("Issue Book");
        issueBookButton.setEnabled(false); // Initially disabled
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(issueBookButton, gbc);

        // Return Book Button
        returnBookButton = new JButton("Return Book");
        returnBookButton.setEnabled(false); // Initially disabled
        gbc.gridx = 1;
        add(returnBookButton, gbc);

        // Add Book Button
        addBookButton = new JButton("Add Book");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(addBookButton, gbc);

        // Add User Button
        addUserButton = new JButton("Add User");
        gbc.gridx = 1;
        add(addUserButton, gbc);

        // Books Table
        String[] columnNames = { "Book ID", "Name", "Author", "Publication Year", "Available" };
        tableModel = new DefaultTableModel(columnNames, 0);
        booksTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(booksTable);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        add(scrollPane, gbc);

        outputArea = new JTextArea(5, 30);
        outputArea.setEditable(false);
        Font font = new Font("Arial", Font.BOLD, 16);
        outputArea.setFont(font);
        outputArea.setBackground(Color.LIGHT_GRAY); 
        outputArea.setMargin(new Insets(10, 10, 10, 10));
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        JScrollPane outputScrollPane = new JScrollPane(outputArea);
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.BOTH;
        add(outputScrollPane, gbc);

        // Add action listeners
        loginButton.addActionListener(new LoginActionListener());
        showBooksButton.addActionListener(new ShowBooksActionListener());
        issueBookButton.addActionListener(new IssueBookActionListener());
        returnBookButton.addActionListener(new ReturnBookActionListener());
        addBookButton.addActionListener(new AddBookActionListener());
        addUserButton.addActionListener(new AddUserActionListener());

        setVisible(true);
    }

    // Define the action listener classes as inner classes

    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String loginIdText = loginIdField.getText();
            String passwordText = new String(passwordField.getPassword());

            if (!loginIdText.isEmpty() && !passwordText.isEmpty()) {
                int loginId = Integer.parseInt(loginIdText);
                int password = Integer.parseInt(passwordText);

                admins admin = session.get(admins.class, loginId);
                if (admin != null && admin.getPassword() == password) {
                    outputArea.setText("Login Successful!");
                    showBooksButton.setEnabled(true);
                    issueBookButton.setEnabled(true);
                    returnBookButton.setEnabled(true);
                    addBookButton.setEnabled(true);
                    addUserButton.setEnabled(true);
                } else {
                    outputArea.setText("Login Failed. Invalid credentials.");
                    showBooksButton.setEnabled(false);
                    issueBookButton.setEnabled(false);
                    returnBookButton.setEnabled(false);
                    addBookButton.setEnabled(false);
                    addUserButton.setEnabled(false);
                }
            } else {
                outputArea.setText("Please enter both Login ID and Password.");
            }
        }
    }

    private class ShowBooksActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Transaction tx = session.beginTransaction();

            List<books> allBooks = session.createQuery("from books", books.class).list();
            tableModel.setRowCount(0); // Clear existing rows

            for (books book : allBooks) {
                tableModel.addRow(new Object[] {
                    book.getBookId(),
                    book.getBookName(),
                    book.getAuthorName(),
                    book.getPublicationYear(),
                    !book.isBorrowed()
                });
            }

            DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
            cellRenderer.setBorder(new EmptyBorder(10, 10, 10, 10));   
            cellRenderer.setHorizontalAlignment(SwingConstants.CENTER); 
            cellRenderer.setFont(new Font("SansSerif", Font.PLAIN, 14)); 

            for (int i = 0; i < booksTable.getColumnCount(); i++) {
                booksTable.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
            }

            booksTable.getTableHeader().setFont(new Font("Serif", Font.BOLD, 16)); 
            booksTable.getTableHeader().setBackground(new Color(200, 200, 255)); 
            booksTable.getTableHeader().setForeground(Color.BLACK); 

            tx.commit();
        }
    }

    private class IssueBookActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String userIdText = JOptionPane.showInputDialog(null, "Enter User ID:", "Issue Book", JOptionPane.QUESTION_MESSAGE);
            String bookIdText = JOptionPane.showInputDialog(null, "Enter Book ID:", "Issue Book", JOptionPane.QUESTION_MESSAGE);

            if (userIdText != null && bookIdText != null) {
                int userId = Integer.parseInt(userIdText);
                int bookId = Integer.parseInt(bookIdText);

                Transaction tx = session.beginTransaction();

                users user = session.get(users.class, userId);
                books book = session.get(books.class, bookId);

                if (user != null && book != null && !book.isBorrowed()) {
                    List<books> borrowedBooks = user.getBorrowedBooks();
                    borrowedBooks.add(book);
                    book.setBorrowed(true);
                    session.update(user);
                    session.update(book);
                    JOptionPane.showMessageDialog(null,
                            "<html><b>Book issued successfully</b><br>User: " + user.getName() + "<br>Book: " + book.getBookName() + "</html>",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else if (book != null && book.isBorrowed()) {
                        JOptionPane.showMessageDialog(null,
                            "Book is already issued.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null,
                            "Invalid User ID or Book ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    tx.commit();
                } else {
                    JOptionPane.showMessageDialog(null, "Operation canceled.", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }

        private class ReturnBookActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userIdText = JOptionPane.showInputDialog(null, "Enter User ID:", "Return Book", JOptionPane.QUESTION_MESSAGE);
                String bookIdText = JOptionPane.showInputDialog(null, "Enter Book ID:", "Return Book", JOptionPane.QUESTION_MESSAGE);

                if (userIdText != null && bookIdText != null) {
                    int userId = Integer.parseInt(userIdText);
                    int bookId = Integer.parseInt(bookIdText);

                    Transaction tx = session.beginTransaction();

                    users user = session.get(users.class, userId);
                    books book = session.get(books.class, bookId);

                    if (user != null && book != null && book.isBorrowed()) {
                        List<books> borrowedBooks = user.getBorrowedBooks();
                        borrowedBooks.remove(book);
                        book.setBorrowed(false);
                        session.update(user);
                        session.update(book);

                        JOptionPane.showMessageDialog(null,
                            "<html><b>Book returned successfully</b><br>User: " + user.getName() + "<br>Book: " + book.getBookName() + "</html>",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null,
                            "Invalid User ID or Book ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    tx.commit();
                } else {
                    JOptionPane.showMessageDialog(null, "Operation canceled.", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }

        private class AddBookActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bookName = JOptionPane.showInputDialog(null, "Enter Book Name:", "Add Book", JOptionPane.QUESTION_MESSAGE);
                String authorName = JOptionPane.showInputDialog(null, "Enter Author Name:", "Add Book", JOptionPane.QUESTION_MESSAGE);
                String publicationYearText = JOptionPane.showInputDialog(null, "Enter Publication Year:", "Add Book", JOptionPane.QUESTION_MESSAGE);

                if (bookName != null && authorName != null && publicationYearText != null) {
                    int publicationYear = Integer.parseInt(publicationYearText);

                    Transaction tx = session.beginTransaction();

                    books newBook = new books();
                    newBook.setBookName(bookName);
                    newBook.setAuthorName(authorName);
                    newBook.setPublicationYear(publicationYear);
                    newBook.setBorrowed(false);

                    session.save(newBook);
                    tx.commit();

                    JOptionPane.showMessageDialog(null,
                        "Book added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Operation canceled.", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }

        private class AddUserActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = JOptionPane.showInputDialog(null, "Enter User Name:", "Add User", JOptionPane.QUESTION_MESSAGE);
                String userEmail = JOptionPane.showInputDialog(null, "Enter User Email:", "Add User", JOptionPane.QUESTION_MESSAGE);

                if (userName != null && userEmail != null) {
                    Transaction tx = session.beginTransaction();

                    users newUser = new users();
                    newUser.setName(userName);
                    newUser.setEmail(userEmail);

                    session.save(newUser);
                    tx.commit();

                    JOptionPane.showMessageDialog(null,
                        "User added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Operation canceled.", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }

        public static void main(String[] args) {
            new App();
        }
    }
