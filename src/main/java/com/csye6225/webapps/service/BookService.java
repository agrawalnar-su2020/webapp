package com.csye6225.webapps.service;

import com.csye6225.webapps.model.Book;
import com.csye6225.webapps.repository.BookRepository;
import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository repository;

    private static final StatsDClient statsd = new NonBlockingStatsDClient("csye6225.webapp", "localhost", 8125);

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
        long startTime = System.currentTimeMillis();
        repository.deleteById(bookID);
        statsd.recordExecutionTime("DB delete book", System.currentTimeMillis() - startTime);
    }
    public List<Book> buyerBooks(Long userID){
        return repository.buyerBooks(userID);
    }
}
