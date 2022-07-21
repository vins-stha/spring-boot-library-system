package com.librarymanagement.stockmanagement;

public class StockRequest {
    private int bookCount;
    private int bookId;

    public StockRequest() {
    };

    public void setBookCount(int bookCount) {
        this.bookCount = bookCount;
    }

    public int getBookCount() {
        return bookCount;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getBookId() {
        return this.bookId;
    }

    public String toString() {
        return "StockRequest{" +
                "id = " + bookId +
                ", book_count='" + bookCount + '\'' +

                '}';
    }

}
