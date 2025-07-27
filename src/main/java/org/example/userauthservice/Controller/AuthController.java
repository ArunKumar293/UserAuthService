package org.example.userauthservice.Controller;

import org.antlr.v4.runtime.misc.Pair;
import org.example.userauthservice.DTO.LoginRequestDTO;
import org.example.userauthservice.DTO.SignupRequestDTO;
import org.example.userauthservice.DTO.UserDTO;
import org.example.userauthservice.DTO.ValidateTokenRequestDTO;
import org.example.userauthservice.Exception.InvalidTokenException;
import org.example.userauthservice.Models.User;
import org.example.userauthservice.Service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@RequestBody SignupRequestDTO signupRequestDTO) {

        User user =  authService.signup(
                signupRequestDTO.getName(),
                signupRequestDTO.getEmail(),
                signupRequestDTO.getPassword(),
                signupRequestDTO.getPhoneNumber()
        );

        UserDTO userDTO = UserToUserDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {

        Pair<User,String> response = authService.login(
                loginRequestDTO.getEmail(),
                loginRequestDTO.getPassword()
        );

        User user = response.a;
        String token = response.b;

        MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.SET_COOKIE,token);

        UserDTO userDTO  = UserToUserDTO(user);
        return new ResponseEntity<>(userDTO,headers,HttpStatus.OK);
    }

    @PostMapping("/validateToken")
    public ResponseEntity<Void> validateToken(@RequestBody ValidateTokenRequestDTO validateTokenRequestDTO) {

        Boolean response = authService.validateToken(validateTokenRequestDTO.getToken(),validateTokenRequestDTO.getUserId());

        if(!response){
            throw new InvalidTokenException("Invalid Token");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public UserDTO UserToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }
}
