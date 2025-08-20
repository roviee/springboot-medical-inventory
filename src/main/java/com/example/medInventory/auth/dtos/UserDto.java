package com.example.medInventory.auth.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private Integer id;
    private String fullName;
    private String email;

    public UserDto(Integer id, String fullname, String email) {
        this.id = id;
        this.fullName = fullname;
        this.email = email;
    }
}
