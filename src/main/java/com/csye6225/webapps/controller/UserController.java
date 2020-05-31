package com.csye6225.webapps.controller;


import com.csye6225.webapps.model.User;
import com.csye6225.webapps.service.BookService;
import com.csye6225.webapps.service.UserService;
import com.csye6225.webapps.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = "/")
    public String index(){

        return "index";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(ModelMap model){
        model.addAttribute("user",new User());
        return "registerUsers";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public  ModelAndView register(@ModelAttribute("user") User user, HttpServletRequest request, BindingResult bindingResult, ModelMap model){
        userValidator.validate(user, bindingResult);
        ModelAndView mv = new ModelAndView();
        if (bindingResult.hasErrors()) {
            model.addAttribute("user",user);
            mv.setViewName("registerUsers");
            return mv;
        }

        User u = userService.save(user);
        HttpSession session = (HttpSession) request.getSession();
        session.setAttribute("user", user);
        mv.addObject("user",user);
        mv.setViewName("home");
            return mv;
    }
    @RequestMapping(value = "/home", method = RequestMethod.POST)
    public ModelAndView login(HttpServletRequest request ){
        ModelAndView mv = new ModelAndView();
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        User u = userService.checkLogin(userName,password);
       if(u==null) {
           mv.addObject("error","Email/Password invalid");
           mv.setViewName("error");
       }
       else {
           HttpSession session = (HttpSession) request.getSession();
           session.setAttribute("user", u);
           mv.addObject("user",u);
           mv.setViewName("home");
       }
       return mv;
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView home (HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        // Checking Session
        HttpSession sessionExpired = (HttpSession) request.getSession(false);
        if(sessionExpired==null )
            mv.setViewName("index");
        else {
            HttpSession session = (HttpSession) request.getSession(false);
            User u = (User) session.getAttribute("user");
            mv.addObject("user",u);
            mv.setViewName("home");
        }
        return mv;
    }

    @RequestMapping(value = "/updatedetail", method = RequestMethod.GET)
    public ModelAndView updateDetails(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        // Checking Session
        HttpSession sessionExpired = (HttpSession) request.getSession(false);
        if(sessionExpired==null)
            mv.setViewName("index");
        else {
            HttpSession session = (HttpSession) request.getSession(false);
            User u = (User) session.getAttribute("user");
            mv.addObject("user",u);
            mv.setViewName("updateUser");
        }
        return mv;
    }
    @RequestMapping(value = "/updatedetail", method = RequestMethod.POST)
    public ModelAndView updateDetail(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        // Checking Session
        HttpSession sessionExpired = (HttpSession) request.getSession(false);
        if(sessionExpired==null )
            mv.setViewName("index");
        else {
            HttpSession session = (HttpSession) request.getSession(false);
            User u = (User) session.getAttribute("user");
            u.setFirstName(request.getParameter("firstName"));
            u.setLastName(request.getParameter("lastName"));
            u.setPassword(request.getParameter("password"));

            userService.updateUser(u);
            mv.setViewName("home");
        }
        return mv;
    }
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request){
        HttpSession session = (HttpSession) request.getSession();
        session.invalidate();
        return "index";
    }
}
