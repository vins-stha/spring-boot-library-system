package com.librarymanagement.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
public class Book {
    @Id
    private int id;
    private String title;
    private String serialNumber;
    private int yearOfPublieshed;
    private float price;
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JoinTable(name = "author_book",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id")
    )
    private Set<Author> authors = new HashSet<>();

    public Book() {
    }

    public Book(int id, String title, String serialNumber, int yearOfPublieshed, float price, Set<Author> authors) {
        this.id = id;
        this.title = title;
        this.serialNumber = serialNumber;
        this.yearOfPublieshed = yearOfPublieshed;
        this.price = price;
        this.authors = authors;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Set<Author> getAuthor() {
        return authors;
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
        return id == book.id && yearOfPublieshed == book.yearOfPublieshed && Float.compare(book.price, price) == 0 && Objects.equals(title, book.title) && Objects.equals(serialNumber, book.serialNumber) && Objects.equals(authors, book.authors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, serialNumber, yearOfPublieshed, price, authors);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", yearOfPublieshed=" + yearOfPublieshed +
                ", price=" + price +
                ", authors=" + authors +
                '}';
    }
}
