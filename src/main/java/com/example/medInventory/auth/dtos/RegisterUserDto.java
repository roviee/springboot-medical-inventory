package com.example.medInventory.auth.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserDto {
    private String fullName;
    private String email;
    private String password;
}
