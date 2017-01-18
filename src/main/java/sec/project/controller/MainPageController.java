package sec.project.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.config.CustomUserDetailsService;
import sec.project.domain.DBAPI;
import sec.project.domain.Friend;
import sec.project.repository.SignupRepository;

@Controller
public class MainPageController {
    @Autowired
    private HttpSession session;
        
    @PostConstruct
    public void init() {
//        statuses = DBAPI.getStatusesByUsername((String)session.getAttribute("user"));        
//        friends = DBAPI.getFriendsByUser((String)session.getAttribute("user"));
    }    
    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/mainPage";
    }


    
    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm() {
        return "redirect:/mainPage";
    }

    @RequestMapping(value = "/mainPage", method = RequestMethod.GET)
    public String mainPage(Model model) {
        System.out.println("session="+session.getAttribute("user"));
        String user = (String)session.getAttribute("user");
        
        model.addAttribute("statuses",  DBAPI.getStatusesByUsername(user));
        model.addAttribute("friends", DBAPI.getFriendsByUser(user));
        model.addAttribute("user", user);
        
        return "mainPage";
    }
    
    @RequestMapping(value = "/addstatus", method = RequestMethod.POST)
    public String addStatus(Model model, @RequestParam String status) {
        System.out.println("koera");
        //this.statuses.add(status);
        String user = (String)session.getAttribute("user");
        DBAPI.addUserStatus(user, status);
        model.addAttribute("statuses", DBAPI.getStatusesByUsername(user));
        model.addAttribute("user", user);
        
        return "redirect:/mainPage";
    }

    @RequestMapping(value = "/searchFriends", method = RequestMethod.GET)
    public String searchFriendsGET(Model model) {
     
        return "searchFriends";
    }  
    
    @RequestMapping(value = "/searchFriends", method = RequestMethod.POST)
    public String searchFriendsPOST(Model model, @RequestParam String friend) {
        String user = (String)session.getAttribute("user");
        
        List<Friend> friends = DBAPI.searchFriends(user, friend);
        model.addAttribute("user", user);
        model.addAttribute("friends", friends);
        
        return "searchFriends";
    }    
}
