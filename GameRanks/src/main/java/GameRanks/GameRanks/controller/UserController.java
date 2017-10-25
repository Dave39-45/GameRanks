package GameRanks.GameRanks.controller;

import GameRanks.GameRanks.annotation.AccessBy;
import GameRanks.GameRanks.exception.EmailInUseException;
import static GameRanks.GameRanks.model.User.AccessLevel;
import GameRanks.GameRanks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    
    @AccessBy({AccessLevel.USER, AccessLevel.ADMIN})
    @GetMapping("")
    public String userPage(){
        return "user";
    }
    
    @AccessBy({AccessLevel.USER, AccessLevel.ADMIN})
    @PostMapping("/changePassword")
    public String changePassword(Model model, @RequestParam String password, @RequestParam String passwordAgain){
        //Ha nem egyezik meg a ket jelszo, akkor jelezzuk
        if(!password.equals(passwordAgain)){
            model.addAttribute("passwordsAreDifferent", true);
        }
        else if(userService.changePassword(password)){
            model.addAttribute("success", true);
        }
        else{
            model.addAttribute("fail", true);
        }
        
        return userPage();
    }
    
    @AccessBy({AccessLevel.USER, AccessLevel.ADMIN})
    @PostMapping("/changeEmail")
    public String changeEmail(Model model, @RequestParam String email){
        try{
            if(userService.changeEmail(email)){
                model.addAttribute("success", true);
            }
            else{
                model.addAttribute("fail", true);
            }
        }
        catch(EmailInUseException e){
            model.addAttribute("emailInUse", true);
        }
        
        return userPage();
    }
}
