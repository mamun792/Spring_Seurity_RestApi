package com.example.jwt_security.dto;

import lombok.Data;
//import com.example.jwt_security.entity.Role;

@Data

public class SingupRequest {
    private String fistname;
    private String lastname;
    private String email;
    private String password;

}