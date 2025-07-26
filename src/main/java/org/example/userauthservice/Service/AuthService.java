package org.example.userauthservice.Service;

import org.example.userauthservice.Exception.PasswordMismatchException;
import org.example.userauthservice.Exception.UserAlreadyExistsException;
import org.example.userauthservice.Exception.UserNotExistsException;
import org.example.userauthservice.Models.User;
import org.example.userauthservice.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User signup(String name, String email, String password, String phoneNumber) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            throw new UserAlreadyExistsException("User Alreday Present");
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setPhoneNumber(phoneNumber);
        return userRepository.save(user);
    }

    @Override
    public User login(String email, String password) {

        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()) {
            throw new UserNotExistsException("User Doesn't Exist. Please Sign Up!");
        }
        User user = userOptional.get();
        if(!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new PasswordMismatchException("Incorrect Password!");
        }
        return user;
    }

    @Override
    public Boolean validateToken(String token, Long userId) {
        return null;
    }
}
