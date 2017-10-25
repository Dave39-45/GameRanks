package GameRanks.GameRanks.controller;

import GameRanks.GameRanks.model.Publisher;
import GameRanks.GameRanks.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/publisher")
public class PublisherController {
    @Autowired
    private PublisherService publisherService;
    
    @GetMapping("")
    public String publishersPage(){
        return "publisherList";
    }
    
    @GetMapping("/{id}")
    public String getPublisher(@PathVariable long id, Model model) {
        Publisher publisher = publisherService.getPublisher(id);
        
        if(publisher != null){
            model.addAttribute("publisherName", publisher.getName());
            return "publisher";
        }
        else{
            return "notFound";
        }
    }
}
