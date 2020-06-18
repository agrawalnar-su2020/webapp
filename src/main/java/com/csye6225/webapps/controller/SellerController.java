package com.csye6225.webapps.controller;

import com.csye6225.webapps.comparator.BookComparator;
import com.csye6225.webapps.model.Book;
import com.csye6225.webapps.model.BookImages;
import com.csye6225.webapps.model.CartItem;
import com.csye6225.webapps.model.User;
import com.csye6225.webapps.service.BookImageService;
import com.csye6225.webapps.service.BookService;
import com.csye6225.webapps.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/seller/**")
public class SellerController {

    @Autowired
    BookService bookService;

    @Autowired
    CartItemService cartItemService;

    @Autowired
    BookImageService imageService;

    @RequestMapping(value = "/seller", method = RequestMethod.GET)
    public ModelAndView sellerHome (HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        // Checking Session
        HttpSession sessionExit = (HttpSession) request.getSession(false);
        if (sessionExit == null)
            mv.setViewName("index");
        else {
            User user = (User) sessionExit.getAttribute("user");
            List<Book> books = bookService.sellerBooks(user.getUserID());
            Collections.sort(books, new BookComparator());
            mv.addObject("sellerBooks",books);
            mv.setViewName("Seller");
        }
        return mv;
    }

    @RequestMapping(value = "/seller/addbook", method = RequestMethod.GET)
    public ModelAndView addBook (HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        // Checking Session
        HttpSession sessionExit = (HttpSession) request.getSession(false);
        if (sessionExit == null)
            mv.setViewName("index");
        else {
            mv.setViewName("addBook");
        }
        return mv;
    }
    @RequestMapping(value = "/seller/addbook", method = RequestMethod.POST)
    public ModelAndView registerBook (HttpServletRequest request, Book book, @RequestParam("image") List<MultipartFile> file) {
        ModelAndView mv = new ModelAndView();
        HttpSession session = (HttpSession) request.getSession();
        User user = (User) session.getAttribute("user");
        book.setTitle(request.getParameter("title"));
        book.setISBN(request.getParameter("ISBN"));
        book.setAuthors(request.getParameter("authors"));
        book.setPublicationDate(request.getParameter("publicationDate"));
        book.setQuantity( Integer.parseInt (request.getParameter("quantity")));
        book.setPrice(Double.parseDouble(request.getParameter("price")));
        book.setUser(user);
        book.setBookAddedTime(new Date());

        Book b = bookService.save(book);

        if(file.size()!=0){
            Iterator<MultipartFile> i=file.iterator();
            while(i.hasNext()) {
                MultipartFile temp = i.next();
                imageService.uploadeImage(temp,b);
            }

        List<Book> books = bookService.sellerBooks(user.getUserID());
        Collections.sort(books, new BookComparator());
        mv.addObject("sellerBooks",books);
        mv.setViewName("Seller");
        }
        return mv;
    }
    @RequestMapping(value = "/seller/updatebook", method = RequestMethod.GET)
    public ModelAndView updateBook (HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        // Checking Session
        HttpSession sessionExit = (HttpSession) request.getSession(false);
        if (sessionExit == null)
            mv.setViewName("index");
        else {
            boolean flag = false;
            Long id = Long.parseLong(request.getParameter("id"));
            User user = (User) sessionExit.getAttribute("user");
            List<Book> books = bookService.sellerBooks(user.getUserID());
            Iterator<Book> i=books.iterator();
            while(i.hasNext()) {
                Book temp =i.next();
                if(temp.getBookID()== id){
                    flag=true;
                }
            }
            if(flag){
               mv.addObject("book", bookService.bookById(id));
               mv.setViewName("updateBook");
            }else{
                mv.addObject("error","You can't update this book");
                mv.setViewName("error");
            }
        }
        return mv;
    }
    @RequestMapping(value = "/seller/updatebook", method = RequestMethod.POST)
    public ModelAndView updateBookS (HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        Long id = Long.parseLong(request.getParameter("id"));
        Book book = bookService.bookById(id);
        book.setTitle(request.getParameter("title"));
        book.setISBN(request.getParameter("ISBN"));
        book.setAuthors(request.getParameter("authors"));
        book.setPublicationDate(request.getParameter("publicationDate"));
        book.setQuantity( Integer.parseInt (request.getParameter("quantity")));
        book.setPrice(Double.parseDouble(request.getParameter("price")));
        book.setBookUpdateTime(new Date());
        bookService.save(book);
        HttpSession sessionExit = (HttpSession) request.getSession(false);
        User user = (User) sessionExit.getAttribute("user");
        List<Book> books = bookService.sellerBooks(user.getUserID());
        Collections.sort(books, new BookComparator());
        mv.addObject("sellerBooks",books);
        mv.setViewName("Seller");
        return mv;
    }

    @RequestMapping(value = "/seller/deletebook", method = RequestMethod.GET)
    public ModelAndView deleteBook (HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        boolean flag = false;
        Long id = Long.parseLong(request.getParameter("id"));
        HttpSession sessionExit = (HttpSession) request.getSession(false);
        User user = (User) sessionExit.getAttribute("user");
        List<Book> books = bookService.sellerBooks(user.getUserID());
        Iterator<Book> i=books.iterator();
        while(i.hasNext()) {
            Book temp =i.next();
            if(temp.getBookID()== id){
                flag=true;
            }
        }
        if(flag){
            List<CartItem> item = cartItemService.cartItemByBookID(id);
            if(item.size()!=0){
                Iterator<CartItem> it =item.iterator();
                while(it.hasNext()) {
                    CartItem temp = it.next();
                    cartItemService.delete(temp);
                }
            }
            bookService.deleteBook(id);
            List<Book> bookUp = bookService.sellerBooks(user.getUserID());
            Collections.sort(bookUp, new BookComparator());
            mv.addObject("sellerBooks",bookUp);
            mv.setViewName("Seller");
        }else{
            mv.addObject("error","You can't delete this book");
            mv.setViewName("error");
        }
       return mv;
    }

    @RequestMapping(value = "/seller/viewimage", method = RequestMethod.GET)
    public ModelAndView viewImage (HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        // Checking Session
        HttpSession sessionExit = (HttpSession) request.getSession(false);
        if (sessionExit == null)
            mv.setViewName("index");
        else {
            Long bookID = Long.parseLong(request.getParameter("id"));
            List<String> imagesURL = imageService.bookImagesURL(bookID);
            if(imagesURL.size()==0){
                mv.addObject("error","Image is not available");
                mv.setViewName("error");
            }else{
                mv.addObject("imagesURL",imagesURL.get(0));
                mv.setViewName("viewImages");
            }
        }
        return mv;
    }

    @RequestMapping(value = "/seller/deleteimage", method = RequestMethod.GET)
    public ModelAndView deleteImage (HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        // Checking Session
        HttpSession sessionExit = (HttpSession) request.getSession(false);
        if (sessionExit == null)
            mv.setViewName("index");
        else {
            Long bookID = Long.parseLong(request.getParameter("id"));
            User user = (User) sessionExit.getAttribute("user");
            List<String> imagesName = imageService.imagesName(bookID);
            if(imagesName.size()==0){
                mv.addObject("error","Image is not available");
                mv.setViewName("error");
            }else{
                Iterator<String> i=imagesName.iterator();
                    while(i.hasNext()) {
                        String temp = i.next();
                        imageService.deleteS3Image(temp);
                    }
                List<Long> imagesID =imageService.imagesID(bookID);
                Iterator<Long> j=imagesID.iterator();
                while(j.hasNext()) {
                    Long temp = j.next();
                    imageService.deleteDBImage(temp);
                }

                List<Book> books = bookService.sellerBooks(user.getUserID());
                Collections.sort(books, new BookComparator());
                mv.addObject("sellerBooks",books);
                mv.setViewName("Seller");
            }
        }
        return mv;
    }



}
