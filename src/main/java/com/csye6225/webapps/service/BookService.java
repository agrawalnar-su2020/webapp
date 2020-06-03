package com.csye6225.webapps.service;

import com.csye6225.webapps.model.Book;
import com.csye6225.webapps.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository repository;

    public Book save(Book book){
        return repository.save(book);
    }

    public List<Book> sellerBooks(Long userID){
        return repository.sellerBooks(userID);
    }

    public Book bookById(long bookID){
        return repository.findById(bookID).orElse(null);
    }


    public void deleteBook(long bookID){

        repository.deleteById(bookID);
    }
    public List<Book> buyerBooks(Long userID){
        return repository.buyerBooks(userID);
    }
}
