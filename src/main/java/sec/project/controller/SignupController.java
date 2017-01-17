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

@Controller
public class SignupController {

    @Autowired
    private SignupRepository signupRepository;

    @Autowired
    CustomUserDetailsService cuds;
    
    private List<String> statuses;
    
    @PostConstruct
    public void init() {
        statuses = new ArrayList<>();
        statuses.add("today is a good day");
        statuses.add("cycling is good for your body");
    }    
    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/mainPage";
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
    
    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm() {
        return "redirect:/mainPage";
    }

    @RequestMapping(value = "/mainPage", method = RequestMethod.GET)
    public String mainPage(Model model) {
        model.addAttribute("statuses", statuses);
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
