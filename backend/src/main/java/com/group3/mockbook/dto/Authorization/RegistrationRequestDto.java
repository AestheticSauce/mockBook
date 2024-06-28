package com.group3.mockbook.dto.Authorization;

public class RegistrationRequestDto {
    private String email;
    private String password;
    private String username;
    private String securityQuestion;
    private String securityAnswer;

    public RegistrationRequestDto() {
    }

    public RegistrationRequestDto(String email, String password, String username, String securityQuestion, String securityAnswer) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }   
}
