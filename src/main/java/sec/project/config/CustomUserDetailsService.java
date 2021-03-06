package sec.project.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    HttpSession session;

    final private String databaseAddress = "jdbc:h2:file:./database";
    
    private String getUserPassFromDB(String username) 
    {
        if (username == null)
           throw new UsernameNotFoundException("User name not given");
        try (Connection con = DriverManager.getConnection(databaseAddress, "sa", "")){
            // A1 Injection vulnerability
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

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loading user "+username);
        UserDetails ud = new org.springframework.security.core.userdetails.User(
                username,
                getUserPassFromDB(username),
                true,
                true,
                true,
                true,
                Arrays.asList(new SimpleGrantedAuthority("USER")));
        session.setAttribute("user", username);
        return ud;
    }
}
