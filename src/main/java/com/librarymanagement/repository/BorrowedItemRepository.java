package com.librarymanagement.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.librarymanagement.model.BorrowedItem;

public interface BorrowedItemRepository extends CrudRepository<BorrowedItem, Integer> {

    @Query(value = "Select * from BorrowedItem item where item.user_id = ?", nativeQuery = true)
    ArrayList<BorrowedItem> getAllItemsBorrowedByUser(int userId);

}
