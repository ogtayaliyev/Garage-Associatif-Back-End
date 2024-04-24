package com.example.garage2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailRequest {

    private String email;
    private String to;
    private String subject;
    private String message;

    // Getters and setters
}
