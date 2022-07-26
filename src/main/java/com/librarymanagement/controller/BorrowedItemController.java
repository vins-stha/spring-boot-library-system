package com.librarymanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.librarymanagement.errorhandlers.NotFoundException;
import com.librarymanagement.model.Book;
import com.librarymanagement.model.BorrowedItem;
import com.librarymanagement.model.User;
import com.librarymanagement.repository.BookRepository;
import com.librarymanagement.repository.BorrowedItemRepository;
import com.librarymanagement.repository.UserRepository;
import com.librarymanagement.requests.BorrowRequests;
import com.librarymanagement.stockmanagement.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

;

@RestController
@RequestMapping("/api")
public class BorrowedItemController {
    private ArrayList<BorrowedItem> borrowedItems = new ArrayList<>();

    @Autowired
    BorrowedItemRepository borrowedItemRepository;

    @Autowired
    UserRepository UserRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    StockService stockService;

    @GetMapping("/users/{userId}/borrowedItems")
    public ResponseEntity<Object> getAllItemsBorrowedByUser(@PathVariable int userId) {

        UserRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        borrowedItemRepository.getAllItemsBorrowedByUser(userId).forEach(borrowedItems::add);

        return new ResponseEntity<>(borrowedItems, HttpStatus.OK);
    }

    @GetMapping("/borrowedItems")
    public ResponseEntity<Object> getAllItems() {

        ArrayList<BorrowedItem> _borrowedItems = new ArrayList<>();
        for (BorrowedItem item : borrowedItemRepository.findAll()) {
            _borrowedItems.add(item);
        }
        // borrowedItemRepository.findAll().forEach(_borrowedItems::add);
        System.out.println("\n\nBOoorow=\n" + _borrowedItems);

        return new ResponseEntity<>(_borrowedItems, HttpStatus.OK);
    }

    // handle borrow (book) request from user
    @PostMapping("/users/{userId}/borrow")
    public ResponseEntity<Object> borrowItem(@PathVariable int userId, @RequestBody BorrowRequests request) {
        String message = "";
        ObjectMapper mapper = new ObjectMapper();
        int bookId = request.getBookId();
        BorrowedItem _item;

        // check user id
        UserRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found with id" + userId));

        // check book id
        try {
            Book _bookToBorrow = bookRepository.findById(bookId).get();
            User _borrower = UserRepository.findById(userId).get();

            // check if book is in stock
            ResponseEntity<Object> obj = stockService.getBookCount(bookId);

            // if book is not present in stock
            if (Integer.parseInt(obj.getBody().toString()) < 1 || obj.getBody() == null) {
                return ResponseEntity.badRequest().body("Book is out of stock");

            } else {
                ArrayList<BorrowedItem> itemsBorrowed = borrowedItemRepository.getAllItemsBorrowedByUser(userId);

                // if items are present in user borrowed list
                if ((itemsBorrowed).size() > 0) {

                    // itemsBorrowed
                    for (BorrowedItem item : itemsBorrowed) {

                        // if book exist
                        if (item.getBook().getId() == request.getBookId()) {
                            message = "Book/Item already exist in borrowed item list of user";
                            return ResponseEntity.badRequest().body(message);
                        }
                    }
                }

                // if null or book is not in list

                Date today = new Date();
                long dueDay = today.getTime() + (14 * 24 * 60 * 60 * 1000);
                Date dueDate = new Date(dueDay);

                _item = new BorrowedItem();
                _item.setBook(_bookToBorrow);
                _item.setUser(_borrower);
                _item.setStartState(today);
                _item.setDueDate(dueDate);

                // save to borrwed list
                borrowedItemRepository.save(_item);

                // Reduce its count in stock by 1
                stockService.loanABookAndUpdateStock(_bookToBorrow.getId());
                message = _item.toString();
            }
            return new ResponseEntity<>(message, HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Book not in stock. " + e.getMessage());
        }

    }

    // handle return book request
    @GetMapping("/users/{userId}/return/books/{bookId}")
    public ResponseEntity<Object> returnItem(@PathVariable int userId, @PathVariable int bookId) {
        System.out.println("Book to return = " + bookId + " user = " + userId);
        String message = "";
        try {
            User _returnee = UserRepository.findById(userId).get();
            try {
                Book _bookToReturn = bookRepository.findById(bookId).get();
                ArrayList<BorrowedItem> _borrowedItemsByUser = borrowedItemRepository.getAllItemsBorrowedByUser(userId);
                if (_borrowedItemsByUser.size() > 0) {
                    // itemsBorrowed
                    for (BorrowedItem item : _borrowedItemsByUser) {

                        // if book exist in borrower list
                        if (item.getBook().getId() == bookId) {
                            // check for over due
                            Date _dateReturned = new Date();
                            Date _dueDate = item.getDueDate();
                                /* test to check OVER DUE
                                     Calendar cal = Calendar.getInstance();                                     cal.setTime(new Date());
                                    cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)-6);
                                   _dueDate = cal.getTime();
                                 */

                            // if overdue
                            if (_dateReturned.after(_dueDate)) {
                                long _overdueDays = TimeUnit.DAYS.convert(_dateReturned.getTime() - _dueDate.getTime(), TimeUnit.MILLISECONDS);
                           // System.out.println("Loan over due by " + _overdueDays + " days");
                                item.setOverDueDays(_overdueDays);
                                item.setFineAmount(_overdueDays * 0.5);
                                item.setFinePaidStatus(false);
                                borrowedItemRepository.save(item);
                            }

                            stockService.handleReturn(item.getBook().getId());
                            if (item.getFineAmount() <= 0) {
                                borrowedItemRepository.deleteById(item.getId());
                            }

                            return ResponseEntity.ok().body("Book returned successfully. ");
                        }

                    }
                    return ResponseEntity.badRequest().body("Book with id " + bookId + " not present in list of items borrowed by user  " + userId);
                } else
                    return ResponseEntity.badRequest().body("No book present in borrower list for user Id : " + userId);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Book not found with given id " + bookId);

            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("User not found with id " + userId + ". " + e.getMessage());
        }
    }


    // @PostMapping("/users/{userId}/borrow")
    // V2.0 wehn UI is present
    // public ResponseEntity<Object> borrowItem(@PathVariable int userId,
    // @RequestBody Book book) {

}
