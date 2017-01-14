package sec.project.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    final private String databaseAddress = "jdbc:h2:file:./database";
    private Map<String, String> accountDetails;

    
    private String getUserPassFromDB(String username) 
    {
        try (Connection con = DriverManager.getConnection(databaseAddress, "sa", "")){
            String accountQuery = "select password from UserAccount where username = \'"+username+"\'";
            ResultSet rs = con.createStatement().executeQuery(accountQuery);
            
            if (rs.next()) {
                String pass = rs.getString(1);
                return pass;
            } else {
                System.out.println("user {} not found".format(username));
                throw new UsernameNotFoundException("user {} not found from database".format(username));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomUserDetailsService.class.getName()).log(Level.SEVERE, null, ex);
            throw new UsernameNotFoundException(ex.getMessage());
        }    
    }
    
    
    @PostConstruct
    public void init() {
        // this data would typically be retrieved from a database
        this.accountDetails = new TreeMap<>();
        //this.accountDetails.put("ted", "$2a$06$rtacOjuBuSlhnqMO2GKxW.Bs8J6KI0kYjw/gtF0bfErYgFyNTZRDm");
        this.accountDetails.put("ted", "GV2b25l");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /* if (!this.accountDetails.containsKey(username)) {
            throw new UsernameNotFoundException("No such user: " + username);
        }
        */

        return new org.springframework.security.core.userdetails.User(
                username,
                //this.accountDetails.get(username),
                getUserPassFromDB(username),
                true,
                true,
                true,
                true,
                Arrays.asList(new SimpleGrantedAuthority("USER")));
    }
}
