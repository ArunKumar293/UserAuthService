package org.example.userauthservice.Controller;

import org.example.userauthservice.DTO.UserDTO;
import org.example.userauthservice.Models.User;
import org.example.userauthservice.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    public UserDTO UserToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        User user = userService.getUserByID(id);
        return UserToUserDTO(user);
    }
}
