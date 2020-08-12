package vip.watchparty.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import vip.watchparty.persistence.repos.DBRepo;

@Controller
public class LoginController {

    @Autowired
    DBRepo dbRepo;

    @GetMapping("/login")
    ModelAndView login() {
        ModelAndView mv = new ModelAndView("login");
        return mv;

    }

    @GetMapping("/register")
    ModelAndView register() {
        ModelAndView mv = new ModelAndView("Signup");
        return mv;

    }

    @GetMapping("api/authenticateUser")
    @ResponseBody
	public String authenticate (@RequestParam String username, @RequestParam String password) {
        Boolean auth = dbRepo.userPasswordCorrect(username, password);

        if(auth == true) {
            return "success";
        }
        else {
            return "false";
        }
	}

    @GetMapping("api/createUser")
    @ResponseBody
	public String create (@RequestParam String username, @RequestParam String password) {

        if(dbRepo.userExists(username)) {
            return "failure";
        }
        else {
            dbRepo.addUser(username, password);
            return "success";
        }

	}



}
