package org.example.userauthservice.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.antlr.v4.runtime.misc.Pair;
import org.example.userauthservice.Exception.PasswordMismatchException;
import org.example.userauthservice.Exception.UserAlreadyExistsException;
import org.example.userauthservice.Exception.UserNotExistsException;
import org.example.userauthservice.Models.Session;
import org.example.userauthservice.Models.Status;
import org.example.userauthservice.Models.User;
import org.example.userauthservice.Repository.SessionRepository;
import org.example.userauthservice.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SecretKey secretKey;

    @Autowired
    private SessionRepository sessionRepository;

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
    public Pair<User,String> login(String email, String password) {

        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()) {
            throw new UserNotExistsException("User Doesn't Exist. Please Sign Up!");
        }
        User user = userOptional.get();
        if(!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new PasswordMismatchException("Incorrect Password!");
        }

        Long nowInMillSeconds = System.currentTimeMillis();

        Map<String,Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("iss","scaler");
        claims.put("gen",nowInMillSeconds);
        claims.put("exp",nowInMillSeconds+1000000);
        claims.put("scope",user.getRoles());

        String token = Jwts.builder().claims(claims).signWith(secretKey).compact();

        Session session = new Session();
        session.setToken(token);
        session.setUser(user);
        session.setStatus(Status.ACTIVE);
        sessionRepository.save(session);

        return new Pair<>(user,token);
    }

    @Override
    public Boolean validateToken(String token, Long userId) {

        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUserId(token,userId);

        if(sessionOptional.isEmpty()) {
            return false;
        }
        Session session = sessionOptional.get();

        System.out.println(token);
        System.out.println(session.getToken());

        JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();

        Claims claims = jwtParser.parseSignedClaims(token).getPayload();

        Long expiry = (Long)claims.get("exp");
        Long currTime = System.currentTimeMillis();

        if(currTime > expiry){
           session.setStatus(Status.INACTIVE);
            sessionRepository.save(session);
            return false;
        }

        return true;
    }
}
