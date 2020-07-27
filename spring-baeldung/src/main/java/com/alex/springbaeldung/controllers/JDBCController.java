package com.alex.springbaeldung.controllers;

import com.alex.springbaeldung.persistence.jdbc.Book;
import com.alex.springbaeldung.persistence.jdbc.BookRepositoryJDBC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

//https://mkyong.com/spring-boot/spring-boot-jdbc-examples/
@Controller
public class JDBCController {

    //Create a logger
    Logger logger = LoggerFactory.getLogger(JDBCController.class);

    //Provide access to JDBC
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    @Qualifier("jdbcBookRepo")
    private BookRepositoryJDBC bookRepository;

    @GetMapping("/jdbc")
    public ModelAndView jdbcQuery() {
        ModelAndView mv = new ModelAndView("jdbc");
        mv.addObject("message", "A message from the controller");

        List<Book> myBooks = bookRepository.findAll();
        List<String> bookList = new ArrayList<String>();

        for(Book b: myBooks) {
           bookList.add(b.getName());
        }

        mv.addObject("books", bookList);

        return mv;
    }
}
