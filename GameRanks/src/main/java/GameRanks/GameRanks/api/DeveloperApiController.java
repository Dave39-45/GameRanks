package GameRanks.GameRanks.api;

import GameRanks.GameRanks.model.Developer;
import GameRanks.GameRanks.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/developer")
public class DeveloperApiController {
    @Autowired
    private DeveloperService developerService;
    
    @GetMapping("")
    public ResponseEntity<Iterable<Developer>> developersPage(){
        return ResponseEntity.ok(developerService.getDeveloperList());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Developer> getDeveloper(@PathVariable long id) {
        Developer developer = developerService.getDeveloper(id);
        
        if(developer != null){
            return ResponseEntity.ok(developer);
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
}
