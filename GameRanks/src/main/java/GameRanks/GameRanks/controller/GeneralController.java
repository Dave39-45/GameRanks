package GameRanks.GameRanks.controller;

import GameRanks.GameRanks.exception.UserNotValidException;
import GameRanks.GameRanks.model.User;
import static GameRanks.GameRanks.model.User.AccessLevel.USER;
import GameRanks.GameRanks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class GeneralController {
    
    
    //TODO
        //Ha mar tudunk lekerni jatekot, akkor csinaljuk meg, hogy ha csak felmegyunk, akkor legyenek meg itt a szukseges adatok, max nem jelenitjuk meg oket
            //Raer, ha mar kitalaltam mit hogy
    
    
    @Autowired
    private UserService userService;
    
    @GetMapping("")
    public String mainPage(){
        return "main";
    }
    
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute(new User());
        return "login";
    }
    
    @PostMapping("/login")
    public String login(@ModelAttribute User user, Model model) {
        try { 
            userService.login(user); 
            return mainPage();
        }
        catch (UserNotValidException e) { 
            model.addAttribute("error", true); 
            return "login"; 
        } 
    }
    
    @PostMapping("/logout")
    public String logout(@ModelAttribute User user){
        userService.logout();
        
        return mainPage();
    }
    
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model, @RequestParam String passwordAgain) {
        //Ha nem egyezik meg a ket jelszo, akkor jelezzuk
        if(!user.getPassword().equals(passwordAgain)){
            model.addAttribute("passwordsAreDifferent", true);
            return "register";
        }
        
        user.setAccessLevel(USER);
        userService.register(user);

        return mainPage();
    }
}
