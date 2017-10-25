package GameRanks.GameRanks.api;

import GameRanks.GameRanks.model.Publisher;
import GameRanks.GameRanks.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/publisher")
public class PublisherApiController {
    @Autowired
    private PublisherService publisherService;
    
    @GetMapping("")
    public ResponseEntity<Iterable<Publisher>> publishersPage(){
        return ResponseEntity.ok(publisherService.getPublisherList());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getPublisher(@PathVariable long id) {
        Publisher publisher = publisherService.getPublisher(id);
        
        if(publisher != null){
            return ResponseEntity.ok(publisher);
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
}
