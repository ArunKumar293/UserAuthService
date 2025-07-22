package org.example.userauthservice.Controller;

import org.example.userauthservice.DTO.LoginRequestDTO;
import org.example.userauthservice.DTO.SignupRequestDTO;
import org.example.userauthservice.DTO.UserDTO;
import org.example.userauthservice.DTO.ValidateTokenRequestDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/signup")
    public UserDTO signup(@RequestBody SignupRequestDTO signupRequestDTO) {
        return null;
    }

    @PostMapping("/login")
    public UserDTO login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return null;
    }

    @PostMapping("/validateToken")
    public UserDTO validateToken(@RequestBody ValidateTokenRequestDTO validateTokenRequestDTO) {
        return null;
    }
}
