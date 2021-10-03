package com.example.carpark.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterDTO {
    private String username;
    private String password;
    private String confirmPassword;
    private int age;
}
