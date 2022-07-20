package com.librarymanagement.services;

import com.librarymanagement.model.Author;
import com.librarymanagement.model.Book;
import com.librarymanagement.repository.AuthorRepository;
import com.librarymanagement.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class BookServiceCopy {
    private ArrayList<Book> books;

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    public BookServiceCopy() {
        this.books = new ArrayList<>();
    }

    public ResponseEntity<Object> getAll() {
        this.books = new ArrayList<>();
        for (Book book : bookRepository.findBooksByDistinctTitle()) {
            this.books.add(book);
        }
        return new ResponseEntity<>(this.books, HttpStatus.OK);
    }

    public ResponseEntity<Object> getBookById(int id) {
        if (!bookRepository.findById(id).isEmpty()) {
            Book book = bookRepository.findById(id).get();

            return new ResponseEntity<>(book, HttpStatus.OK);
        }

        return new ResponseEntity<>("Not found.", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Object> addBook(Book book) {
        if (book.getTitle().isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        bookRepository.save(book);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    public ResponseEntity<Object> updateBookById(Book book, int id) {
        if (!bookRepository.findById(id).isEmpty()) {
            Book old = bookRepository.findById(id).get();

            old.setPrice(book.getPrice());
            old.setSerialNumber(book.getSerialNumber());
            old.setTitle(book.getTitle());
            old.setYearOfPublished(book.getYearOfPublished());
            old.setAuthors(book.getAuthors());

            bookRepository.save(old);

            return new ResponseEntity<>(old, HttpStatus.OK);
        }

        return new ResponseEntity<>("Book not found.", HttpStatus.NOT_FOUND);

    }

    public ResponseEntity<Object> deleteBookById(int id) {
        if (bookRepository.findById(id).isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        bookRepository.deleteById(id);
        return new ResponseEntity<>("Book deleted", HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Object> getAllBooksByAuthorId(int authorId) {
        if (authorRepository.findById(authorId).isEmpty()) {

            return new ResponseEntity<>("Author not found", HttpStatus.NOT_FOUND);
        }
        List<Book> bookList = bookRepository.findBooksByAuthorsId(authorId);

        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    public ResponseEntity<Object> getABookByAuthorId(int authorId, int bookId) {
        List<Book> bookList = bookRepository.findBooksByAuthorsId(authorId);
        Book bookToReturn = bookList.stream().filter(book -> book.getId() == bookId).findFirst().orElse(null);
        if (bookToReturn == null)
            return new ResponseEntity<>("No such book", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(bookToReturn, HttpStatus.OK);
    }

    public ResponseEntity<Object> addBookByAuthorId(int authorId, Book book) {

        Author author = authorRepository.findById(authorId).get();
        final boolean[] inList = { false };
        final boolean[] newBook = { true };

        // save book if not present in book table
        bookRepository.findAll().forEach(book1 -> {
            if (book1.getTitle().equals(book.getTitle())
                    && book1.getYearOfPublished() == book.getYearOfPublished()
                    && book1.getSerialNumber() == book.getSerialNumber()) {
                System.out.println("Not new book");
                newBook[0] = false;
            }
        });

        // add to book database if not present
//        if (!newBook[0]) {
//            System.out.println("Not New book");
//            System.out.println("Book already exist" + HttpStatus.CREATED);
//        } else {
//            bookRepository.save(book);
//        }

        // check if book exist in author books list
        bookRepository.findBooksByAuthorsId(authorId).forEach(book1 -> {
            boolean exist = book1.getTitle().equals(book.getTitle())
                    && book1.getYearOfPublished() == book.getYearOfPublished()
                    && book1.getSerialNumber() == book.getSerialNumber();
            if (exist) {
                // do nothing
                inList[0] = true;
                System.out.println("Book already present in author book list");

            }
        });

        if (inList[0])
            return new ResponseEntity<>("Book exist in author book list", HttpStatus.OK);

        if (!inList[0]) {

            System.out.println("Not in list");
            author.addBook(book);
            authorRepository.save(author);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    public ResponseEntity<Object> updateABookByAuthorId(int authorId, int bookId, Book book) {
        ResponseEntity<Object> response = getABookByAuthorId(authorId, bookId);

        if (Integer.valueOf(response.getStatusCodeValue()).equals(404))
            return new ResponseEntity<>("Book does not exist", HttpStatus.NOT_FOUND);

        Book bookToUpdate = (Book) response.getBody();
        if (book.getTitle() != null)
            bookToUpdate.setTitle(book.getTitle());
        if (book.getSerialNumber() != null)
            bookToUpdate.setSerialNumber(book.getSerialNumber());
        if (!Float.toString(book.getPrice()).isEmpty())
            bookToUpdate.setPrice(book.getPrice());
        if (!Integer.toString(book.getYearOfPublished()).isEmpty())
            bookToUpdate.setYearOfPublished(book.getYearOfPublished());

        bookRepository.save(bookToUpdate);
        return new ResponseEntity<>(bookToUpdate, HttpStatus.OK);
    }

    public ResponseEntity<Object> deleteABookByAuthorId(int authorId, int bookId) {
        ResponseEntity<Object> response = getABookByAuthorId(authorId, bookId);

        if (Integer.valueOf(response.getStatusCodeValue()).equals(404))
            return new ResponseEntity<>("Book does not exist", HttpStatus.NOT_FOUND);

        bookRepository.deleteById(bookId);

        return new ResponseEntity<>("Book deleted", HttpStatus.OK);
    }

    public ResponseEntity<Object> getAllAuthorsByBookId(int bookId) {
        List<Author> authors = authorRepository.findAuthorsByBooksId(bookId);
        System.out.println("Authors = " + authors);
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

}
