package com.librarymanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String title;
    private String serialNumber;
    private int yearOfPublished;
    private float price;
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "books"
    )
    @JsonIgnore
    private Set<Author> authors = new HashSet<>();

    public Book() {
    }

    public Book(int id, String title, String serialNumber, int yearOfPublished, float price) {
        this.id = id;
        this.title = title;
        this.serialNumber = serialNumber;
        this.yearOfPublished = yearOfPublished;
        this.price = price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setYearOfPublished(int yearOfPublished) {
        this.yearOfPublished = yearOfPublished;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public int getYearOfPublished() {
        return yearOfPublished;
    }

    public float getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return  yearOfPublished == book.yearOfPublished && Float.compare(book.price, price) == 0 && Objects.equals(title, book.title) && Objects.equals(serialNumber, book.serialNumber) && Objects.equals(authors, book.authors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, serialNumber, yearOfPublished, price, authors);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", yearOfPublished=" + yearOfPublished +
                ", price=" + price +
                ", authors=" + authors +
                '}';
    }
}
