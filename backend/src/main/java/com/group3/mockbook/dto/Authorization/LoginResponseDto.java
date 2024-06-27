package com.group3.mockbook.dto.Authorization;

public class LoginResponseDto {
    private String token;

    public LoginResponseDto() {
    }

    public LoginResponseDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
