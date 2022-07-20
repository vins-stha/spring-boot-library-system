package com.librarymanagement.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")

public class BookControllerCopy {

//    @Autowired
//    private BookService bookService;
//
//    @Autowired
//    private AuthorRepository authorRepository;
//    @Autowired
//    private BookRepository bookRepository;

/******
        @GetMapping("/books")
        public ResponseEntity<Object> getAllBooks() {
            List<Book> books = new ArrayList<Book>();

            bookRepository.findAll().forEach(books::add);

            if (books.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(books, HttpStatus.OK);
        }

        @GetMapping("/authors/{authorId}/books")
        public ResponseEntity<Object> getAllBooksByAuthorId(@PathVariable(value = "authorId") int authorId) {
            if (!authorRepository.existsById(authorId)) {
                return new ResponseEntity<>("author not found ", HttpStatus.NOT_FOUND);
            }

            List<Book> books = bookRepository.findBooksByAuthorsId(authorId);
            return new ResponseEntity<>(books, HttpStatus.OK);
        }

        @GetMapping("/books/{id}")
        public ResponseEntity<Object> getBooksById(@PathVariable(value = "id") int id) {
            if (bookRepository.findById(id).isEmpty())
                return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
            Book book = bookRepository.findById(id).get();


            return new ResponseEntity<>(book, HttpStatus.OK);
        }

        @GetMapping("/books/{bookId}/authors")
        public ResponseEntity<Object> getAllAuthorsByBookId(@PathVariable(value = "bookId") int bookId) {
            if (!bookRepository.existsById(bookId)) {
                return new ResponseEntity<>("book not found ", HttpStatus.NOT_FOUND);
            }

            List<Author> authors = authorRepository.findAuthorsByBooksId(bookId);
            return new ResponseEntity<>(authors, HttpStatus.OK);
        }

        @PostMapping("/authors/{authorId}/books")
        public ResponseEntity<Book> addBook(@PathVariable(value = "authorId") int authorId, @RequestBody Book bookRequest) {
            Book book = authorRepository.findById(authorId).map(author -> {
                int bookId = bookRequest.getId();

                // book is existed
                if (bookId != 0L) {
                    Book _book = bookRepository.findById(bookId)
                            .orElseThrow(() -> new NotFoundException("Not found Book with id = " + bookId));
                    author.addBook(_book);
                    authorRepository.save(author);
                    return _book;
                }

                // add and create new Book
                author.addBook(bookRequest);
                return bookRepository.save(bookRequest);
            }).orElseThrow(() -> new NotFoundException("Not found Author with id = " + authorId));

            return new ResponseEntity<>(book, HttpStatus.CREATED);
        }
        
        
        
        /*
        public ResponseEntity<Object> addBookByAuthorId(@PathVariable(value = "authorId") int authorId, @RequestBody Book book) {

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
        */
        
/****
        @PutMapping("/books/{id}")
        public ResponseEntity<Book> updateBook(@PathVariable("id") int id, @RequestBody Book bookRequest) {
            if (bookRepository.findById(id).isEmpty())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            Book book = bookRepository.findById(id).get();

            book.setTitle(bookRequest.getTitle());

            return new ResponseEntity<>(bookRepository.save(book), HttpStatus.OK);
        }

        @DeleteMapping("/authors/{authorId}/books/{bookId}")
        public ResponseEntity<HttpStatus> deleteBookFromAuthor(@PathVariable(value = "authorId") int authorId,
                                                                @PathVariable(value = "bookId") int bookId) {
            if (authorRepository.findById(authorId).isEmpty())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            Author author = authorRepository.findById(authorId).get();

            author.removeBook(bookId);
            authorRepository.save(author);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        @DeleteMapping("/books/{id}")
        public ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") int id) {
            bookRepository.deleteById(id);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    

    
    
    ****/
    
    
    
    
    
    
//
//    @RequestMapping("/books")
//    public ResponseEntity<Object> getList() {
//        return bookService.getAll();
//    }
//
//    @RequestMapping("/book/{id}")
//    public ResponseEntity<Object> getBookById(@PathVariable int id) {
//        return bookService.getBookById(id);
//
//    }
//
//    @RequestMapping(value = "/book", method = RequestMethod.POST)
//    public ResponseEntity<Object> addBook(@RequestBody Book book) {
//        return bookService.addBook(book);
//    }

//    @RequestMapping(value = "/book/{id}", method = RequestMethod.PUT)
//    public ResponseEntity<Object> updateBookById(@PathVariable int id, @RequestBody Book book) {
//        System.out.println(book.toString());
//        return bookService.updateBookById(book, id);
//
//    }
//
//    @RequestMapping(value = "/book/{id}", method = RequestMethod.DELETE)
//    public ResponseEntity<Object> deleteBookById(@PathVariable int id) {
//        return bookService.deleteBookById(id);
//
//    }
//
//    @RequestMapping(value = "/author/{authorId}/books")
//    public ResponseEntity<Object> getAllBooksByAuthorId(@PathVariable int authorId) {
//
//        return bookService.getAllBooksByAuthorId(authorId);
//    }
//
//    @RequestMapping(value = "/author/{authorId}/book", method = RequestMethod.POST)
//    public ResponseEntity<Object> addBookByAuthorId(@PathVariable int authorId, @RequestBody Book book) {
//
//        Author author = authorRepository.findById(authorId).get();
//        final boolean[] inList = { false };
//        final boolean[] newBook = { true };
//
//        // save book if not present in book table
//        bookRepository.findAll().forEach(book1 -> {
//            if (book1.getTitle().equals(book.getTitle())
//                    && book1.getYearOfPublished() == book.getYearOfPublished()
//                    && book1.getSerialNumber() == book.getSerialNumber()) {
//                System.out.println("Not new book");
//                newBook[0] = false;
//            }
//        });
//
//        // add to book database if not present
////        if (!newBook[0]) {
////            System.out.println("Not New book");
////            System.out.println("Book already exist" + HttpStatus.CREATED);
////        } else {
////            bookRepository.save(book);
////        }
//
//        // check if book exist in author books list
//        bookRepository.findBooksByAuthorsId(authorId).forEach(book1 -> {
//            boolean exist = book1.getTitle().equals(book.getTitle())
//                    && book1.getYearOfPublished() == book.getYearOfPublished()
//                    && book1.getSerialNumber() == book.getSerialNumber();
//            if (exist) {
//                // do nothing
//                inList[0] = true;
//                System.out.println("Book already present in author book list");
//
//            }
//        });
//
//        if (inList[0])
//            return new ResponseEntity<>("Book exist in author book list", HttpStatus.OK);
//
//        if (!inList[0]) {
//
//            System.out.println("Not in list");
//            author.addBook(book);
//            authorRepository.save(author);
//        }
//
//        return new ResponseEntity<>(HttpStatus.CREATED);
//
//
////        if (!authorRepository.existsById(authorId)) {
////            return new ResponseEntity<>("Author not found", HttpStatus.NOT_FOUND);
////        }
////        System.out.println("author exist=" + authorRepository.findById(authorId));
////        Book book = authorRepository.findById(authorId).map(author -> {
////            int bookId = bookRequest.getId();
////
////            // book is existed
////            if (bookId != 0L) {
////                Book _book = bookRepository.findById(bookId)
////                        .orElseThrow(() -> new NotFoundException("Not found Book with id = " + bookId));
////                author.addBook(_book);
////                authorRepository.save(author);
////                return _book;
////            }
////
////            // add and create new Book
////            author.addBook(bookRequest);
////            return bookRepository.save(bookRequest);
////        }).orElseThrow(() -> new NotFoundException("Not found Author with id = " + authorId));
////
////        return new ResponseEntity<>("book", HttpStatus.CREATED);
////        return bookService.addBookByAuthorId(authorId, book);
//    }
//
//    @RequestMapping(value = "/author/{authorId}/book/{bookId}")
//    public ResponseEntity<Object> getABookByAuthorId(@PathVariable("authorId") int authorId,
//            @PathVariable("bookId") int bookId) {
//        if (!authorRepository.existsById(authorId)) {
//            return new ResponseEntity<>("Author not found", HttpStatus.NOT_FOUND);
//        }
//        return bookService.getABookByAuthorId(authorId, bookId);
//    }
//
//    @RequestMapping(value = "/author/{authorId}/book/{bookId}", method = RequestMethod.PUT)
//    public ResponseEntity<Object> updateABookByAuthorId(@PathVariable("authorId") int authorId,
//            @PathVariable("bookId") int bookId, @RequestBody Book book) {
//        if (!authorRepository.existsById(authorId)) {
//            return new ResponseEntity<>("Author not found", HttpStatus.NOT_FOUND);
//        }
//        return bookService.updateABookByAuthorId(authorId, bookId, book);
//    }
//
//    @RequestMapping(value = "/author/{authorId}/book/{bookId}", method = RequestMethod.DELETE)
//    public ResponseEntity<Object> deleteABookByAuthorId(@PathVariable("authorId") int authorId,
//            @PathVariable("bookId") int bookId) {
//        if (!authorRepository.existsById(authorId)) {
//            return new ResponseEntity<>("Author not found", HttpStatus.NOT_FOUND);
//        }
//        return bookService.deleteABookByAuthorId(authorId, bookId);
//    }
//
//    @RequestMapping("/book/{bookId}/authors")
//    public ResponseEntity<Object> getAllAuthorsByBookId(@PathVariable int bookId) {
//        if (bookRepository.existsById(bookId))
//            return bookService.getAllAuthorsByBookId(bookId);
//        return new ResponseEntity<>("Book does not exist", HttpStatus.NOT_FOUND);
//
//    }

}
