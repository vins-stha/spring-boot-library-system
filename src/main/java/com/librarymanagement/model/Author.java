package com.librarymanagement.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String fname;
    private String lname;
    private String mname;
    private String country;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date DOB;
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JoinTable(name = "author_book",
            inverseJoinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            joinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id")
    )
    private Set<Book> books = new HashSet<>();



    public Author(){};
    public Author(int id, String fname, String lname, String mname, String country, Date DOB) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.mname = mname;
        this.country = country;
        this.DOB = DOB;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }


    public int getId() {
        return id;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getMname() {
        return mname;
    }

    public String getCountry() {
        return country;
    }

    public Date getDOB() {
        return DOB;
    }

    public void addBook(Book book){
        this.books.add(book);
        System.out.println("authors of this book ::" + book.getAuthors().toString());
        book.getAuthors().add(this);
    }

    public void removeBook(Book book){
        this.books.remove(book);
        System.out.println("authors of this book ::" + book.getAuthors().toString());
        book.getAuthors().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return id == author.id && Objects.equals(fname, author.fname) && Objects.equals(lname, author.lname) && Objects.equals(mname, author.mname) && Objects.equals(country, author.country) && Objects.equals(DOB, author.DOB);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fname, lname, mname, country, DOB);
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", mname='" + mname + '\'' +
                ", country='" + country + '\'' +
                ", DOB=" + DOB +
                '}';
    }
}