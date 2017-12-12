package GameRanks.GameRanks.service;

import GameRanks.GameRanks.clientStruct.element.PublisherStruct;
import GameRanks.GameRanks.serverStruct.ScoreStruct;
import GameRanks.GameRanks.model.Publisher;
import GameRanks.GameRanks.repository.PublisherRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
public class PublisherService {
    @Autowired
    private PublisherRepository publisherRepository;
    
    @Autowired
    private ReviewService reviewService;
    
    public Iterable<Publisher> getPublisherList(){
        return publisherRepository.findAll();
    }
    
    public Publisher getPublisher(long id){
        return publisherRepository.findOne(id);
    }
    
    public Iterable<PublisherStruct> getPublishersByScore(Iterable<ScoreStruct> publishersToGet){
        List<PublisherStruct> publishers = new ArrayList<>();
        
        for(ScoreStruct publisherScore : publishersToGet){
            publishers.add(new PublisherStruct(publisherRepository.findOne(publisherScore.getId()), publisherScore.getAvgscore(), null, null));
        }
        
        return reviewService.addPublisherRankTitle(publishers);
    }
    
    public Iterable<Publisher> getPublishersInRangeWithFilter(int start, int amount, String name, boolean ascending){
        return ascending ? publisherRepository.getPublishersInRangeWithFilterAsc(start, amount, name.toLowerCase()) :
                            publisherRepository.getPublishersInRangeWithFilterDesc(start, amount, name.toLowerCase());
    }
}
