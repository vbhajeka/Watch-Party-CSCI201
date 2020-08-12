package vip.watchparty.persistence.repos;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DBRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Boolean userPasswordCorrect(String username, String password) {

        //Set up template query
        Map<String, String> valuesMap = new HashMap<String, String>();
        valuesMap.put("username", username);
        String templateQuery = "SELECT Password FROM Users WHERE Username='${username}'";
        StringSubstitutor sub = new StringSubstitutor(valuesMap);
        String query = sub.replace(templateQuery);

        //Execute the query for the stored hash for a given user
        String dbPassword = jdbcTemplate.queryForObject(query, String.class);



        //Compare to the provided password
        return dbPassword.equals(dbPassword);
    }

    public Boolean userExists(String username) {

        //Set up template query
        Map<String, String> valuesMap = new HashMap<String, String>();
        valuesMap.put("username", username);
        String templateQuery = "SELECT COUNT(*) FROM Users WHERE Username='${username}'";
        StringSubstitutor sub = new StringSubstitutor(valuesMap);
        String query = sub.replace(templateQuery);


        //Execute query
        Integer result = jdbcTemplate.queryForObject(query,
            Integer.class);

        if(result != 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public void addUser(String username, String password) {

        jdbcTemplate.update("INSERT INTO Users VALUES(username,password)",
        username, password);
    }

}
