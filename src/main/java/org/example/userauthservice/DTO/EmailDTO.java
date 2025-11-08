package org.example.userauthservice.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmailDTO {

    private String to;

    private String from;

    private String subject;

    private String body;

}
