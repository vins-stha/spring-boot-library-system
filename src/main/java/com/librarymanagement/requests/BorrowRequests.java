package com.librarymanagement.requests;

public class BorrowRequests {
    private int id;

    private int userId;
    private int bookId;

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return this.bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }



}
