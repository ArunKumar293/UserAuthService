package org.example.userauthservice.Service;

import org.example.userauthservice.Models.User;
import org.example.userauthservice.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserByID(Long id){
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()){
            return null;
        }
        return userOptional.get();
    }
}
