package com.librarymanagement.stockmanagement;

import com.librarymanagement.model.Book;

import javax.persistence.*;

@Entity
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int book_count;
    private int borrowed_count;

    @OneToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    public Stock() {
        this.book_count = 0;
        this.borrowed_count = 0;
    }

    public int getBorrowed_count() {
        return borrowed_count;
    }

    public void setBorrowed_count(int borrowed_count) {
        this.borrowed_count = borrowed_count;
    }

    public int getId() {
        return this.id;
    }

    public int getCount() {
        return this.book_count;
    }

    public void setCount(int count) {
        this.book_count = count;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Book getBook() {
        return this.book;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", book_id='" + this.getBook().getId() + '\'' +
                ", count=" + this.getCount() +
                '}';
    }

}
