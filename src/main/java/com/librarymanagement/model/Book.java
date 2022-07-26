package com.librarymanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;
    private String serialNumber;
    private int yearOfPublished;
    private float price;
    private String language = "EN";
    @ElementCollection(fetch = FetchType.LAZY)
    private Set<String> genres = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY,
            mappedBy = "books")
    // @OneToOne(mappedBy = "book_id", cascade = CascadeType.ALL, fetch =
    // FetchType.LAZY)
    // private Stock inStock;

    @JsonIgnore
    private Set<Author> authors = new HashSet<>();

    public Book() {

    }

    public Book(String title) {
        this.title = title;

    }

//    public Book(String title, String serialNumber, int yearOfPublished, float price) {
//        this.title = title;
//        this.serialNumber = serialNumber;
//        this.yearOfPublished = yearOfPublished;
//        this.price = price;
//    }


    public Book(String title, String serialNumber, int yearOfPublished, float price, String language, Set<String> genres) {
        this.title = title;
        this.serialNumber = serialNumber;
        this.yearOfPublished = yearOfPublished;
        this.price = price;
        this.language = language;
        this.genres = genres;
    }

    public Set<String> getGenres() {
        return genres;
    }

    public void setGenres(Set<String> genres) {
        this.genres = genres;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getYearOfPublished() {
        return yearOfPublished;
    }

    public void setYearOfPublished(int yearOfPublished) {
        this.yearOfPublished = yearOfPublished;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Book book = (Book) o;
        return Objects.equals(title, book.title) && Objects.equals(authors, book.authors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, authors);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authors=" + authors +
                ", genres=" + genres +
                '}';
    }
}
