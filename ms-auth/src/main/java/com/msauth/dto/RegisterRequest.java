package com.msauth.dto;

import com.msauth.entity.Rol;
import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private Rol rol;
}
