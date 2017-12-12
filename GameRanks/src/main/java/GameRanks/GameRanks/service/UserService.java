package GameRanks.GameRanks.service;

import GameRanks.GameRanks.exception.UserNotValidException;
import GameRanks.GameRanks.exception.EmailInUseException;
import GameRanks.GameRanks.model.User;
import GameRanks.GameRanks.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.SessionScope;

@Service
@SessionScope
@Data
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    private User user;
    
    public User login(User user) throws UserNotValidException {
        if (isValidUser(user)) {
            return this.user = userRepository.findByUsername(user.getUsername()).get();
        }
        
        throw new UserNotValidException();
    }

    public void logout() { 
        user = null; 
    } 

    public User register(User user) {
        return this.user = userRepository.save(user);
    }

    public boolean isValidUser(User user) {
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword()).isPresent();
    }
    
    public boolean isUserAlreadyExists(User user){
        return (userRepository.findByEmail(user.getEmail()).isPresent() || userRepository.findByUsername(user.getUsername()).isPresent());
    }
    
    public boolean isLoggedIn() {
        return user != null;
    }
    
    public User getUser() { 
        return user; 
    }
    
    public void reloadUser(){
        if(this.user != null){
            user = userRepository.findOne(user.getId());
        }
    }
    
    @Transactional
    public boolean changeAvatar(String avatar){
        return userRepository.updateAvatar(user.getId(), avatar) > 0;
    }
    
    @Transactional
    public boolean changePassword(String password){
        return userRepository.updatePassword(user.getId(), password) > 0;
    }
    
    @Transactional
    public boolean changeEmail(String email) throws EmailInUseException{
        if(!userRepository.findByEmail(email).isPresent()){
            return userRepository.updateEmail(user.getId(), email) > 0;
        }
        else{
            throw new EmailInUseException();
        }
    }
}
