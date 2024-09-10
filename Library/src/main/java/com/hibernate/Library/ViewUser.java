package com.hibernate.Library;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.awt.*;
import java.util.List;

public class ViewUser {
    private JFrame frame;
    private JTable userTable;
    private DefaultTableModel tableModel;

    public ViewUser(Session session) {
        frame = new JFrame("All Users");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Initialize table model
        String[] columnNames = {"User ID", "Name", "Email"};
        tableModel = new DefaultTableModel(columnNames, 0);
        userTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(userTable);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Load data
        loadUserData(session);

        frame.setVisible(true);
    }

    private void loadUserData(Session session) {
        Transaction tx = session.beginTransaction();
        List<users> userList = session.createQuery("from users", users.class).list();
        tx.commit();

        tableModel.setRowCount(0); // Clear existing rows

        for (users user : userList) {
            tableModel.addRow(new Object[] {
                user.getUserid(), user.getName(), user.getEmail()
            });
        }
    }

    public static void main(String[] args) {
        // This is just an example, replace with your Hibernate session setup
        Session session = HibernateUtil.getSessionFactory().openSession();
        new ViewUser(session);
    }
}
