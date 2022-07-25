package com.librarymanagement.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "borrowed_item")
public class BorrowedItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Date startState; // default now
    private Date dueDate; // default now + 14 days
    private long overDueDays; // default 0
    private double fineAmount; // dfault 0
    private boolean finePaid=true;
    // private int itemCount;

    @OneToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public BorrowedItem() {
    }

    public boolean isFinePaid() {
        return finePaid;
    }

    public void setFinePaid(boolean finePaid) {
        this.finePaid = finePaid;
    }

    public long getOverDueDays() {
        return overDueDays;
    }

    public void setOverDueDays(long overDueDays) {
        this.overDueDays = overDueDays;
    }

    public double getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(double fineAmount) {
        this.fineAmount = fineAmount;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getStartState() {
        return this.startState;
    }

    public void setStartState(Date startState) {
        this.startState = startState;
    }

    public Date getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Book getBook() {
        return this.book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String toString() {
        return "BorrowedItem{" +
                "id=" + id +
                ", book_id='" + this.getBook().getId() + '\'' +
                ", user_id='" + this.getUser().getId() + '\'' +
                ", loan_date=" + this.startState +
                ", due_date=" + this.dueDate +
                '}';
    }

}
