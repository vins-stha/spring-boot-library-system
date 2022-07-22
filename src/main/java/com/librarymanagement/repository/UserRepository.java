package com.librarymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.librarymanagement.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
