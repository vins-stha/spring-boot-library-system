package com.librarymanagement.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "authors")
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

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "author_books",
            joinColumns = { @JoinColumn(name = "author_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "book_id", referencedColumnName = "id") })
    private Set<Book> books = new HashSet<>();

    public Author() {};

    public Author(String fname){ this.fname=fname;}

    public Author(String fname, String lname, String mname, String country, Date DOB) {
        this.fname = fname;
        this.lname = lname;
        this.mname = mname;
        this.country = country;
        this.DOB = DOB;
    }

    public Author(String fname, String lname, String country) {
        this.fname = fname;
        this.lname = lname;
        this.country = country;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public long getId() {
        return id;
    }

    public Set<Book> getBooks(){
        return books;
    }

    public void setBooks(Set<Book> books){
        this.books = books;
    }
    public void addBook(Book book) {
        this.books.add(book);
        book.getAuthors().add(this);
    }

    public void removeBook(long bookId) {
        Book book = this.books.stream().filter(t -> t.getId() == bookId).findFirst().orElse(null);
        if (book != null) {
            this.books.remove(book);
            book.getAuthors().remove(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return id == author.id && Objects.equals(fname, author.fname) && Objects.equals(lname, author.lname) && Objects.equals(mname, author.mname) && Objects.equals(country, author.country) && Objects.equals(DOB, author.DOB) && Objects.equals(books, author.books);
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
                ", books=" + books +
                '}';
    }
}
