package org.example.userauthservice.Controller;

import org.example.userauthservice.DTO.LoginRequestDTO;
import org.example.userauthservice.DTO.SignupRequestDTO;
import org.example.userauthservice.DTO.UserDTO;
import org.example.userauthservice.DTO.ValidateTokenRequestDTO;
import org.example.userauthservice.Models.User;
import org.example.userauthservice.Service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public UserDTO signup(@RequestBody SignupRequestDTO signupRequestDTO) {

        User user =  authService.signup(
                signupRequestDTO.getName(),
                signupRequestDTO.getEmail(),
                signupRequestDTO.getPassword(),
                signupRequestDTO.getPhoneNumber()
        );

        return UserToUserDTO(user);
    }

    @PostMapping("/login")
    public UserDTO login(@RequestBody LoginRequestDTO loginRequestDTO) {

        User user = authService.login(
                loginRequestDTO.getEmail(),
                loginRequestDTO.getPassword()
        );

        return UserToUserDTO(user);
    }

    @PostMapping("/validateToken")
    public UserDTO validateToken(@RequestBody ValidateTokenRequestDTO validateTokenRequestDTO) {
        return null;
    }

    public UserDTO UserToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }
}
