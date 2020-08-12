package vip.watchparty.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {


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
    	return "success";
    	// return loginDB(username, password);
	}
    
    @GetMapping("api/createUser")
    @ResponseBody
	public String create (@RequestParam String username, @RequestParam String password) {
    	return "success";
    	// return createDB(username, password);
	}
    
    
    public static String loginDB (String username, String password) {
    	System.out.println("here");
    	Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String retVal = "failure";
		
		try {
			System.out.println("Trying to connect to db");
			conn = DriverManager.getConnection("jdbc:mysql://165.227.63.76:3306/csci?user=csci201&password=Csci201!&serverTimezone=UTC");
			System.out.println("Connected to db!");
			
			st = conn.createStatement();
			rs = st.executeQuery("SELECT Password FROM Users WHERE Username='" + username + "';");
			
			if (rs.next()) {
				if (rs.getString("Password").equals(password)) {
					System.out.println("found one!");
					retVal = "success";
				} else {
					System.out.println("invalid password");
				}
			} else {
				System.out.println("nope");
			}
			
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		
		return retVal;
    }
    
    public static String createDB (String username, String password) {
    	Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String retVal = "";
		
		try {
			System.out.println("Trying to connect to db");
			conn = DriverManager.getConnection("jdbc:mysql://165.227.63.76:3306/csci?user=csci201&password=Csci201!&serverTimezone=UTC");
			System.out.println("Connected to db!");
			
			st = conn.createStatement();
			
			rs = st.executeQuery("SELECT * FROM Users WHERE Username=" + username + ";");
			if (rs.next() ) {
				retVal = "failure";
			} else {
				st.executeUpdate("INSERT INTO Users (Username, Password) VALUES ('" + username + "', '" + password + " ');");
				retVal = "success";
			}
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		
		return retVal;
    }

}
