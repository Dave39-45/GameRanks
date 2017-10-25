package GameRanks.GameRanks.service;

import GameRanks.GameRanks.model.Publisher;
import GameRanks.GameRanks.repository.PublisherRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
public class PublisherService {
    @Autowired
    private PublisherRepository publisherRepository;
    
    public Iterable<Publisher> getPublisherList(){
        return publisherRepository.findAll();
    }
    
    public Publisher getPublisher(long id){
        return publisherRepository.findOne(id);
    }
}
