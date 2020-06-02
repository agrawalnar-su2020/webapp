package com.csye6225.webapps.service;

import com.csye6225.webapps.model.ShoppingCart;
import com.csye6225.webapps.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {

    @Autowired
    ShoppingCartRepository repository;

    public void save(ShoppingCart cart){

        repository.save(cart);
    }

    public ShoppingCart cartByUserID(Long userID){
        return repository.cartByUserID(userID);
    }

}
