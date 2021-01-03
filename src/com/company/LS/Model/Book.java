package com.company.LS.Model;
import java.lang.Boolean;
public class Book {
    private String BookId;
    private String Title;
    private String Author;
    private String Publisher;
    private String IsAvail;

    public Book(String bookId, String title, String author, String publisher, String isAvail) {
        BookId = bookId;
        Title = title;
        Author = author;
        Publisher = publisher;
        IsAvail = isAvail;
    }

    public String getBookId() {
        return BookId;
    }

    public void setBookId(String bookId) {
        BookId = bookId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String publisher) {
        Publisher = publisher;
    }

    public String getIsAvail() {
        return IsAvail;
    }

    public void setIsAvail(String isAvail) {
        IsAvail = isAvail;
    }
}
