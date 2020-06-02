package com.csye6225.webapps.controller;

import com.csye6225.webapps.model.Book;
import com.csye6225.webapps.model.CartItem;
import com.csye6225.webapps.model.ShoppingCart;
import com.csye6225.webapps.model.User;
import com.csye6225.webapps.service.BookService;
import com.csye6225.webapps.service.CartItemService;
import com.csye6225.webapps.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/buyer/**")
public class BuyerController {

    @Autowired
    ShoppingCartService cartService;

    @Autowired
    CartItemService cartItemService;

    @Autowired
    BookService bookService;


    @RequestMapping(value = "/buyer/addcart", method = RequestMethod.POST)
    public void addTOCart (HttpServletRequest request, CartItem cartItem) {

            HttpSession sessionExit = (HttpSession) request.getSession(false);
            User user = (User) sessionExit.getAttribute("user");
            ShoppingCart cart = cartService.cartByUserID(user.getUserID());
            Book book = bookService.bookById(Long.parseLong(request.getParameter("id")));
            int quant =Integer.parseInt (request.getParameter("quantityAdd"));
            boolean flag = false;
            Long itemID = 0L;

            for(CartItem item: cart.getCartItem()){
                if(item.getBook().getBookID() == book.getBookID()){
                    flag = true;
                    itemID =item.getCartItemID();
                }
            }
            if(flag){
              CartItem exitItem = cartItemService.cartItemByID(itemID);
              int temp =exitItem.getQuantityAdd()+quant;
              exitItem.setQuantityAdd(temp);
              cartItemService.save(exitItem);
            }
            else {
                cartItem.setQuantityAdd(quant);
                cartItem.setBook(book);
                cartItem.setShoppinhCart(cart);
                cartItemService.save(cartItem);
                cart.getCartItem().add(cartItem);
                cartService.save(cart);
            }
    }

    @RequestMapping(value = "/buyer/cart", method = RequestMethod.GET)
    public ModelAndView cart (HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        // Checking Session
        HttpSession sessionExit = (HttpSession) request.getSession(false);
        if (sessionExit == null)
            mv.setViewName("index");
        else {
            User user = (User) sessionExit.getAttribute("user");
            ShoppingCart cart = cartService.cartByUserID(user.getUserID());
            mv.addObject("cartItem",cart.getCartItem());
            mv.setViewName("shoppingCart");
        }
        return mv;
    }
}
