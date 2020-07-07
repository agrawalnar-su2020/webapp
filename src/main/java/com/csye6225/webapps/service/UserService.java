package com.csye6225.webapps.service;


import com.csye6225.webapps.model.Book;
import com.csye6225.webapps.model.User;
import com.csye6225.webapps.repository.UserRepository;
import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    private static final StatsDClient statsd = new NonBlockingStatsDClient("csye6225.webapp", "localhost", 8125);


    public User save(User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10)));
        long startTime = System.currentTimeMillis();
        User u= repository.save(user);
        statsd.recordExecutionTime("DB save user", System.currentTimeMillis() - startTime);
        return u;
    }
    public User findByEmail(String email){
        long startTime = System.currentTimeMillis();
        User u= repository.findByEmail(email);
        statsd.recordExecutionTime("DB findByEmail", System.currentTimeMillis() - startTime);
        return u;
    }

    public User checkLogin(String email, String password) {
        long startTime = System.currentTimeMillis();
          User user = repository.findByEmail(email);
          if(user == null)
              return user ;
          else {
              if (BCrypt.checkpw(password, user.getPassword())) {
                  statsd.recordExecutionTime("DB check login", System.currentTimeMillis() - startTime);
                  return user;
              }
          }
        statsd.recordExecutionTime("DB check login", System.currentTimeMillis() - startTime);
          return null;
    }
    public void updateUser(User u){
        long startTime = System.currentTimeMillis();
        User user = repository.findByEmail(u.getEmail());
        user.setPassword(BCrypt.hashpw(u.getPassword(), BCrypt.gensalt(10)));
        user.setFirstName(u.getFirstName());
        user.setLastName(u.getLastName());
        repository.save(user);
        statsd.recordExecutionTime("DB update user", System.currentTimeMillis() - startTime);
    }


}
