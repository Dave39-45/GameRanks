package GameRanks.GameRanks.controller;

import GameRanks.GameRanks.model.Developer;
import GameRanks.GameRanks.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/developer")
public class DeveloperController {
    @Autowired
    private DeveloperService developerService;
    
    @GetMapping("")
    public String developersPage(){
        return "developerList";
    }
    
    @GetMapping("/{id}")
    public String getDeveloper(@PathVariable long id, Model model) {
        Developer developer = developerService.getDeveloper(id);
        
        if(developer != null){
            model.addAttribute("developerName", developer.getName());
            return "developer";
        }
        else{
            return "notFound";
        }
    }
}
