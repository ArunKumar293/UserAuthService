package org.example.userauthservice.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ValidateTokenRequestDTO {

    private String token;

    private Long userId;
}
