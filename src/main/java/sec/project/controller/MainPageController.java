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
    
    private List<String> statuses;
    private List<Friend> friends;


    private Friend createFriend(String user, List<String> statuses) {
        Friend f = new Friend();
        f.setUsername(user);
        f.setStatuses(statuses);
        
        return f;
    }
    
    @PostConstruct
    public void init() {
        statuses = DBAPI.getStatusesByUser("tero");        
        friends = DBAPI.getFriendsByUser("tero");
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
        model.addAttribute("statuses", statuses);
        model.addAttribute("friends", friends);
        return "mainPage";
    }
    
    @RequestMapping(value = "/addstatus", method = RequestMethod.POST)
    public String addStatus(Model model, @RequestParam String status) {
        //signupRepository.save(new Signup(name, address));
        //System.out.println("status={}"+status);
        this.statuses.add(status);
        model.addAttribute("statuses", statuses);
        return "redirect:/mainPage";
    }
}
