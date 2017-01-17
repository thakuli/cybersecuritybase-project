/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sec.project.controller;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.config.CustomUserDetailsService;
import sec.project.repository.SignupRepository;


/**
 *
 * @author th567
 */
@Controller
public class LoginController {    
    @Autowired
    CustomUserDetailsService cuds;
    
    @PostConstruct
    public void init() {
    }    
    
    @RequestMapping(value = "/mylogin")
    public String myLogin(Model model,
                          @RequestParam(required = false) String username, 
                          @RequestParam(required = false) String password) {
        try {
            System.out.println("hevone: username="+username);
            UserDetails ud = cuds.loadUserByUsername(username);
            System.out.println("ud.name={}".format(ud.getUsername()));
            model.addAttribute("authenticated", true);
            
            return "/mainPage";
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Invalid login");
            return "mylogin";
        }
    }    
}
