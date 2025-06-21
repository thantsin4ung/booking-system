package com.tsa.dev.requestDTO;

import lombok.Data;

@Data
public class AuthRequest {
    private String usernameOrEmail;
    private String password;
}
