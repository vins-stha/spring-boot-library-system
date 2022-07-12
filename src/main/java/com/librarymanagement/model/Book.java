package com.librarymanagement.model;

import java.util.Objects;
import java.util.ArrayList;
import javax.persistence.*;

@Entity
public class Book {
    @Id
    private int id;
    private String title;
    private ArrayList<Author> author;
    private String serialNumber;
    private int yearOfPublieshed;
    private float price;

    public Book(int id, String title, ArrayList<Author> author, String serialNumber, int yearOfPublieshed, float price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.serialNumber = serialNumber;
        this.yearOfPublieshed = yearOfPublieshed;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Author> getAuthor() {
        return author;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public int getYearOfPublieshed() {
        return yearOfPublieshed;
    }

    public float getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && yearOfPublieshed == book.yearOfPublieshed && Float.compare(book.price, price) == 0 && Objects.equals(title, book.title) && Objects.equals(author, book.author) && Objects.equals(serialNumber, book.serialNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, serialNumber, yearOfPublieshed, price);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", serialNumber='" + serialNumber + '\'' +
                ", yearOfPublieshed=" + yearOfPublieshed +
                ", price=" + price +
                '}';
    }
}
