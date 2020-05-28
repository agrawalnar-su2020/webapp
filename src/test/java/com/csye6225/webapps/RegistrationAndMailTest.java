package com.csye6225.webapps;

import com.csye6225.webapps.model.User;
import com.csye6225.webapps.repository.UserRepository;
import com.csye6225.webapps.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class RegistrationAndMailTest {

    @MockBean
    UserRepository repository;

    @Autowired
    UserService service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void checkEmail() {

        User user = new User();
        user.setEmail("naresh@gmail.com");

        try {

            when(repository.findByEmail("naresh@gmail.com")).thenReturn(user);

            User result =(service.findByEmail("naresh@gmail.com"));

            assertEquals(user ,result);

        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void registerUser() {

        User user = new User();
        user.setFirstName("naresh");
        user.setLastName("agrawal");
        user.setEmail("naresh@gmail.com");
        user.setPassword("123456Na");

        try {

            when(repository.save(user)).thenReturn(user);

            User result =(service.save(user));

            assertEquals(user ,result);

        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
