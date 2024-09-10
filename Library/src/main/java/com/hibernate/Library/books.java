package com.hibernate.Library;

import javax.persistence.*;

@Entity
@Table(name = "books")
public class books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;

    private String bookName;
    private String authorName;
    private int publicationYear;
    private boolean isBorrowed;

    public books() {
        // Default constructor
    }

    public books(int bookId, String bookName, String authorName, int publicationYear, boolean isBorrowed) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.authorName = authorName;
        this.publicationYear = publicationYear;
        this.isBorrowed = isBorrowed;
    }

    // Getters and Setters
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean isBorrowed) {
        this.isBorrowed = isBorrowed;
    }

	public Object isAvailable() {
		// TODO Auto-generated method stub
		return null;
	}
}
