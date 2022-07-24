package com.librarymanagement.repository;

import java.util.ArrayList;

import javax.persistence.Table;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.librarymanagement.model.BorrowedItem;

@Table(name = "borrowed_item")
public interface BorrowedItemRepository extends CrudRepository<BorrowedItem, Integer> {

    @Query(value = "Select * from borrowed_item item where item.user_id = ?", nativeQuery = true)
    ArrayList<BorrowedItem> getAllItemsBorrowedByUser(int userId);

}
