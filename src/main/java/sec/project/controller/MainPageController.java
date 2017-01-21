package sec.project.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.DBAPI;
import sec.project.domain.Friend;

@Controller
public class MainPageController {
    @Autowired
    private HttpSession session;
        
    @PostConstruct
    public void init() {    }    
    
    
    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/mainPage";
    }


    @RequestMapping(value = "/mainPage", method = RequestMethod.GET)
    public String mainPage(Model model) {
        System.out.println("session="+session.getAttribute("user"));
        String user = (String)session.getAttribute("user");
        
        model.addAttribute("statuses",  DBAPI.getStatusesByUsername(user));
        model.addAttribute("friends", DBAPI.getFriendsByUser(user));
        model.addAttribute("user", user);
        
        return "/mainPage";
    }
    
    @RequestMapping(value = "/addstatus", method = RequestMethod.POST)
    public String addStatus(Model model, @RequestParam String status) {
        String user = (String)session.getAttribute("user");
        DBAPI.addUserStatus(user, status);
        model.addAttribute("statuses", DBAPI.getStatusesByUsername(user));
        model.addAttribute("user", user);
        
        return "redirect:/mainPage";
    }

    @RequestMapping(value = "/searchFriends", method = RequestMethod.GET)
    public String searchFriendsGET(Model model) {
     
        return "/searchFriends";
    }  
    
    @RequestMapping(value = "/searchFriends", method = RequestMethod.POST)
    public String searchFriendsPOST(Model model, @RequestParam String friend) {
        List<String> friends = DBAPI.getUsersByName(friend);
        model.addAttribute("friends", friends);
        
        return "/searchFriends";
    }

    @RequestMapping(value = "/addNewFriend", method = RequestMethod.GET)
    public String addNewFriendPOST(Model model, @RequestParam String new_friend) {
        String user = (String)session.getAttribute("user");
        
        List<Friend> friends = DBAPI.getFriendsByUser(user);
        model.addAttribute("user", user);
        model.addAttribute("friend", new_friend);

        if (friends.stream().filter(f -> f.getUsername().equals(new_friend)).collect(Collectors.toList()).isEmpty()) {
            DBAPI.addNewFriend(user, new_friend);
            model.addAttribute("friendAdded", "true");
        } else {
            System.out.println("Already is a friend");
            model.addAttribute("friendAdded", "false");
        }

        return "addNewFriend";
    }    
    
}
