package com.librarymanagement.controller;

import com.librarymanagement.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @RequestMapping("/authors/")
    public String index(){
        System.out.println("Hello from authors");
        return "Hello from authors";
    }

    @RequestMapping("/authors/all")
    public  ResponseEntity <Object> getList(){

        return authorService.getAll();

    }


}
