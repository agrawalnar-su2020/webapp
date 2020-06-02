package com.csye6225.webapps.service;

import com.csye6225.webapps.model.CartItem;
import com.csye6225.webapps.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService {

    @Autowired
    CartItemRepository repository;

    public void save(CartItem item){
        repository.save(item);
    }

    public CartItem cartItemByID(long itemID){
        return repository.findById(itemID).orElse(null);
    }

     public List<CartItem> cartItemByBookID(Long bookID){
        return repository.cartItemByBookID(bookID);
    }

    public void delete(CartItem item){
        repository.delete(item);
    }
}
