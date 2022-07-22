package com.librarymanagement.stockmanagement;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.librarymanagement.model.Book;

@Entity
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int book_count;

    @OneToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    public Stock() {
        this.book_count = 0;
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
