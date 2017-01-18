package sec.project.config;

import java.util.Base64;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired 
    CustomUserDetailsService cuds;

    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /* Orig code: no real security at the moment
        
        http.authorizeRequests()
                .anyRequest().permitAll();
        */
        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();
        
        http.authorizeRequests()
                .antMatchers("/h2-console/*").permitAll()
                .anyRequest().authenticated();
        http.formLogin()
            //.loginPage("/mylogin")         
            .permitAll();        
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        //auth.userDetailsService(userDetailsService); 
        auth.userDetailsService(cuds).passwordEncoder(passwordEncoder()); 

    }

    //@Bean
    public PasswordEncoder passwordEncoder() {
        System.out.println("taalla ollaan");
        
//        return new BCryptPasswordEncoder();
        return new Base64PasswordEncoder();
    }
}
