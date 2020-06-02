package com.csye6225.webapps.service;


import com.csye6225.webapps.model.Book;
import com.csye6225.webapps.model.User;
import com.csye6225.webapps.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public User save(User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10)));
        return repository.save(user);
    }
    public User findByEmail(String email){
        return repository.findByEmail(email);
    }

    public User checkLogin(String email, String password) {
          User user = repository.findByEmail(email);
          if(user == null)
              return user ;
          else {
              if (BCrypt.checkpw(password, user.getPassword()))
                  return user;
          }
          return null;
    }
    public void updateUser(User u){
        User user = repository.findByEmail(u.getEmail());
        user.setPassword(BCrypt.hashpw(u.getPassword(), BCrypt.gensalt(10)));
        user.setFirstName(u.getFirstName());
        user.setLastName(u.getLastName());
        repository.save(user);
    }


}
