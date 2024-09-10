package com.hibernate.Library;

import javax.persistence.*;
import javax.swing.JOptionPane;

import org.hibernate.Transaction;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generate the user ID
    private Integer userid; // Use Integer instead of int to allow null values

    private String name;
    private String email;
    private String phoneNumber;

    @ManyToMany
    @JoinTable(
        name = "user_books",
        joinColumns = @JoinColumn(name = "userid"),
        inverseJoinColumns = @JoinColumn(name = "bookId")
    )
    private List<books> borrowedBooks = new ArrayList<>();

    public users() {
        // Default constructor
    }

    public users(String name, String email, String phoneNumber, List<books> borrowedBooks) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.borrowedBooks = borrowedBooks;
    }

    // Getters and Setters
    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<books> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<books> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }
}

